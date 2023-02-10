import { useEffect, useState } from "react";
import { handleResponse } from "../Utils";
import { OrderItemModel, ReservationModel } from "../models";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import useOrderItemService from "../hooks/serviceHooks/useOrderItemService";
import useReservationService from "../hooks/serviceHooks/useReservationService";
import { OrderItemService } from "../services/OrderItemService";
import { useParams } from "react-router-dom";

function CheckOutPage(){
    const [orderItems, setOrderItems] = useState<OrderItemModel[]>([]);
    const [reservation, setReservation] = useState<ReservationModel | undefined>();
    const { securityCode } = useParams();

    const orderItemService = useOrderItemService();
    const reservationService = useReservationService();

    useEffect(() => {
        let reservationParams = new ReservationParams();
        reservationParams.securityCode = securityCode;
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

    return(
        <h1>checkOut page coming soon</h1>
    );
}

export default CheckOutPage;