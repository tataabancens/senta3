import { Button, Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { FC, useContext, useState } from "react";
import { useTranslation } from "react-i18next";
import { ReservationContext } from "../../context/ReservationContext";
import useAuth from "../../hooks/serviceHooks/authentication/useAuth";
import useReservationService from "../../hooks/serviceHooks/reservations/useReservationService";
import { OrderItemModel } from "../../models";
import { UserRoles } from "../../models/Enums/UserRoles";
import { ReservationParams } from "../../models/Reservations/ReservationParams";
import FinishReservationModal from "../FinishReservationModal";
import ShoppingCartItem from "./ShoppingCartItem";
import useServiceProvider from "../../context/ServiceProvider";

type Props = {
    children?: React.ReactNode;
    index: number;
    value: number;
    orderItems: OrderItemModel[];
}

const CheckPanel: FC<Props> = (props: Props) => {
    const { children, value, index, orderItems, ... other } = props;
    const { t } = useTranslation();
    const { auth } = useAuth();
    const itemsToPay = orderItems.filter(orderItem => (orderItem.status !== "SELECTED" && orderItem.status !== "CANCELED"));
    const { reservation, updateReservation, discount, restaurant } = useContext(ReservationContext);
    const [isButtonDisabled, setIsButtonDisabled] = useState(false);
    const [openModal, setOpenModal] = useState(false);
    const { reservationService: rs } = useServiceProvider();

    const calculateTotal = () =>{
        let total = 0;
        itemsToPay.map(item => {
            total += item.quantity * item.unitPrice
        })
    
        return total;
    }

    const calculateDiscountedTotal = () => {
        const total = calculateTotal();

        return total * (1-restaurant!.discountCoefficient);
    }

    const orderCheck = async () => {
        setIsButtonDisabled(true);
        let updateReservationParams = new ReservationParams();
        updateReservationParams.securityCode = reservation?.securityCode;
        updateReservationParams.status = "CHECK_ORDERED";
        if(discount){
            updateReservationParams.discount = true;
        }
        const { isOk } = await rs.patchReservation(updateReservationParams);
        if(isOk){
            updateReservation();
        }
        if(auth.roles[0] !== UserRoles.RESTAURANT){
            setOpenModal(true);
        }
    }
    const handleOpen = () => {
        setOpenModal(!openModal);
    }
    
    return (
        <>
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
                            isCartItem={false}
                            usedDiscount={discount}
                            />)}
                        </TableBody>
                    </Grid>
                    <Grid item container xs={12} sx={{marginTop: 3}}>
                        <Grid item xs={3}></Grid>
                        <Grid item xs={5}><Typography variant="h6">Total:</Typography></Grid>
                        {discount && 
                        <Grid item xs={1}>
                             <Typography variant="h6" color="blue">${calculateDiscountedTotal()}</Typography>
                        </Grid>}
                        <Grid item xs={1.5}>
                            <Typography variant="h6" >{discount? "" : "$"}{calculateTotal()}</Typography>
                        </Grid>
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
            <FinishReservationModal isOpen={openModal} handleOpen={handleOpen} total={calculateTotal()} />
        </>
    );
}

export default CheckPanel;