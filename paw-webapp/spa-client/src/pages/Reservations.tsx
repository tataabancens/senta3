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
} from "@mui/material";
import { useEffect, useState } from "react";
import { handleResponse } from "../Utils";
import useReservationService from "../hooks/serviceHooks/useReservationService";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { ReservationModel } from "../models";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { useLocation, useNavigate } from "react-router-dom";
import ReservationRow from "../components/ReservationRow";

function Reservations() {
  const [value, setValue] = useState(9);
  const [page, setPage] = useState(1);
  const [reservationList, setReservations] = useState<ReservationModel[]>([]);
  const [reload, setReload] = useState(false);
  const axiosPrivate = useAxiosPrivate();

  const reservationService = useReservationService();

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };
    const navigate = useNavigate();
    const location = useLocation();

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
      setReservations([]);
      let reservationParams = new ReservationParams();
      reservationParams.filterStatus = value.toString();
      reservationParams.page = page;     
        handleResponse(
            reservationService.getReservations(reservationParams),
            (response) => {
              if(response.length > 0)
                setReservations(response)
              },
            navigate,
            location
        );
    },[value, page, reload]);

  return (
    <>
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
      <TableContainer component={Paper}>
        <Table sx={{ width: 1 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Sec.Code</TableCell>
              <TableCell>Customer</TableCell>
              <TableCell>Date</TableCell>
              <TableCell>Hour</TableCell>
              <TableCell>Table nmbr</TableCell>
              <TableCell>People</TableCell>
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
    </>
  );
}

export default Reservations;
