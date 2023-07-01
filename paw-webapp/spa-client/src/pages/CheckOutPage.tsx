import { FC, useEffect, useState } from "react";
import { handleResponse } from "../Utils";
import { OrderItemModel } from "../models";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import useOrderItemService from "../hooks/serviceHooks/orderItems/useOrderItemService";
import { useParams } from "react-router-dom";
import { Grid } from "@mui/material";
import CheckOutSummary from "../components/CheckoutSummary";
import { useReservation } from "../hooks/serviceHooks/reservations/useReservation";
import { useOrderItemsBySecCode } from "../hooks/serviceHooks/reservations/useOrderItemsBySecCode";


const CheckOutPage: FC = () =>{

    const { securityCode } = useParams();

    const orderItemService = useOrderItemService();

    const { reservation, loading: reservationLoading, error: reservationError, updateReservation } = useReservation(securityCode!);

    const {orderItems, error: orderItemsError, loading: orderItemLoading} = useOrderItemsBySecCode(reservation, false);

    return(
        <Grid container xs={12} justifyContent="center">
            <CheckOutSummary reservation={reservation!} orderItems={orderItems!} />    
        </Grid>
    );
}

export default CheckOutPage;