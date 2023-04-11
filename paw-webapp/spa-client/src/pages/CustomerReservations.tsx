import {
  Button,
  Grid,
  Paper,
  Typography
} from "@mui/material";
import {CustomerModel, ReservationModel, UserModel} from "../models";
import {useEffect, useState} from "react";
import useUserService from "../hooks/serviceHooks/useUserService";
import useCustomerService from "../hooks/serviceHooks/useCustomerService";
import useAuth from '../hooks/useAuth';
import useReservationService from "../hooks/serviceHooks/useReservationService";
import {useNavigate} from "react-router-dom";
import {awaitWrapper, handleResponse} from "../Utils";
import {AxiosResponse} from "axios";
import {extractCustomerIdFromContent} from "./SignUpPage";
import {ReservationParams} from "../models/Reservations/ReservationParams";
import {paths} from "../constants/constants";


function CustomerReservations() {
  const { auth } = useAuth();
  let navigate = useNavigate();

  const [user, setUser] = useState<UserModel>();
  const [customer, setCustomer] = useState<CustomerModel>();
  const [activeReservations, setActiveReservations] = useState<Array<ReservationModel>>();
  const [finishedReservations, setFinishedReservations] = useState<Array<ReservationModel>>();
  const [cancelledReservations, setCancelledReservations] = useState<Array<ReservationModel>>();



  const userService = useUserService();
  const customerService = useCustomerService();
  const reservationService = useReservationService();

  const activeResParams: ReservationParams = new ReservationParams();
  const finishedResParams: ReservationParams = new ReservationParams();
  const cancelledResParams: ReservationParams = new ReservationParams();




  useEffect(() => {
    if(user == undefined){
      handleResponse(userService.getUserById(auth.id), (user: UserModel) =>
          setUser(user)
      );
    }
  });

  useEffect(() => {
    if(user != undefined && customer == undefined) {
      const custId = extractCustomerIdFromContent(user.content);
      handleResponse(customerService.getCustomerById(custId), (customer: CustomerModel) =>
          setCustomer(customer)
      );
    }
  });

  if(customer != undefined && activeReservations == undefined){
    activeResParams.customerId = customer.id;
    activeResParams.filterStatus = "0";
    handleResponse(reservationService.getReservations(activeResParams), (activeReservations: Array<ReservationModel>) =>
        setActiveReservations(activeReservations)
    );
  }

  if(customer != undefined && finishedReservations == undefined){
    finishedResParams.customerId = customer.id;
    finishedResParams.filterStatus = "3";
    handleResponse(reservationService.getReservations(finishedResParams), (finishedReservations: Array<ReservationModel>) =>
        setFinishedReservations(finishedReservations)
    );
  }
  if(customer != undefined && cancelledReservations == undefined){
    cancelledResParams.customerId = customer.id;
    cancelledResParams.filterStatus = "4";
    handleResponse(reservationService.getReservations(cancelledResParams), (cancelledReservations: Array<ReservationModel>) =>
        setCancelledReservations(cancelledReservations)
    );
  }

  return (
      <Grid
          item
          container
          justifyContent="space-around"
          sx={{ height: "40vh" }}
      >
        <Grid //POINTS
            item
            container
            xs={11}
            sm={11}
            component={Paper}
            borderRadius={3}
            elevation={3}
            padding={2}
        >
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Points: {customer?.points}</Grid>
        </Grid>

        <Grid //ACTIVE RESERVATIONS
            item
            container
            xs={6}
            sm={6}
            component={Paper}
            borderRadius={3}
            elevation={3}
            padding={2}
        >
          <Grid item component={Typography} variant="h4" xl={11} lg={11} md={11} sm={12} xs={12} marginBottom={4}>Active reservations:</Grid>
          <Grid container spacing={2}>
            {activeReservations && activeReservations.length > 0 ? (
              activeReservations?.map((reservation) => (
                <Grid item key={reservation.securityCode} xs={6} sm={3}>
                  <Button onClick={() => {navigate(paths.RESERVATIONS + "/" + reservation.securityCode)}}>
                    date:{reservation.date} hour:{reservation.hour}hs code:{reservation.securityCode}
                  </Button>
                </Grid>
            ))
            ) : (
                <Grid item xs={12}>
                  No active reservations found.
                </Grid>
            )}
          </Grid>
        </Grid>

        <Grid //HISTORY
            item
            container
            xs={6}
            sm={6}
            component={Paper}
            borderRadius={3}
            elevation={3}
            padding={2}
        >
          <Grid item component={Typography} variant="h4" xl={11} lg={11} md={11} sm={12} xs={12} marginBottom={4}>History:</Grid>
          <Grid container spacing={2}>
            Finished:
            {finishedReservations && finishedReservations.length > 0 ? (
                finishedReservations?.map((reservation) => (
                <Grid item key={reservation.securityCode} xs={6} sm={3}>
                  <Button onClick={() => {navigate(paths.RESERVATIONS + "/" + reservation.securityCode + "/checkout")}}>
                    {reservation.securityCode}
                  </Button>
                </Grid>
            ))
            ) : (
            <Grid item xs={12}>
              No finished past reservations found.
            </Grid>
            )}

            Cancelled:
            {cancelledReservations && cancelledReservations.length > 0 ? (
                cancelledReservations?.map((reservation) => (
                    <Grid item key={reservation.securityCode} xs={6} sm={3}>
                      {reservation.securityCode}
                    </Grid>
                ))
            ) : (
                <Grid item xs={12}>
                  No cancelled past reservations found.
                </Grid>
            )}
          </Grid>
        </Grid>

      </Grid>

  );
}

export default CustomerReservations;
