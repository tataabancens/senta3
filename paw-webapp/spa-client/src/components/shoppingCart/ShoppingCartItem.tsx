import { IconButton, TableCell, TableRow } from "@mui/material";
import { FC, useEffect, useState } from "react";
import { DishModel, OrderItemModel } from "../../models";
import { handleResponse } from "../../Utils";
import CloseIcon from '@mui/icons-material/Close';
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import useOrderItemService from "../../hooks/serviceHooks/orderItems/useOrderItemService";
import { OrderitemParams } from "../../models/OrderItems/OrderitemParams";

type Props = {
    orderItem: OrderItemModel;
    securityCode?: string | undefined;
    toggleReload?: () => void;
    isCartItem: boolean
}

const ShoppingCartItem: FC<Props> = ({orderItem, securityCode, toggleReload, isCartItem}) => {

    const [dish, setDish] = useState<DishModel | undefined>();
    const dishService = useDishService();
    const orderItemService = useOrderItemService();

    useEffect(() => {
        handleResponse(
            dishService.getDishById(orderItem.dishId),
            (response) => setDish(response)
        )
    },[])

    const cancelItem = () => {
        let orderItemParams = new OrderitemParams();
        orderItemParams.status = "DELETED";
        orderItemParams.securityCode = securityCode;
        orderItemParams.orderItemId = orderItem.orderItemId;
        handleResponse(
            orderItemService.editOrderItem(orderItemParams),
            (response) => {if(toggleReload){
                toggleReload();
            }
            }
        )
    }

    return (
        <TableRow>
            <TableCell align="left">{dish?.name}</TableCell>
            <TableCell align="center">{orderItem.quantity}</TableCell>
            <TableCell align="center">${orderItem.quantity*orderItem.unitPrice}</TableCell>
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