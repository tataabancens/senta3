import { Button, CircularProgress, Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { useRaisedHands } from "../hooks/serviceHooks/reservations/useRaisedHands";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import { ReservationModel } from "../models";
import { ReservationParams } from "../models/Reservations/ReservationParams";


  
const HandsTable: FC = () => {

    const { t } = useTranslation();
    const { reservations, loading, removeReservation } = useRaisedHands(true, 1);
    const rs = useReservationService();

    const clientAttended = async (reservation: ReservationModel) => {
        let reservationParams = new ReservationParams();
        reservationParams.securityCode = reservation.securityCode;
        reservationParams.hand = false;
        const { isOk } = await rs.patchReservation(reservationParams);
        if(isOk){
            removeReservation(reservation);
        }
    }

    return(
        <Grid item xs={12} paddingX={2}>
        {loading && <Grid item xs={12} sx={{display: "flex",}} justifyContent="center" alignItems="center"><CircularProgress /></Grid>}
        { reservations && reservations.length === 0 &&
            <Typography align="center" variant="h6">{t('kitchenPage.noHands')}</Typography>
        }
        { reservations && reservations.length > 0 &&
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>{t('kitchenPage.handsTableHeaders.table')}</TableCell>
                        <TableCell>{t('kitchenPage.handsTableHeaders.customer')}</TableCell>
                        <TableCell></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {reservations.map((reservation,i) => 
                        <TableRow key={i}>
                            <TableCell>{reservation.tableNumber}</TableCell>
                            <TableCell align="center">{reservation.customerName}</TableCell>
                            <TableCell align="center">
                                <Button size="small" variant="outlined" onClick={() => clientAttended(reservation)}>
                                    {t('kitchenPage.handsActionButton')}
                                </Button>
                            </TableCell>
                        </TableRow>    
                    )}
                </TableBody>
            </Table>
        }
        </Grid> 
    );
}

export default HandsTable;