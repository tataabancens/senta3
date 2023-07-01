import { useEffect, useState } from "react";
import { OrderItemModel, ReservationModel } from "../../../models";
import useOrderItemService from "../orderItems/useOrderItemService";
import { OrderitemParams } from "../../../models/OrderItems/OrderitemParams";

export const useOrderItemsBySecCode = (reservation: ReservationModel | undefined, reloadOrderItems: boolean) => {
    const [orderItems, setOrderItems] = useState<OrderItemModel[]>([]);
    const [error, setError] = useState<string>();
    const abortController = new AbortController();
    const orderItemService = useOrderItemService();

    useEffect(() => {
        if (!reservation) return;
        (async () => {
            let orderItemParams = new OrderitemParams();
            orderItemParams.securityCode = reservation.securityCode;
            
            const { isOk, data, error } = await orderItemService.getOrderItems(orderItemParams, abortController);
            if (isOk) {
                data.length > 0 ? setOrderItems(data) : setOrderItems([]);
            }
            else setError(error);
        })();
        return () => {
            abortController.abort();
        }
    }, [reservation, reloadOrderItems]);

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
    }
}