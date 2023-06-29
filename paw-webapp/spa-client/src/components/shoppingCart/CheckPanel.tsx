import { Button, Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { FC, useContext, useState } from "react";
import { useTranslation } from "react-i18next";
import { ReservationContext } from "../../context/ReservationContext";
import useReservationService from "../../hooks/serviceHooks/reservations/useReservationService";
import { OrderItemModel } from "../../models";
import { ReservationParams } from "../../models/Reservations/ReservationParams";
import { handleResponse } from "../../Utils";
import ShoppingCartItem from "./ShoppingCartItem";

type Props = {
    children?: React.ReactNode;
    index: number;
    value: number;
    orderItems: OrderItemModel[];
}

const CheckPanel: FC<Props> = (props: Props) => {
    const { children, value, index, orderItems, ... other } = props;
    const { t } = useTranslation();
    const itemsToPay = orderItems.filter(orderItem => (orderItem.status !== "SELECTED" && orderItem.status !== "CANCELED"));
    const { reservation, updateReservation } = useContext(ReservationContext);
    const [isButtonDisabled, setIsButtonDisabled] = useState(false);
    const reservationService = useReservationService();

    const calculateTotal = () =>{
        let total = 0;
        itemsToPay.map(item => { total += item.quantity * item.unitPrice})
    
        return total;
    }

    const orderCheck = () => {
        setIsButtonDisabled(true);
        let updateReservationParams = new ReservationParams();
        updateReservationParams.securityCode = reservation?.securityCode;
        updateReservationParams.status = "CHECK_ORDERED";
        handleResponse(
            reservationService.patchReservation(updateReservationParams),
            (response) => {updateReservation()}
        )
    }
    
    return (
        <div
        role="tabpanel"
        hidden={value !== index}
        {...other}
      >
        {value === index && (
            <Grid container xs={12}>
                <Grid item xs={12} component={Table}>
                    <TableHead>
                        <TableRow>
                            <TableCell align="left">{t('shoppingCart.tableHeaders.dish')}</TableCell>
                            <TableCell align="center">{t('shoppingCart.tableHeaders.qty')}</TableCell>
                            <TableCell align="center">{t('shoppingCart.tableHeaders.subtotal')}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {itemsToPay.map(item => <ShoppingCartItem key={item.orderItemId} 
                        securityCode={reservation!.securityCode}
                        orderItem={item}
                        isCartItem={false}/>)}
                    </TableBody>
                </Grid>
                <Grid item container xs={12} sx={{marginTop: 3}}>
                    <Grid item xs={3}></Grid>
                    <Grid item xs={5}><Typography variant="h6">Total:</Typography></Grid>
                    <Grid item xs={3}><Typography variant="h6">${calculateTotal()}</Typography></Grid>
                </Grid>
                <Grid item container xs ={12} sx={{marginTop: 3}} justifyContent="center">
                    <Grid item xs={8}>
                        {itemsToPay.length > 0 && reservation?.status === "SEATED"? 
                         <Button fullWidth variant="contained" color="success" size="large" disabled={isButtonDisabled} onClick={orderCheck}>{t('shoppingCart.checkPanel.orderCheck')}</Button> :
                         <Button fullWidth disabled variant="contained" color="success" size="large" onClick={orderCheck}>{t('shoppingCart.checkPanel.orderCheck')}</Button>}
                    </Grid>
                </Grid>
            </Grid>
        )}
      </div>
    );
}

export default CheckPanel;