import { useEffect, useState } from "react";
import { handleResponse } from "../Utils";
import { OrderItemModel, ReservationModel } from "../models";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import useOrderItemService from "../hooks/serviceHooks/orderItems/useOrderItemService";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import { OrderItemService } from "../services/OrderItemService";
import { useParams } from "react-router-dom";
import {Button, Grid, Paper, Typography} from "@mui/material";
import {paths} from "../constants/constants";
import {useNavigate} from "react-router-dom";


function CheckOutPage(){
    let navigate = useNavigate();

    const [orderItems, setOrderItems] = useState<OrderItemModel[]>([]);
    const [reservation, setReservation] = useState<ReservationModel | undefined>();
    const { securityCode } = useParams();

    const orderItemService = useOrderItemService();
    const reservationService = useReservationService();

    const [accumPrice, setAccumPrice] = useState(0);
    const [accumulated, setAccumulated] = useState(false);

    const reservationParams = new ReservationParams();
    reservationParams.securityCode = securityCode;

    useEffect(() => {
        handleResponse(
            reservationService.getReservation(reservationParams),
            (response: ReservationModel) => setReservation(response)
        );
    }, []);

    useEffect(() => {
        let orderItemParams = new OrderitemParams();
        orderItemParams.securityCode = securityCode;
        handleResponse(
            orderItemService.getOrderItems(orderItemParams),
            (response: OrderItemModel[]) => setOrderItems(response)
        );
    }, []);

    if(!accumulated && orderItems && orderItems.length > 0) {
            let total = 0;
            orderItems.forEach((orderItem) => {
                total += orderItem.unitPrice * orderItem.quantity;
            });
            setAccumPrice(total);
        setAccumulated(true);
    }

    return(
        <Grid
            item
            container
            justifyContent="space-around"
            sx={{ height: "40vh" }}
        >
            <Grid
                item
                container
                xs={12}
                sm={11}
                component={Paper}
                borderRadius={3}
                elevation={3}
                padding={2}
                alignItems="center" justifyContent="center"
            >
                <Grid item component={Typography} variant="h5" xs={12} marginY={2}>
                    Your orders:
                </Grid>
                    {
                        orderItems && orderItems.length > 0 ? (
                        orderItems.map((orderItem) => (
                            <Grid item key={orderItem.orderItemId} xs={12}>
                                {//orderItemId: {orderItem.orderItemId}
                                    }
                                dishId: {orderItem.dishId}
                                quantity: {orderItem.quantity}
                                unitPrice: {orderItem.unitPrice}
                                total: {orderItem.unitPrice * orderItem.quantity}
                            </Grid>
                        ))
                    ) : (
                        <Button
                            onClick={() => {
                                if (reservation) navigate(paths.ROOT + paths.RESERVATIONS + "/" + reservation.securityCode);
                            }}
                        >
                            You didn't order anything!
                        </Button>
                    )
                    }
                    {
                        orderItems && orderItems.length > 0 ? (
                            <>
                                <div>Total check: {accumPrice}</div>
                                <Button
                                    fullWidth
                                    onClick={() => {
                                        if (reservation) navigate(paths.ROOT + paths.RESERVATIONS + "/" + reservation.securityCode);
                                    }}
                                >
                                    Continue ordering
                                </Button>
                                <Button
                                    fullWidth
                                    onClick={() => {
                                        reservationParams.status = "CHECK_ORDERED";
                                        reservationService.patchReservation(reservationParams).then(r => console.log(r));
                                        navigate(paths.ROOT);
                                    }}
                                >Check please!</Button>
                            </>
                        ) : (<div></div>)
                    }
            </Grid>
        </Grid>
    );
}

export default CheckOutPage;