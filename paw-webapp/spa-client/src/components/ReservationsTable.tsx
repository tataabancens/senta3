import { CircularProgress, Grid, Pagination, Paper, Table, TableBody, TableCell, TableContainer, TableFooter, TableHead, TableRow, Typography } from "@mui/material";
import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { ReservationsPaginated, useReservationsPagination } from "../hooks/serviceHooks/reservations/usePagination";
import { ReservationModel } from "../models";
import ReservationRow from "./ReservationRow";

type Props = {
    reservationsPaginated: ReservationsPaginated,
    page: number,
    setPage: (page: number) => void;
};

const ReservationsTable: FC<Props> = ({reservationsPaginated, setPage, page}) =>{
    const { t } = useTranslation();
    
    const { reservations: reservationList, error: reservationsError, loading: reservationsLoading, toggleReload, lastPage } = reservationsPaginated;

    const handlePagination = (event: React.ChangeEvent<unknown>,page: number) => {
        setPage(page);
    };

    return(
        <Grid item xs ={12}>
            {reservationList && !reservationsError && reservationList.length > 0 &&
            <TableContainer component={Paper}>
                <Table sx={{ width: 1 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>{t('reservationsPage.tableHeaders.code')}</TableCell>
                            <TableCell>{t('reservationsPage.tableHeaders.customer')}</TableCell>
                            <TableCell align="center">{t('reservationsPage.tableHeaders.date')}</TableCell>
                            <TableCell align="center">{t('reservationsPage.tableHeaders.hour')}</TableCell>
                            <TableCell align="center">{t('reservationsPage.tableHeaders.table')}</TableCell>
                            <TableCell align="center">{t('reservationsPage.tableHeaders.people')}</TableCell>
                            <TableCell align="center"></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {reservationList.map((reservation: ReservationModel, i) => <ReservationRow key={i} reservation={reservation}  toggleReload={toggleReload}/>)}
                    </TableBody>
                    <TableFooter>
                        <TableRow>
                            <Pagination sx={{marginY: 1}} count={lastPage} page={page} color="primary" onChange={handlePagination} />
                        </TableRow>
                    </TableFooter>
                </Table> 
            </TableContainer>}
            {reservationList && reservationList.length === 0 && <Typography variant="h4" align="center" marginTop={30}>{t('reservationsPage.noReservations')}</Typography>}
            {reservationsError && !reservationList && <Typography variant="h4" align="center" marginTop={30}>{t('systemError')}</Typography>}
            {reservationsLoading && !reservationsError &&
            <Grid item xs={12} minHeight={500} sx={{display:"flex"}} alignItems="center" justifyContent="center">
                <CircularProgress />
            </Grid> 
            }
        </Grid>
    );
}

export default ReservationsTable;