import { useEffect, useState } from "react";
import { ReservationParams } from "../../../models/Reservations/ReservationParams";
import { OrderItemModel } from "../../../models";
import useReservationService from "./useReservationService";

export const useOrderItems = (filterStatus: string, orderItemStatus: string) => {
    const [orderItems, setOrderItems] = useState<OrderItemModel[]>([]);
    const [error, setError] = useState<string>();
    const abortController = new AbortController();
    const reservationService = useReservationService();

    useEffect(() => {
        (async () => {
            let reservationParams = new ReservationParams();
            reservationParams.filterStatus = filterStatus;
            reservationParams.orderItemStatus = orderItemStatus;
            
            const { isOk, data, error } = await reservationService.getOrderItemsNewVersion(reservationParams, abortController);
            if (isOk) {
                data.length > 0 ? setOrderItems(data) : setOrderItems([]);
            }
            else setError(error);
        })();
        return () => {
            abortController.abort();
        }
    }, []);

    const removeItem = (item: OrderItemModel) => {
        setOrderItems(prevItems => prevItems.filter(orderItem => orderItem !== item));
    }

    const addItem = (newItem: OrderItemModel) => {
        setOrderItems(prevItems => [...prevItems, newItem]);
    }

    return {
        orderItems: orderItems,
        error,
        loading: !orderItems && !error,
        removeItem,
        addItem
    }
}