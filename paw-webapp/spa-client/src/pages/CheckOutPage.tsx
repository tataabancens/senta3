import { FC, useEffect, useState } from "react";
import { handleResponse } from "../Utils";
import { OrderItemModel } from "../models";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import useOrderItemService from "../hooks/serviceHooks/orderItems/useOrderItemService";
import { useParams } from "react-router-dom";
import { Grid } from "@mui/material";
import CheckOutSummary from "../components/CheckoutSummary";
import { useReservation } from "../hooks/serviceHooks/reservations/useReservation";


const CheckOutPage: FC = () =>{

    const [orderItems, setOrderItems] = useState<OrderItemModel[]>([]);
    const { securityCode } = useParams();

    const orderItemService = useOrderItemService();

    const { reservation, loading: reservationLoading, error: reservationError, updateReservation } = useReservation(securityCode!);

    useEffect(() => {
        let orderItemParams = new OrderitemParams();
        orderItemParams.securityCode = securityCode;
        handleResponse(
            orderItemService.getOrderItems(orderItemParams),
            (response: OrderItemModel[]) => setOrderItems(response)
        );
    }, [reservation]);

    return(
        <Grid container xs={12} justifyContent="center">
            <CheckOutSummary reservation={reservation!} orderItems={orderItems!} />    
        </Grid>
    );
}

export default CheckOutPage;