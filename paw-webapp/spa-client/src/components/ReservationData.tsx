import { Box, Drawer, Grid, IconButton, Typography } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import { FC } from "react";
import { ReservationModel } from "../models";
import { useTranslation } from "react-i18next";


type Props = {
    reservation: ReservationModel | undefined;
    state: boolean;
    toggleDrawer: () => void;
};

const ReservationData: FC<Props> =({reservation, state, toggleDrawer}) => {
    const { t, i18n } = useTranslation();

    const formatDate = (date: string) => {
        const dateParts: string[] = date.split("-");
        const year: string = dateParts[0];
        const month: string = dateParts[1];
        const day: string = dateParts[2];

        return `${day}/${month}/${year}`;
    };

    return(
        <Drawer anchor="right" open={state} onClose={toggleDrawer}>
          <Grid container component={Box} sx={{ width: 300}} padding={1}>
            <Grid item xs={12} sx={{display:"flex", justifyContent:"flex-end"}}>
                <IconButton onClick={toggleDrawer}>
                    <CloseIcon  color="primary"/>
                </IconButton>
            </Grid>
            <Grid item xs={12} sx={ {display:"flex", justifyContent:"center", marginBottom: 2}}><Typography variant="h5">{t('reservationData.title')}</Typography></Grid>
            <Grid item xs={12} component={Typography} variant="h6">{t('reservationData.customer',{customer: reservation?.customerName})}</Grid>
            <Grid item xs={12} component={Typography} variant="h6">{t('reservationData.code',{code: reservation?.securityCode})}</Grid>
            <Grid item xs={12} component={Typography} variant="h6">{t('reservationData.date', {date: formatDate})}</Grid>
            <Grid item xs={12} component={Typography} variant="h6">{t('reservationData.hour',{hour: reservation?.hour})}</Grid>
            <Grid item xs={12} component={Typography} variant="h6">{t('reservationData.table',{table: reservation?.tableNumber})}</Grid>
          </Grid>
        </Drawer>
    );
}

export default ReservationData;