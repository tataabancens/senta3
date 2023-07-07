import { Alert, Button, Grid, Paper, Snackbar, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { paths } from "../constants/constants";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import useAuth from "../hooks/useAuth";
import { OrderItemModel, ReservationModel } from "../models";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import ShoppingCartItem from "./shoppingCart/ShoppingCartItem";

type Props = {
    reservation: ReservationModel,
    orderItems: OrderItemModel[]
};

const CheckOutSummary: FC<Props> = ({ reservation, orderItems }) => {

    const { t } = useTranslation();
    const { auth } = useAuth();
    const rs = useReservationService();
    const navigate = useNavigate();
    const [snackbarOpen, setSnackbarOpen] = useState(false);


    const formatDate = (date?: string) => {
        if(date){
            const dateParts: string[] = date.split("-");
            const year: string = dateParts[0];
            const month: string = dateParts[1];
            const day: string = dateParts[2];

            return `${day}/${month}/${year}`;
        }
        return 0;
    }

    const calculateTotal = (orderItems: OrderItemModel[]) =>{
        let total = 0;
        orderItems.filter(orderItem => orderItem.status !== "SELECTED" && orderItem.status !== "DELETED").map(filteredItem => { total += filteredItem.quantity * filteredItem.unitPrice})
    
        return total;
    }

    const endReservation = async () => {
        let resParams = new ReservationParams();
        resParams.securityCode = reservation.securityCode;
        resParams.status = "FINISHED";
        const { isOk } = await rs.patchReservation(resParams);
        if(isOk){
            setSnackbarOpen(true);
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
                            <ShoppingCartItem key={i} orderItem={filteredItem} isCartItem={false} />
                        ))}
                    </TableBody>
                </Table>
            </Grid>
            <Grid item container xs={12} marginTop={3}>
                <Grid item xs={6}><Typography variant="h5" align="center" color="primary">Total:</Typography></Grid>
                <Grid item xs ={6}><Typography variant="h5" align="center" color="primary">${calculateTotal(orderItems)}</Typography></Grid>
            </Grid>
            { auth.roles[0] === "ROLE_RESTAURANT" &&
                <Grid item xs={12} marginTop={5}>
                    <Button variant="contained" fullWidth onClick={endReservation}>{t('checkoutSummary.finishButton')}</Button>
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

export default CheckOutSummary;