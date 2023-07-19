import { Step, StepLabel, Stepper, TableCell, TableRow, Typography } from "@mui/material";
import { FC, useContext, useEffect, useState } from "react";
import { DishModel, OrderItemModel } from "../../models";
import { handleResponse } from "../../Utils";
import InfoIcon from '@mui/icons-material/Info';
import { ReservationContext } from "../../context/ReservationContext";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import { useTranslation } from "react-i18next";
import useServiceProvider from "../../context/ServiceProvider";

type Props = {
    orderItem: OrderItemModel;
    toggleReload?: () => void;
}

const OrdersItem: FC<Props> = ({orderItem, toggleReload}) => {

    const [dish, setDish] = useState<DishModel | undefined>();
    const { dishService } = useServiceProvider();
    const {reservation} = useContext(ReservationContext);
    const statusMap: Map<string, number> = new Map([
        ["ORDERED", 0],
        ["INCOMING", 1],
        ["DELIVERED", 2],
        ["FINISHED", 3]
    ]);
    const { t } = useTranslation();


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
                    <Typography variant="body1" color="secondary">{t('shoppingCart.ordersPanel.disclaimer')}</Typography>
                </TableCell>
            }
            {reservation!.status !== "OPEN" &&
                <TableCell>
                    <Stepper activeStep={statusMap.get(orderItem.status)}>
                        <Step><StepLabel>{t('shoppingCart.ordersPanel.orderedStatus')}</StepLabel></Step>
                        <Step><StepLabel>{t('shoppingCart.ordersPanel.cookingStatus')}</StepLabel></Step>
                        <Step><StepLabel>{t('shoppingCart.ordersPanel.deliveringStatus')}</StepLabel></Step>
                    </Stepper>
                </TableCell>  
            }                
        </TableRow>
    );
}

export default OrdersItem;