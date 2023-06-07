import { Box, Grid, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import OrderItemCard from "../components/OrderItemCard";
import { themePalette } from "../config/theme.config";
import { handleResponse } from "../Utils";
import useReservationService from "../hooks/serviceHooks/useReservationService";
import useRestaurantService from "../hooks/serviceHooks/useRestaurantService";
import { OrderItemModel, RestaurantModel } from "../models";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { ReservationService } from "../services/ReservationService";


function Kitchen (){
    const [orderedOrderItems, setOrderedOrderItems] = useState<OrderItemModel[]>([]);
    const [incomingOrderItems, setIncomingOrderItems] =useState<OrderItemModel[]>([]);
    const [deliveringOrderItems, setDeliveringOrderItems] =useState<OrderItemModel[]>([]);

    const reservationService = useReservationService();


    useEffect(() => {
        let reservationParams = new ReservationParams();
        reservationParams.filterStatus = "1";
        reservationParams.orderItemStatus = "1";

        handleResponse(
            reservationService.getOrderItems(reservationParams),
            (response: OrderItemModel[]) => {response.length > 0 ? setOrderedOrderItems(response) : setOrderedOrderItems([])
                console.log(response)
            }
        )

        reservationParams.orderItemStatus = "2";
        handleResponse(
            reservationService.getOrderItems(reservationParams),
            (response: OrderItemModel[]) => (response.length > 0 ? setIncomingOrderItems(response) : setIncomingOrderItems([]))
        )

        reservationParams.orderItemStatus = "3";
        handleResponse(
            reservationService.getOrderItems(reservationParams),
            (response: OrderItemModel[]) => (response.length > 0 ? setDeliveringOrderItems(response) : setDeliveringOrderItems([]))
        )
    },[]);

    return(
        <Grid container xs={12}>
            <Grid item container component={Box} sx={{background: themePalette.PURPLE}} marginY={3} xs={4}>
                <Grid item xs={12}><Typography>Ordered</Typography></Grid>
                <Grid item xs={10}>
                    {orderedOrderItems.map(orderItem => <OrderItemCard orderItem={orderItem}/>)}
                </Grid>
            </Grid>
            <Grid item container component={Box} sx={{background: themePalette.RED}} marginY={3} xs={4}>
                <Grid item xs={12}><Typography>Ordered</Typography></Grid>
                <Grid item xs={10}>
                    {incomingOrderItems.map(orderItem => <OrderItemCard orderItem={orderItem}/>)}
                </Grid>
            </Grid>
            <Grid item container component={Box} sx={{background: themePalette.BLUE}} marginY={3} xs={4}>
                <Grid item xs={12}><Typography>Delivering</Typography></Grid>
                <Grid item xs={10}>
                    {deliveringOrderItems.map(orderItem => <OrderItemCard orderItem={orderItem}/>)}
                </Grid>
            </Grid>
        </Grid>
    );
}

export default Kitchen