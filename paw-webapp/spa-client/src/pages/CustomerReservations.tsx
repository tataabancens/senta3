import {
  Grid,
  Paper,
  Typography
} from "@mui/material";
import useAuth from '../hooks/useAuth';
import { FC } from "react";
import { CustomerModel, ReservationModel, UserModel } from "../models";
import { useReservations } from "../hooks/serviceHooks/reservations/useReservations";


const CustomerReservations: FC = () => {
  const { auth } = useAuth();
  const { content: customer } = auth as { content: CustomerModel };

  const activeFilterStatus = "0"
  const { reservations: activeReservations, error: activeReservationsError, loading: activeReservationsLoading } = useReservations(customer.id, activeFilterStatus);

  const finishedFilterStatus = "3"
  const { reservations: finishedReservations, error: finishedReservationsError, loading: finishedReservationsLoading } = useReservations(customer.id, finishedFilterStatus);

  return (
    <Grid container justifyContent="center" xs={12} padding={3}>
      <Grid item container component={Paper} elevation={5} xs={12} padding={2} borderRadius={2} justifyContent="space-between" alignItems="center">
        <Grid item xs={6}><Typography variant="h4">{customer?.name}</Typography></Grid>
        <Grid item xs={6}><Typography variant="h4" align="right">Points: {customer?.points}</Typography></Grid>
      </Grid>
      <Grid item container xs={12} component={Paper} elevation={5} padding={2} borderRadius={2} marginTop={3} sx={{ minHeight: 300 }}>
        <Grid item xs={12}><Typography variant="h5" align="center">Active Reservations</Typography></Grid>
        <Grid item container xs={12} spacing={2}>
          {activeReservations.map(reservation =>
            <Grid item container component={Paper} xs={3} elevation={4}>
              <Grid item xs={12}><Typography>date: {reservation.date}</Typography></Grid>
              <Grid item xs={12}><Typography>hour: {reservation.hour}:00</Typography></Grid>
              <Grid item xs={12}><Typography>people: {reservation.peopleAmount}</Typography></Grid>
            </Grid>)}
        </Grid>
      </Grid>
      <Grid item container xs={12} component={Paper} elevation={5} padding={2} borderRadius={2} marginTop={3} sx={{ minHeight: 300 }}>
        <Grid item xs={12}><Typography variant="h5" align="center">Finished Reservations</Typography></Grid>
        <Grid item container xs={12} spacing={2}>
          {finishedReservations.map(reservation =>
            <Grid item container component={Paper} xs={3} elevation={4}>
              <Grid item xs={12}><Typography>date: {reservation.date}</Typography></Grid>
              <Grid item xs={12}><Typography>hour: {reservation.hour}:00</Typography></Grid>
              <Grid item xs={12}><Typography>people: {reservation.peopleAmount}</Typography></Grid>
            </Grid>)}
        </Grid>
      </Grid>
    </Grid>
  );
}

export default CustomerReservations;

