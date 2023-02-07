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
  Grid,
} from "@mui/material";
import axios, { AxiosResponse } from "axios";
import { useEffect, useState } from "react";
import reservationRow from "../components/reservationRow";
import { handleResponse } from "../handleResponse";
import useReservationService from "../hooks/serviceHooks/useReservationService";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { CustomerModel, ReservationModel } from "../models";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { useLocation, useNavigate } from "react-router-dom";

function Reservations() {
  const [value, setValue] = useState(9);
  const [page, setPage] = useState(1);
  const [reservationList, setReservations] = useState<ReservationModel[]>([]);
  const [customerList, setCustomers] = useState<CustomerModel[]>([]);
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

  useEffect(() => {
    let reservationParams = new ReservationParams();
    reservationParams.filterStatus = value.toString();
    reservationParams.page = page;
    handleResponse(
      reservationService.getReservations(reservationParams),
      (response: ReservationModel[]) => {
        response.length > 0 ? setReservations(response) : setReservations([]);
      }
    );
  }, [value, page]);
    useEffect(() => {   
      let reservationParams = new ReservationParams();
      reservationParams.filterStatus = value.toString();
      reservationParams.page = page;     
        handleResponse(
            reservationService.getReservations(reservationParams),
            (response) => setReservations(response),
            navigate,
            location
        );
    },[value]);

  useEffect(() => {
    setCustomers([]);
    console.log(reservationList);
    reservationList.forEach((reservation) => {
      axios
        .get(reservation.customer)
        .then((response: AxiosResponse<CustomerModel>) => {
          setCustomers((customerList) => customerList.concat(response.data));
        })
        .catch((err) => console.log(err));
    });
    console.log(customerList);
  }, [reservationList]);

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
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Sec.Code</TableCell>
              <TableCell align="right">Customer</TableCell>
              <TableCell align="right">Date</TableCell>
              <TableCell align="right">Hour</TableCell>
              <TableCell align="right">Table nmbr</TableCell>
              <TableCell align="right">People</TableCell>
              <TableCell align="right">Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {reservationList.map((reservation: ReservationModel, i) =>
              reservationRow(
                reservation,
                customerList.length > 0 ? customerList[i] : undefined
              )
            )}
          </TableBody>
        </Table>
      </TableContainer>
      <Grid container justifyContent="center" position="fixed" bottom={16}>
        <Grid
          item
          component={Pagination}
          count={10}
          page={page}
          color="primary"
          onChange={handlePagination}
        />
      </Grid>
    </>
  );
}

export default Reservations;
