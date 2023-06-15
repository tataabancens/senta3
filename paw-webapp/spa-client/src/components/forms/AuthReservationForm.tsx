import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField } from "@mui/material";
import { FC, useState } from "react";
import { useNavigate } from "react-router-dom";
import { awaitWrapper } from "../../Utils";
import useReservationService from "../../hooks/serviceHooks/reservations/useReservationService";
import { ReservationParams } from "../../models/Reservations/ReservationParams";
import { paths } from "../../constants/constants";
import { Formik, Form, Field, FormikHelpers, ErrorMessage } from "formik";
import * as Yup from "yup";
import { ReservationModel } from "../../models";
import ApiErrorDetails, { ApiError } from "../../models/ApiError/ApiErrorDetails";

type Props = {
  isOpen: boolean;
  handleOpen: () => void;
};

interface findReservationFormValue {
  securityCode: string;
}

const AuthReservationForm: FC<Props> = ({ isOpen, handleOpen }): JSX.Element => {
  const navigate = useNavigate();
  const reservationService = useReservationService()

  const initialValue: findReservationFormValue = {
    securityCode: '',
  }

  const validationSchema = Yup.object().shape({
    securityCode: Yup.string().length(6, "Must be 6 characters long").required("Required")
  })

  const handleSubmit = async (values: findReservationFormValue, props: FormikHelpers<findReservationFormValue>) => {
    const { securityCode } = values;

    const reservationParams = new ReservationParams();
    reservationParams.securityCode = securityCode;

    const { ok, error, response } = await awaitWrapper(reservationService.getReservation(reservationParams));

    if (!ok) {
      const apiError = error.response?.data as ApiErrorDetails;
      props.setFieldError("securityCode", apiError.message);
      props.setSubmitting(false);
      return;
    }

    const reservation = response.data as ReservationModel;

    props.resetForm();
    props.setSubmitting(false);
    navigate(`${paths.ROOT}/reservations/${reservation.securityCode}`);
  };

  return (
    <>
      <Dialog open={isOpen} fullWidth>
        <Formik initialValues={initialValue} onSubmit={handleSubmit} validationSchema={validationSchema}>
          {(props) => (
            <Form>
              <DialogTitle>Reservation security code</DialogTitle>
              <DialogContent>
                <DialogContentText>
                  Enter the security code given in the email we sent you.
                </DialogContentText>
                <Grid container marginY={3} xs={12}>
                  <Field as={TextField}
                    item
                    xs={12}
                    required
                    id="securityCode"
                    label="security code"
                    name="securityCode"
                    helperText={<ErrorMessage name="securityCode" />}
                    error={props.errors.securityCode}
                  />
                </Grid>
              </DialogContent>
              <DialogActions>
                <Button
                  type="submit"
                  variant="contained"
                  color="success"
                  disabled={props.isSubmitting}
                >
                  Confirm
                </Button>
                <Button onClick={handleOpen} variant="contained">
                  Cancel
                </Button>
              </DialogActions>
            </Form>
          )}
        </Formik>
      </Dialog>
    </>
  );
};

export default AuthReservationForm;