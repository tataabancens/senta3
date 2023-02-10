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
    const [restaurant, setRestaurant] = useState<RestaurantModel>();

    const reservationService = useReservationService();
    const restaurantService = useRestaurantService();

    useEffect(() => {
        handleResponse(
            restaurantService.getRestaurant(1),
            (response) => setRestaurant(response)
        )
    },[])

    useEffect(() => {
        let reservationParams = new ReservationParams();
        reservationParams.filterStatus = "1";
        reservationParams.orderItemStatus = "1";

        handleResponse(
            reservationService.getOrderItems(reservationParams),
            (response: OrderItemModel[]) => (response.length > 0 ? setOrderedOrderItems(response) : setOrderedOrderItems([]))
        )

        reservationParams.orderItemStatus = "2";
        handleResponse(
            reservationService.getOrderItems(reservationParams),
            (response: OrderItemModel[]) => (response.length > 0 ? setIncomingOrderItems(response) : setIncomingOrderItems([]))
        )
    },[]);

    return(
        <Grid container component={Box} sx={{background: themePalette.PURPLE}} marginY={3}> 
            {orderedOrderItems.map(orderItem => <OrderItemCard orderItem={orderItem}/>)}
        </Grid>
    );
}

export default Kitchen