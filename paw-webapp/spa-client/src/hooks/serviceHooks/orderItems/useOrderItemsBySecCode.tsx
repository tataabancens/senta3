import { useEffect, useState } from "react";
import { OrderItemModel, ReservationModel } from "../../../models";
import useOrderItemService from "./useOrderItemService";
import { OrderitemParams } from "../../../models/OrderItems/OrderitemParams";
import { ORDERITEMS_INTERVAL } from "../../../constants/constants";

export const useOrderItemsBySecCode = (reservation: ReservationModel | undefined) => {
    const [orderItems, setOrderItems] = useState<OrderItemModel[]>([]);
    const [reload, setReload] = useState(false)
    const [error, setError] = useState<string>();
    const abortController = new AbortController();
    const orderItemService = useOrderItemService();

    const reloadItems = () => {setReload(!reload)}

    const getOrderItems = async () => {
        if(!reservation) return;
        let orderItemParams = new OrderitemParams();
        orderItemParams.securityCode = reservation.securityCode;
            
        const { isOk, data, error } = await orderItemService.getOrderItems(orderItemParams, abortController);
        if (isOk) {
            data.length > 0 ? setOrderItems(data) : setOrderItems([]);
        }
        else setError(error);
    }

    useEffect(() => {
        getOrderItems()
        const interval = setInterval(getOrderItems, ORDERITEMS_INTERVAL); 
        return () => {
            clearInterval(interval);
            abortController.abort();
        }
    }, [reload]);

    const addItem = (newItem: OrderItemModel) => {
        setOrderItems((prevItems) => [...prevItems, newItem]);
    };
    
    const removeItem = (item: OrderItemModel) => {
        setOrderItems((prevItems) => prevItems.filter((orderItem) => orderItem !== item));
    };

    const updateItem = (updatedItem: OrderItemModel) => {
        setOrderItems((prevItems) =>
            prevItems.map((orderItem) =>
                orderItem.orderItemId === updatedItem.orderItemId ? updatedItem : orderItem
            )
        );
    };

    return {
        orderItems: orderItems,
        error,
        loading: !orderItems && !error,
        reloadItems,
        addItem,
        removeItem,
        updateItem
    }
}