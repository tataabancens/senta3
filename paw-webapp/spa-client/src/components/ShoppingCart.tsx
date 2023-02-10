import { Box, Button, Drawer, Grid, IconButton, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import {useNavigate} from "react-router-dom";
import { FC, useEffect, useState } from "react";
import { DishModel, OrderItemModel } from "../models";
import useOrderItemService from "../hooks/serviceHooks/useOrderItemService";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import { handleResponse } from "../Utils";
import ShoppingCartItem from "./ShoppingCartItem";


type Props = {
    orderItems: OrderItemModel[];
    isOpen: boolean;
    toggleCart: () => void;
    securityCode?: string;
    toggleReload?: () => void;
};

const ShoppingCart: FC<Props> =({orderItems, isOpen, toggleCart, securityCode, toggleReload}) => {


    const orderItemService = useOrderItemService();
    const navigate = useNavigate();

    const calculateTotal = (orderItems: OrderItemModel[]) =>{
        let total = 0;
        orderItems.forEach(orderItem =>{
            total += orderItem.quantity * orderItem.unitPrice;
        })

        return total;
    }

    const cancelDishes = () => {
        orderItems.filter(orderItem => orderItem.status === "SELECTED").map(filteredItem => {
            let orderItemParams = new OrderitemParams();
            orderItemParams.securityCode = securityCode;
            orderItemParams.orderItemId = filteredItem.orderItemId;
            orderItemParams.status = "DELETED";
            handleResponse(
                orderItemService.editOrderItem(orderItemParams),
                (response) => {
                    if(toggleReload)
                        toggleReload();
                }
            )
            }
        );
    }

    const confirmDishes = () => {
        orderItems.filter(orderItem => orderItem.status === "SELECTED").map(filteredItem => {
            let orderItemParams = new OrderitemParams();
            orderItemParams.securityCode = securityCode;
            orderItemParams.orderItemId = filteredItem.orderItemId;
            orderItemParams.status = "ORDERED";
            handleResponse(
                orderItemService.editOrderItem(orderItemParams),
                (response) => {
                    if(toggleReload)
                        toggleReload();
                }
            )
            }
        );
    }

    return(
        <Drawer anchor="right" open={isOpen} onClose={toggleCart}>
          <Grid container component={Box} sx={{ width: 400}} onClick={toggleCart} padding={1}>
            <Grid item xs={12} sx={{display:"flex", justifyContent:"flex-end"}}>
                <IconButton>
                    <CloseIcon  color="primary"/>
                </IconButton>
            </Grid>
            <Grid item xs={12} sx={ {display:"flex", justifyContent:"center", marginBottom: 2}}><Typography variant="h5">Shopping cart</Typography></Grid>
            <Grid item xs={12} component={Table}>
                <TableHead>
                    <TableRow>
                        <TableCell align="left">Dish</TableCell>
                        <TableCell>Qty</TableCell>
                        <TableCell>Subtotal</TableCell>
                        <TableCell></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                        {orderItems.filter(orderItem => orderItem.status === "SELECTED").map((filteredItem) => (
                            <ShoppingCartItem key={filteredItem.orderItemId} securityCode={securityCode} orderItem={filteredItem} toggleReload={toggleReload} />
                        ))}
                </TableBody>
            </Grid>
            <Grid item xs={12} sx={{display:"flex", justifyContent:"space-evenly"}} marginY={2}>
                <Typography>Total:</Typography>
                <Typography>${calculateTotal(orderItems)}</Typography>
            </Grid>
            <Grid item xs={12} sx={{display:"flex", justifyContent:"space-evenly"}}>
                <Button color="success" variant="outlined" onClick={confirmDishes}><Typography>Confirm</Typography></Button>
                <Button color="error" variant="outlined" onClick={cancelDishes}><Typography>Clear</Typography></Button>
            </Grid>
          </Grid>
        </Drawer>
    );
}

export default ShoppingCart;