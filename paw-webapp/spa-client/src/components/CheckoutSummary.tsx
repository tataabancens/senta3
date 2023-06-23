import { Grid, Paper, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { FC } from "react";
import { OrderItemModel, ReservationModel } from "../models";
import ShoppingCartItem from "./shoppingCart/ShoppingCartItem";

type Props = {
    reservation: ReservationModel,
    orderItems: OrderItemModel[]
};

const CheckOutSummary: FC<Props> = ({ reservation, orderItems }) => {


    const formatDate = (date?: string) => {
        if(date){
            const dateParts: string[] = date.split("-");
            const year: string = dateParts[0];
            const month: string = dateParts[1];
            const day: string = dateParts[2];

            return `${day}/${month}/${year}`;
        }
        return 0;
    }

    const calculateTotal = (orderItems: OrderItemModel[]) =>{
        let total = 0;
        orderItems.filter(orderItem => orderItem.status !== "SELECTED" && orderItem.status !== "DELETED").map(filteredItem => { total += filteredItem.quantity * filteredItem.unitPrice})
    
        return total;
    }


    return (
        <Grid item container component={Paper} xs={11} marginTop={2} padding={3} elevation={5} borderRadius={3}>
            <Grid item xs={12}><Typography variant="h4" align="center" color="primary">Reservation Summary</Typography></Grid>
            <Grid item container xs={12} marginTop={6}>
                <Grid item xs={12}><Typography variant="h5">Reservation info:</Typography></Grid>
                <Grid item xs={12}><Typography variant="subtitle1">Customer: {reservation?.customerName}</Typography></Grid>  
                <Grid item xs={12}><Typography variant="subtitle1">Date: {formatDate(reservation?.date)}</Typography></Grid>
                <Grid item xs={12}><Typography variant="subtitle1">Hour: {reservation?.hour}:00</Typography></Grid>  
                <Grid item xs={12}><Typography variant="subtitle1">People: {reservation?.peopleAmount}</Typography></Grid>
                <Grid item xs={12}><Typography variant="subtitle1">Table: {reservation?.tableNumber}</Typography></Grid>
                <Grid item xs={12}><Typography variant="subtitle1">Status: {reservation?.status}</Typography></Grid> 
            </Grid>
            <Grid item xs={12}><Typography variant="h5" align="center">Items Summary</Typography></Grid>
            <Grid item container xs={12}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell align="left">Dish</TableCell>
                            <TableCell align="center">Quantity</TableCell>
                            <TableCell align="center">Subtotal</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {orderItems.filter(item => item.status !== "DELETED" && item.status !== "SELECTED").map((filteredItem, i) => (
                            <ShoppingCartItem key={i} orderItem={filteredItem} isCartItem={false} />
                        ))}
                    </TableBody>
                </Table>
            </Grid>
            <Grid item container xs={12} marginTop={3}>
                <Grid item xs={6}><Typography variant="h5" align="center" color="primary">Total:</Typography></Grid>
                <Grid item xs ={6}><Typography variant="h5" align="center" color="primary">${calculateTotal(orderItems)}</Typography></Grid>
            </Grid>
        </Grid>
    );
}

export default CheckOutSummary;