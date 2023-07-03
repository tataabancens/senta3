import { Grid, Pagination, Paper, Table, TableBody, TableCell, TableContainer, TableFooter, TableHead, TableRow } from "@mui/material";
import { FC, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import { ReservationModel } from "../models";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { handleResponse } from "../Utils";
import ReservationRow from "./ReservationRow";

type Props = {
    sortDirection: boolean,
    value: number
};

const ReservationsTable: FC<Props> = ({sortDirection, value}) =>{
    const { t } = useTranslation();
    const [page, setPage] = useState(1);
    const [reload, setReload] = useState(false);
    const [reservationList, setReservations ] = useState<ReservationModel[]>([]);
    const reservationService = useReservationService();

    const handlePagination = (event: React.ChangeEvent<unknown>,page: number) => {
        setPage(page);
    };
    const toggleReload = () => {
        setReload(!reload);
    }

    useEffect(() => {
        let reservationParams = new ReservationParams();
        reservationParams.filterStatus = value.toString();
        reservationParams.page = page;
        sortDirection? reservationParams.direction = "DESC" :  reservationParams.direction = "ASC";   
        handleResponse(
          reservationService.getReservations(reservationParams),
          (response) => {response.length > 0? setReservations(response) : setReservations([]);}
        );
    },[value, page, reload, sortDirection]);

    return(
        <Grid item xs ={12}>
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
                        {reservationList?.map((reservation: ReservationModel, i) => <ReservationRow key={i} reservation={reservation}  toggleReload={toggleReload}/>)}
                    </TableBody>
                    <TableFooter>
                        <TableRow>
                            <Pagination sx={{marginY: 1}} count={10} page={page} color="primary" onChange={handlePagination} />
                        </TableRow>
                    </TableFooter>
                </Table> 
            </TableContainer>
        </Grid>
    );
}

export default ReservationsTable;