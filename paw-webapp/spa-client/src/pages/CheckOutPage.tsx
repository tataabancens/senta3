import { useEffect, useState } from "react";
import { handleResponse } from "../handleResponse";
import { OrderItemModel, ReservationModel } from "../models";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import useOrderItemService from "../hooks/serviceHooks/useOrderItemService";
import useReservationService from "../hooks/serviceHooks/useReservationService";
import { OrderItemService } from "../services/OrderItemService";

function CheckOutPage(){
    const [orderItems, setOrderItems] = useState<OrderItemModel[]>([]);
    const [reservation, setReservation] = useState<ReservationModel | undefined>();

    const orderItemService = useOrderItemService();
    const reservationService = useReservationService();

    useEffect(() => {
        let reservationParams = new ReservationParams();
        reservationParams.securityCode = "XF2ZMH";
        handleResponse(
            reservationService.getReservation(reservationParams),
            (response: ReservationModel) => setReservation(response)
        );
    }, []);

    useEffect(() => {
        let orderItemParams = new OrderitemParams();
        orderItemParams.securityCode = "XF2ZMH";
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