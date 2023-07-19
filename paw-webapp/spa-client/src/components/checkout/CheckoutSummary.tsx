import { Alert, Button, Grid, Paper, Snackbar, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { paths } from "../../constants/constants";
import useReservationService from "../../hooks/serviceHooks/reservations/useReservationService";
import { OrderItemModel, ReservationModel } from "../../models";
import { ReservationParams } from "../../models/Reservations/ReservationParams";
import { useCustomer } from "../../hooks/serviceHooks/customers/useCustomer";
import { UserRoles } from "../../models/Enums/UserRoles";
import { useRestaurant } from "../../hooks/serviceHooks/restaurants/useRestaurant";
import useAuth from "../../hooks/serviceHooks/authentication/useAuth";
import useCustomerService from "../../hooks/serviceHooks/customers/useCustomerService";
import CheckoutItem from "./CheckoutItem";
import useServices from "../../hooks/useServices";

type Props = {
    reservation: ReservationModel,
    orderItems: OrderItemModel[]
};

const CheckoutSummary: FC<Props> = ({ reservation, orderItems }) => {

    const { t, i18n } = useTranslation();
    const { restaurant } = useRestaurant(1);
    const {customerService: cs, reservationService: rs } = useServices();
    const navigate = useNavigate();
    const { customer, points } = useCustomer(reservation?.customer);
    const [endReservationPressed, setEndReservationPressed] = useState(false);
    const { auth } = useAuth();
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const textDecoration = reservation?.usedDiscount ? 'line-through' : 'none';


    const formatDate = (date: string) => {
        const dateParts: string[] = date.split("-");
        const year: string = dateParts[0];
        const month: string = dateParts[1];
        const day: string = dateParts[2];
        if(i18n.language.includes("en",0)){
            return `${month}/${day}/${year}`;
        }else if(i18n.language.includes("es",0)){
            return `${day}/${month}/${year}`;
        }
        return `${month}/${day}/${year}`;
    };

    const calculateTotal = (orderItems: OrderItemModel[]) =>{
        let total = 0;
        orderItems.filter(orderItem => orderItem.status !== "SELECTED" && orderItem.status !== "DELETED").map(filteredItem => { total += filteredItem.quantity * filteredItem.unitPrice})
    
        return total;
    }

    const endReservation = async () => {
        setEndReservationPressed(true);
        let resParams = new ReservationParams();
        resParams.securityCode = reservation.securityCode;
        resParams.status = "FINISHED";
        const { isOk } = await rs.patchReservation(resParams);
        if(isOk){
            setSnackbarOpen(true);
            let pointsToPatch = points!.points + restaurant!.addPointsCoefficient * calculateTotal(orderItems!);
            if(reservation?.usedDiscount && restaurant){
                pointsToPatch -= restaurant.pointsForDiscount
            }
            await cs.patchPoints(customer!.id, pointsToPatch);
        }
    }

    const handleSnackbarClose = () => {
        setSnackbarOpen(false);
        navigate(paths.ROOT + "/restaurantReservations");
    };

    return (
        <>
                <Grid item container component={Paper} xs={11} marginTop={2} padding={3} elevation={5} borderRadius={3}>
            <Grid item xs={12}><Typography variant="h4" align="center" color="primary">{t('checkoutSummary.title')}</Typography></Grid>
            <Grid item container xs={12} marginTop={6}>
                <Grid item xs={12}><Typography variant="h5">{t('checkoutSummary.reservationInfo')}</Typography></Grid>
                <Grid item xs={12}><Typography variant="subtitle1">{t('checkoutSummary.customer',{customer: reservation?.customerName})}</Typography></Grid>  
                <Grid item xs={12}><Typography variant="subtitle1">{t('checkoutSummary.date')}{formatDate(reservation?.date)}</Typography></Grid>
                <Grid item xs={12}><Typography variant="subtitle1">{t('checkoutSummary.hour',{hour: reservation?.hour})}</Typography></Grid>  
                <Grid item xs={12}><Typography variant="subtitle1">{t('checkoutSummary.people',{people: reservation?.peopleAmount})}</Typography></Grid>
                <Grid item xs={12}><Typography variant="subtitle1">{t('checkoutSummary.table',{table: reservation?.tableNumber})}</Typography></Grid>
            </Grid>
            <Grid item xs={12}><Typography variant="h5" align="center">{t('checkoutSummary.itemSummary')}</Typography></Grid>
            <Grid item container xs={12}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell align="left">{t('checkoutSummary.tableHeaders.dish')}</TableCell>
                            <TableCell align="center">{t('checkoutSummary.tableHeaders.qty')}</TableCell>
                            <TableCell align="center">{t('checkoutSummary.tableHeaders.subtotal')}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {orderItems.filter(item => item.status !== "DELETED" && item.status !== "SELECTED").map((filteredItem, i) => (
                            <CheckoutItem key={i} orderItem={filteredItem} restaurant={restaurant} usedDiscount={reservation?.usedDiscount} />
                        ))}
                    </TableBody>
                </Table>
            </Grid>
            <Grid item container xs={12} marginTop={3}>
                <Grid item xs={12} sx={{display:"flex"}} justifyContent="center">
                    <Typography variant="h5" align="center" marginRight={2}>Total:</Typography>
                    <Typography variant="h6" align="center" style={{textDecoration}} marginRight={reservation?.usedDiscount? 1 : 0}>
                        ${calculateTotal(orderItems)}
                    </Typography>
                    {reservation?.usedDiscount && restaurant && <Typography variant="h6" color="blue">${(1-restaurant.discountCoefficient) * calculateTotal(orderItems)}</Typography>}
                </Grid>
            </Grid>
            { auth.roles[0] === UserRoles.RESTAURANT &&
                <Grid item xs={12} marginTop={5}>
                    {!endReservationPressed && <Button variant="contained" fullWidth onClick={endReservation}>{t('checkoutSummary.finishButton')}</Button>}
                    {endReservationPressed && <Button variant="contained" disabled fullWidth onClick={endReservation}>{t('checkoutSummary.finishButton')}</Button>}
                </Grid>
            }
        </Grid>
        <Snackbar open={snackbarOpen} autoHideDuration={1500} onClose={handleSnackbarClose}>
            <Alert onClose={handleSnackbarClose} severity="success">
                {t('checkoutSummary.finishMessage')}
            </Alert>
        </Snackbar>
        </>
    );
}

export default CheckoutSummary;