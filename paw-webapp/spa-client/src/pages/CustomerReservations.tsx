import {
  Grid,
  Paper,
  Typography
} from "@mui/material";
import useAuth from '../hooks/serviceHooks/authentication/useAuth';
import { FC } from "react";
import { CustomerModel } from "../models";
import { useReservations } from "../hooks/serviceHooks/reservations/useReservations";
import ReservationCard from "../components/ReservationCard";
import { useTranslation } from "react-i18next";
import { usePoints } from "../hooks/serviceHooks/customers/usePoints";



const CustomerReservations: FC = () => {
  const { auth } = useAuth();
  const { content } = auth as { content: CustomerModel };
  const { points } = usePoints(content);
  const { t } = useTranslation();

  const activeFilterStatus = "0"
  const { reservations: activeReservations, error: activeReservationsError, loading: activeReservationsLoading } = useReservations(content.id, activeFilterStatus);
  const pendingFilterStatus = "2"
  const { reservations: pendingReservations, error: pendingReservationsError, loading: pendingReservationsLoading } = useReservations(content.id, pendingFilterStatus);
  const finishedFilterStatus = "3"
  const { reservations: finishedReservations, error: finishedReservationsError, loading: finishedReservationsLoading } = useReservations(content.id, finishedFilterStatus);

  return (
    <Grid container justifyContent="center" xs={12} padding={3}>
      <Grid item container component={Paper} elevation={5} xs={12} padding={2} borderRadius={2} justifyContent="space-between" alignItems="center">
        <Grid item xs={6}><Typography variant="h4">{content.name}</Typography></Grid>
        <Grid item xs={6}><Typography variant="h4" align="right">{t('customerReservations.points',{points: points?.points})}</Typography></Grid>
      </Grid>
      <Grid item container xs={12} component={Paper} elevation={5} borderRadius={2} marginTop={3} sx={{ minHeight: 350 }} padding={2}>
        <Grid item xs={12}><Typography variant="h5" align="center">{t('customerReservations.activeReservationsTitle')}</Typography></Grid>
          {activeReservations.map(reservation => <ReservationCard reservation={reservation} reservationType={"ACTIVE"} />)}
      </Grid>
      <Grid item container xs={12} component={Paper} elevation={5} borderRadius={2} marginTop={3} sx={{ minHeight: 350 }} padding={2}>
        <Grid item xs={12}><Typography variant="h5" align="center">{t('customerReservations.pendingReservationsTitle')}</Typography></Grid>
          {pendingReservations.map(reservation => <ReservationCard reservation={reservation} reservationType={"PENDING"} />)}
      </Grid>
      <Grid item container xs={12} component={Paper} elevation={5} padding={2} borderRadius={2} marginTop={3} sx={{ minHeight: 350 }}>
        <Grid item xs={12}><Typography variant="h5" align="center" marginBottom={2}>{t('customerReservations.oldReservationsTitle')}</Typography></Grid>
        <Grid item container xs={12} spacing={2}>
          {finishedReservations.map(reservation => <ReservationCard key={reservation.securityCode} reservation={reservation} reservationType={"FINISHED"}/>)}
        </Grid>
      </Grid>
    </Grid>
  );
}

export default CustomerReservations;

