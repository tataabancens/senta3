import {
  Paper,
  Tab,
  Tabs,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Pagination,
  TableFooter,
  Stack,
  Typography,
  Switch,
  Grid,
} from "@mui/material";
import { useEffect, useState } from "react";
import { handleResponse } from "../Utils";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import { ReservationModel } from "../models";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { useLocation, useNavigate } from "react-router-dom";
import ReservationRow from "../components/ReservationRow";

function Reservations() {
  const [value, setValue] = useState(9);
  const [page, setPage] = useState(1);
  const [reservationList, setReservations] = useState<ReservationModel[]>([]);
  const [reload, setReload] = useState(false);
  const [sortDirection, setSorting] = useState(false);

  const reservationService = useReservationService();
  const navigate = useNavigate();
  const location = useLocation();

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  const handleSwitchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSorting(event.target.checked);
  };

  const handlePagination = (
    event: React.ChangeEvent<unknown>,
    page: number
  ) => {
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
        (response) => {response.length > 0? setReservations(response) : setReservations([]);},
        navigate,
        location
      );
    },[value, page, reload, sortDirection]);

  return (
      <Grid container xs={12}>
        <Grid item xs={10}>
          <Tabs
            value={value}
            onChange={handleChange}
            variant="scrollable"
            scrollButtons="auto"
            aria-label="scrollable auto tabs example"
          >
          <Tab value={9} label="ALL" />
          <Tab value={0} label="OPEN" />
          <Tab value={1} label="SEATED" />
          <Tab value={2} label="CHECK ORDERED" />
          <Tab value={3} label="FINISHED" />
          <Tab value={4} label="CANCELLED" />
          </Tabs>
        </Grid>
        <Grid item container xs={2} justifyContent="flex-start">
          <Stack direction="row" alignItems="center" spacing={3}>
            <Typography variant="subtitle1">SortBy:</Typography>
            <Stack direction="row" spacing={2} alignItems="center">
              <Typography>Asc</Typography>
              <Switch checked={sortDirection} onChange={handleSwitchChange} color="primary" />
              <Typography>Desc</Typography>
            </Stack>
          </Stack>
        </Grid>
        <Grid item xs={12}>
          <TableContainer component={Paper}>
            <Table sx={{ width: 1 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>Sec.Code</TableCell>
                  <TableCell>Customer</TableCell>
                  <TableCell align="center">Date</TableCell>
                  <TableCell align="center">Hour</TableCell>
                  <TableCell align="center">Table nmbr</TableCell>
                  <TableCell align="center">People</TableCell>
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
          </TableContainer>
        </Grid>
      </Grid>
  );
}

export default Reservations;
