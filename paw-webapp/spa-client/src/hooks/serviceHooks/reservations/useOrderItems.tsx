import { useEffect, useState } from "react";
import { OrderItemModel } from "../../../models";
import useOrderItemService from "../orderItems/useOrderItemService";
import { OrderitemParams } from "../../../models/OrderItems/OrderitemParams";

export const useOrderItems = (reservationFilterStatus: string, orderItemStatus: string, interval?: number) => {
    const [orderItems, setOrderItems] = useState<OrderItemModel[] | undefined>(undefined);
    const [error, setError] = useState<string>();
    const abortController = new AbortController();
    const orderItemService = useOrderItemService();

    const getOrderItems = async () => {
        let orderItemParams = new OrderitemParams();
        orderItemParams.reservationStatus = reservationFilterStatus;
        orderItemParams.status = orderItemStatus;
        
        const { isOk, data, error } = await orderItemService.getOrderItems(orderItemParams, abortController);
        if (isOk) {
            data.length > 0 ? setOrderItems(data) : setOrderItems([]);
        }
        else setError(error);
    };

    useEffect(() => {
        getOrderItems();
        if(interval){
            const intervalId = setInterval(getOrderItems, interval);
            return () => clearInterval(intervalId);
        }
        return () => {
            abortController.abort();
        }
    }, []);

    const removeItem = (item: OrderItemModel) => {
        setOrderItems(prevItems => prevItems!.filter(orderItem => orderItem !== item));
    }

    const addItem = (newItem: OrderItemModel) => {
        setOrderItems(prevItems => [...prevItems!, newItem]);
    }

    return {
        orderItems: orderItems,
        error,
        loading: !orderItems && !error,
        removeItem,
        addItem
    }
}