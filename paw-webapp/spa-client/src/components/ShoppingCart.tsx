import { Box, Button, Drawer, Grid, IconButton, Tab, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import { FC } from "react";
import { OrderItemModel } from "../models";


type Props = {
    orderItems: OrderItemModel[];
    isOpen: boolean;
    toggleCart: () => void;
};

const ShoppingCart: FC<Props> =({orderItems, isOpen, toggleCart}) => {

    const calculateTotal = (orderItems: OrderItemModel[]) =>{
        let total = 0;
        orderItems.forEach(orderItem =>{
            total += orderItem.quantity * orderItem.unitPrice;
        })

        return total;
    }

    const cancelDishes = () => {
        orderItems.filter(orderItem => orderItem.status === "SELECTED").map(filteredItem => (
            console.log("do something to api")
        ));
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
                        <TableCell>Dish</TableCell>
                        <TableCell>Subtotal</TableCell>
                        <TableCell></TableCell>
                    </TableRow>
                    <TableBody>
                        {orderItems.filter(orderItem => orderItem.status === "SELECTED").map(filteredItem => (
                            <TableRow>
                                <TableCell>{filteredItem.id}</TableCell>
                                <TableCell>{filteredItem.quantity*filteredItem.unitPrice}</TableCell>
                                <TableCell>
                                    <IconButton>
                                        <CloseIcon />
                                    </IconButton>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </TableHead>
            </Grid>
            <Grid item xs={12} sx={{display:"flex", justifyContent:"space-evenly"}} marginY={2}>
                <Typography>Total:</Typography>
                <Typography>{calculateTotal(orderItems)}</Typography>
            </Grid>
            <Grid item xs={12} sx={{display:"flex", justifyContent:"space-evenly"}}>
                <Button color="success" variant="outlined" href="checkOut"><Typography>Confirm</Typography></Button>
                <Button color="error" variant="outlined" onClick={cancelDishes}><Typography>Clear</Typography></Button>
            </Grid>
          </Grid>
        </Drawer>
    );
}

export default ShoppingCart;