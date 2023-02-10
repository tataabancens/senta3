import { Box, Drawer, Grid, IconButton, Typography } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import { FC } from "react";
import { ReservationModel } from "../models";


type Props = {
    reservation: ReservationModel | undefined;
    state: boolean;
    toggleDrawer: () => void;
};

const ReservationData: FC<Props> =({reservation, state, toggleDrawer}) => {


    return(
        <Drawer anchor="right" open={state} onClose={toggleDrawer}>
          <Grid container component={Box} sx={{ width: 300}} onClick={toggleDrawer} padding={1}>
            <Grid item xs={12} sx={{display:"flex", justifyContent:"flex-end"}}>
                <IconButton>
                    <CloseIcon  color="primary"/>
                </IconButton>
            </Grid>
            <Grid item xs={12} sx={ {display:"flex", justifyContent:"center", marginBottom: 2}}><Typography variant="h5">Your reservation</Typography></Grid>
            <Grid item xs={12} component={Typography} variant="h6">Customer: {reservation?.customerName}</Grid>
            <Grid item xs={12} component={Typography} variant="h6">Code: {reservation?.securityCode}</Grid>
            <Grid item xs={12} component={Typography} variant="h6">Date: {reservation?.date}</Grid>
            <Grid item xs={12} component={Typography} variant="h6">Hour: {reservation?.hour}:00</Grid>
            <Grid item xs={12} component={Typography} variant="h6">Table: {reservation?.tableNumber}</Grid>
          </Grid>
        </Drawer>
    );
}

export default ReservationData;