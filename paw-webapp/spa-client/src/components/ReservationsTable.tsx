import { CircularProgress, Grid, Pagination, Paper, Table, TableBody, TableCell, TableContainer, TableFooter, TableHead, TableRow, Typography } from "@mui/material";
import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { useReservationsPagination } from "../hooks/serviceHooks/reservations/usePagination";
import { ReservationModel } from "../models";
import ReservationRow from "./ReservationRow";

type Props = {
    sortDirection: boolean,
    value: number
};

const ReservationsTable: FC<Props> = ({sortDirection, value}) =>{
    const { t } = useTranslation();
    const [page, setPage] = useState(1);
    const { reservations: reservationList, error: reservationsError, loading: reservationsLoading, toggleReload } = useReservationsPagination(page, value, sortDirection);

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
                            <Pagination sx={{marginY: 1}} count={10} page={page} color="primary" onChange={handlePagination} />
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