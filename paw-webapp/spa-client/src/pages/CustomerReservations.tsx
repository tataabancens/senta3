import {
  CircularProgress,
  Grid,
  Paper,
  Typography
} from "@mui/material";
import useAuth from '../hooks/serviceHooks/authentication/useAuth';
import { FC, useEffect, useState } from "react";
import { CustomerModel, ReservationModel } from "../models";
import { useReservations } from "../hooks/serviceHooks/reservations/useReservations";
import ReservationCard from "../components/ReservationCard";
import { useTranslation } from "react-i18next";
import { usePoints } from "../hooks/serviceHooks/customers/usePoints";



const CustomerReservations: FC = () => {
  const { auth } = useAuth();
  const { content } = auth as { content: CustomerModel };
  const { points } = usePoints(content);
  const [reservationsToDisplay, setReservations ] = useState<ReservationModel[] | undefined>()
  const { t } = useTranslation();

  const activeFilterStatus = "0"
  const { reservations: activeReservations } = useReservations(content.id, activeFilterStatus);
  const seatedFilterStatus = "1"
  const { reservations: seatedReservations } = useReservations(content.id, seatedFilterStatus);
  const pendingFilterStatus = "2"
  const { reservations: pendingReservations} = useReservations(content.id, pendingFilterStatus);
  const finishedFilterStatus = "3"
  const { reservations: finishedReservations } = useReservations(content.id, finishedFilterStatus);

  useEffect(() => {
    if(seatedReservations) { 
      var union = seatedReservations;
      if(activeReservations){
        union = seatedReservations.concat(activeReservations);
      }
      setReservations(union);
    }
  },[activeReservations, seatedReservations])

  return (
    <Grid container justifyContent="center" xs={12} padding={3}>
      <Grid item container component={Paper} elevation={5} xs={12} padding={2} borderRadius={2} justifyContent="space-between" alignItems="center">
        <Grid item xs={6}><Typography variant="h4">{content.name}</Typography></Grid>
        <Grid item xs={6}><Typography variant="h4" align="right">{t('customerReservations.points',{points: points?.points})}</Typography></Grid>
      </Grid>
      <Grid item container xs={12} component={Paper} elevation={5} borderRadius={2} marginTop={3} sx={{ minHeight: 350 }} padding={2}>
        <Grid item xs={12}><Typography variant="h5" align="center">{t('customerReservations.activeReservationsTitle')}</Typography></Grid>
          {reservationsToDisplay && reservationsToDisplay.map(reservation => <ReservationCard reservation={reservation} reservationType={"ACTIVE"} />)}
          {!reservationsToDisplay && <Grid item xs={12} sx={{display:"flex", justifyContent:"center"}}><CircularProgress /></Grid>}
          {reservationsToDisplay && reservationsToDisplay.length === 0 && <Grid item xs={12}><Typography variant="h6" align="center">{t('customerReservations.noReservations')}</Typography></Grid>}
      </Grid>
      <Grid item container xs={12} component={Paper} elevation={5} borderRadius={2} marginTop={3} sx={{ minHeight: 350 }} padding={2}>
        <Grid item xs={12}><Typography variant="h5" align="center">{t('customerReservations.pendingReservationsTitle')}</Typography></Grid>
          {pendingReservations && pendingReservations.map(reservation => <ReservationCard reservation={reservation} reservationType={"PENDING"} />)}
          {!pendingReservations && <Grid item xs={12} sx={{display:"flex", justifyContent:"center"}}><CircularProgress /></Grid>}
          {pendingReservations && pendingReservations.length === 0 && <Grid item xs={12}><Typography variant="h6" align="center">{t('customerReservations.noReservations')}</Typography></Grid>}
      </Grid>
      <Grid item container xs={12} component={Paper} elevation={5} padding={2} borderRadius={2} marginTop={3} sx={{ minHeight: 350 }}>
        <Grid item xs={12}><Typography variant="h5" align="center" marginBottom={2}>{t('customerReservations.oldReservationsTitle')}</Typography></Grid>
        <Grid item container xs={12} spacing={2}>
          {finishedReservations && finishedReservations.map(reservation => <ReservationCard key={reservation.securityCode} reservation={reservation} reservationType={"FINISHED"}/>)}
          {!finishedReservations && <Grid item xs={12} sx={{display:"flex", justifyContent:"center"}}><CircularProgress /></Grid>}
          {finishedReservations && finishedReservations.length === 0 && <Grid item xs={12}><Typography variant="h6" align="center">{t('customerReservations.noReservations')}</Typography></Grid>}
        </Grid>
      </Grid>
    </Grid>
  );
}

export default CustomerReservations;

