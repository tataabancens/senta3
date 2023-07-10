import { TableCell, TableRow, Typography } from "@mui/material";
import { FC, useEffect, useState } from "react";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import { useRestaurant } from "../../hooks/serviceHooks/restaurants/useRestaurant";
import { DishModel, OrderItemModel, RestaurantModel } from "../../models";
import { handleResponse } from "../../Utils";

type Props = {
    orderItem: OrderItemModel;
    usedDiscount: boolean | undefined;
    restaurant: RestaurantModel | undefined;
}

const CheckoutItem: FC<Props> = ({orderItem, usedDiscount, restaurant}) => {

    const [dish, setDish] = useState<DishModel | undefined>();
    const ds = useDishService();
    const textDecoration = usedDiscount ? 'line-through' : 'none';

    useEffect(() => {
        handleResponse(
            ds.getDishById(orderItem.dishId),
            (response: DishModel) => setDish(response)
        )
    },[])

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
        </TableRow>
    );
}

export default CheckoutItem;