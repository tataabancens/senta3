import { Button, Grid, Paper, Typography } from "@mui/material";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { NavigateFunction, useNavigate } from "react-router-dom";
import { paths } from "../constants/constants";
import { ReservationModel } from "../models";

type Props = {
    reservation: ReservationModel,
    reservationType: string    
};

const ReservationCard: FC<Props> = ({reservation, reservationType}): JSX.Element => {

    const navigate = useNavigate();
    const { t } = useTranslation();

    const handleNavigation = () => {
        if(reservationType === "ACTIVE"){
            navigate(paths.ROOT + '/reservations/' + reservation.securityCode)
        }else if(reservationType === "FINISHED"){
            navigate(paths.ROOT + '/reservations/' + reservation.securityCode + '/checkOut')
        }
    }

    const formatDate = (date: string) => {
        const dateParts: string[] = date.split("-");
        const year: string = dateParts[0];
        const month: string = dateParts[1];
        const day: string = dateParts[2];
    
        return `${day}/${month}/${year}`;
    };
    
    return(
        <Grid item container component={Paper} xs={2.5} elevation={4} padding={1.5} margin={1} sx={{ minHeight: 100 }}>
              <Grid item xs={12}><Typography variant="h5" align="center">{reservation.customerName}</Typography></Grid>
              <Grid item xs={6}><Typography variant="body1" align="left">{t('customerReservations.reservationCard.date')}{formatDate(reservation.date)}</Typography></Grid>
              <Grid item xs={6}><Typography variant="body1" align="right">{t('customerReservations.reservationCard.hour',{hour: reservation.hour})}</Typography></Grid>
              <Grid item xs={6}><Typography variant="body1" align="left">{t('customerReservations.reservationCard.people',{people: reservation.peopleAmount})}</Typography></Grid>
              <Grid item xs={6}><Typography variant="body1" align="right">{t('customerReservations.reservationCard.code',{code: reservation.securityCode})}</Typography></Grid>
              <Grid item xs={12}>
                <Button color="primary" variant="contained" fullWidth onClick={handleNavigation}>
                  {t('customerReservations.enterButton')}
                </Button>
              </Grid>
        </Grid>
    );
}

export default ReservationCard;