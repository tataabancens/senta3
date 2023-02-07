import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField } from "@mui/material";
import { FC, useState } from "react";
import { useNavigate } from "react-router-dom";
import { handleResponse } from "../../handleResponse";
import useReservationService from "../../hooks/serviceHooks/useReservationService";
import { ReservationParams } from "../../models/Reservations/ReservationParams";

type Props ={
    isOpen: boolean;
    handleOpen: () => void;
};

const AuthReservationForm: FC<Props> = ({isOpen, handleOpen}): JSX.Element => {
    const [secCode, setSecCode] = useState();
    const navigate = useNavigate();
    const reservationService = useReservationService()

    const handleSubmit = () => {
      const reservationParams = new ReservationParams();
      reservationParams.securityCode = secCode;

      handleResponse(
        reservationService.getReservation(reservationParams),
        () => {
          handleOpen();
          navigate(`/reservation/${secCode}`);
        }
      )
    };
  
    return (
      <>
        <Dialog open={isOpen} fullWidth>
          <DialogTitle>Reservation security code</DialogTitle>
          <DialogContent>
            <DialogContentText>
              Enter the security code given in the email we sent you.
            </DialogContentText>
            <Grid container marginY={3} xs={12}>
            <Grid
                item
                xs={12}
                component={TextField}
                onChange={(e: any) => setSecCode(e.target.value)}
                label="Security code"
              />
            </Grid>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleSubmit} variant="contained" color="success">
              Confirm
            </Button>
            <Button onClick={handleOpen} variant="contained">
              Cancel
            </Button>
          </DialogActions>
        </Dialog>
      </>
    );
  };
  
  export default AuthReservationForm;