import { IconButton, Stack, Step, StepLabel, Stepper, TableCell, TableRow, Typography } from "@mui/material";
import { FC, useContext, useEffect, useState } from "react";
import { DishModel, OrderItemModel } from "../../models";
import { handleResponse } from "../../Utils";
import CloseIcon from '@mui/icons-material/Close';
import useOrderItemService from "../../hooks/serviceHooks/useOrderItemService";
import { OrderitemParams } from "../../models/OrderItems/OrderitemParams";
import InfoIcon from '@mui/icons-material/Info';
import { ReservationContext } from "../../context/ReservationContext";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";

type Props = {
    orderItem: OrderItemModel;
    toggleReload?: () => void;
}

const OrdersItem: FC<Props> = ({orderItem, toggleReload}) => {

    const [dish, setDish] = useState<DishModel | undefined>();
    const dishService = useDishService();
    const reservation = useContext(ReservationContext);
    const statusMap: Map<string, number> = new Map([
        ["ORDERED", 1],
        ["INCOMING", 2],
        ["DELIVERED", 3]
    ]);


    useEffect(() => {
        handleResponse(
            dishService.getDishById(orderItem.dishId),
            (response) => setDish(response)
        )
    },[])

    return (
        <TableRow>
            <TableCell align="left">{dish?.name}</TableCell>
            <TableCell align="center">{orderItem.quantity}</TableCell>
            {reservation!.status === "OPEN" &&
                <TableCell align="center">
                    <InfoIcon color="secondary"/>
                    <Typography variant="body1" color="secondary">Your orders status will be seen when you arrive at the restaurants</Typography>
                </TableCell>
            }
            {reservation!.status !== "OPEN" &&
                <TableCell>
                    <Stepper activeStep={statusMap.get(orderItem.status)}>
                        <Step><StepLabel>Ordered</StepLabel></Step>
                        <Step><StepLabel>Incoming</StepLabel></Step>
                        <Step><StepLabel>Delivered</StepLabel></Step>
                    </Stepper>
                </TableCell>  
            }                
        </TableRow>
    );
}

export default OrdersItem;