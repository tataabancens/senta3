import { IconButton, TableCell, TableRow, Typography } from "@mui/material";
import { FC, useContext, useEffect, useState } from "react";
import { DishModel, OrderItemModel } from "../../models";
import { handleResponse } from "../../Utils";
import CloseIcon from '@mui/icons-material/Close';
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import useOrderItemService from "../../hooks/serviceHooks/orderItems/useOrderItemService";
import { OrderitemParams } from "../../models/OrderItems/OrderitemParams";
import { ReservationContext } from "../../context/ReservationContext";

type Props = {
    orderItem: OrderItemModel;
    securityCode?: string | undefined;
    isCartItem: boolean,
    usedDiscount: boolean | undefined;
}

const ShoppingCartItem: FC<Props> = ({orderItem, securityCode, isCartItem, usedDiscount}) => {

    const [dish, setDish] = useState<DishModel | undefined>();
    const dishService = useDishService();
    const orderItemService = useOrderItemService();
    const { updateItem, restaurant } = useContext(ReservationContext);
    const abortController = new AbortController();
    const textDecoration = usedDiscount ? 'line-through' : 'none';

    useEffect(() => {
        handleResponse(
            dishService.getDishById(orderItem.dishId),
            (response) => setDish(response)
        )
    },[])

    const cancelItem = async () => {
        let orderItemParams = new OrderitemParams();
        orderItemParams.status = "DELETED";
        orderItemParams.securityCode = securityCode;
        orderItemParams.orderItemId = orderItem.orderItemId;

        const { isOk } = await orderItemService.editOrderItem(orderItemParams, abortController);

        if (isOk) {
            orderItem.status = "DELETED";
            updateItem(orderItem);
        }
    }

    return (
        <TableRow>
            <TableCell align="left">{dish?.name}</TableCell>
            <TableCell align="center">
                <Typography variant="caption">
                    {orderItem.quantity}
                </Typography>
            </TableCell>
            <TableCell align="center">
                <Typography style={{textDecoration}} variant="caption" marginRight={usedDiscount? 1 : 0}>
                    ${orderItem.quantity*orderItem.unitPrice}
                </Typography>
                {usedDiscount && restaurant && <Typography variant="caption" color="blue">{(1-restaurant?.discountCoefficient) * orderItem.quantity*orderItem.unitPrice}</Typography>}
            </TableCell>
            {isCartItem && 
                <TableCell align="center">
                    <IconButton onClick={cancelItem}>
                        <CloseIcon />
                    </IconButton>
                </TableCell>
            }
        </TableRow>
    );
}

export default ShoppingCartItem;