import { CallToActionSharp } from "@mui/icons-material";
import { CircularProgress, Grid, Pagination, Paper, SortDirection, Table, TableBody, TableCell, TableContainer, TableFooter, TableHead, TableRow, TableSortLabel, Typography } from "@mui/material";
import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { ReservationsPaginated} from "../hooks/serviceHooks/reservations/usePagination";
import { ReservationModel } from "../models";
import ReservationRow from "./ReservationRow";

type Props = {
    reservationsPaginated: ReservationsPaginated,
    page: number,
    setPage: (page: number) => void;
    setOrderByCriteria: (sortDirection: string, orderBy: string) => void;
};

type Order = "asc" | "desc";

interface HeadCell {
  id: string;
  label: string;
  sortable: boolean;
}

const headCells: HeadCell[] = [
    { id: "code", label: "Code", sortable: false },
    { id: "customer", label: "Customer", sortable: false },
    { id: "date", label: "reservationdate", sortable: true },
    { id: "hour", label: "reservationhour", sortable: true },
    { id: "table", label: "tablenumber", sortable: false },
    { id: "people", label: "qpeople", sortable: false },
    { id: "actions", label: "Actions", sortable: false },
];

const ReservationsTable: FC<Props> = ({reservationsPaginated, setPage, page, setOrderByCriteria}) =>{
    const { t } = useTranslation();
    const [order, setOrder] = useState<Order>('asc');
    const [orderByField, setOrderByField] = useState("code");
    
    const { reservations: reservationList, error: reservationsError, loading: reservationsLoading, toggleReload, lastPage } = reservationsPaginated;

    const handlePagination = (event: React.ChangeEvent<unknown>,page: number) => {
        setPage(page);
    };

    const handleSort = (property: string) => {
        const isAsc = orderByField === property && order === 'asc';
        const orderBy = headCells.find(headCell => headCell.id === property)
        setOrderByCriteria(isAsc ? 'DESC' : 'ASC', orderBy?.sortable? orderBy.label : "reservationid")
        setOrder(isAsc ? 'desc' : 'asc');
        setOrderByField(property);
    };
    
    const createSortHandler = (property: string) => () => {
        handleSort(property);
    };

    return(
        <Grid item xs ={12}>
            {reservationList && !reservationsError && reservationList.length > 0 &&
            <TableContainer component={Paper}>
                <Table sx={{ width: 1 }} aria-label="simple table">
                        <TableRow>
                            {headCells.map((headCell) => (
                            <TableCell key={headCell.id} align={headCell.id === "code"? "left" : "center"}>
                                {headCell.sortable ? (
                                <TableSortLabel
                                    active={orderByField === headCell.id}
                                    direction={orderByField === headCell.id ? order : 'asc'}
                                    onClick={createSortHandler(headCell.id)}
                                >
                                    {t(`reservationsPage.tableHeaders.${headCell.id}`)}
                                </TableSortLabel>
                                ) : (
                                    t(`reservationsPage.tableHeaders.${headCell.id}`)
                                )}
                            </TableCell>
                            ))}
                     </TableRow>
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