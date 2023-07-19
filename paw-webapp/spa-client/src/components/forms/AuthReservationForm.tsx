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
import { useTranslation } from "react-i18next";
import useServiceProvider from "../../context/ServiceProvider";

type Props = {
  isOpen: boolean;
  handleOpen: () => void;
};

interface findReservationFormValue {
  securityCode: string;
}

const AuthReservationForm: FC<Props> = ({ isOpen, handleOpen }): JSX.Element => {
  const navigate = useNavigate();
  const { reservationService } = useServiceProvider();
  const { t } = useTranslation();

  const initialValue: findReservationFormValue = {
    securityCode: '',
  }

  const validationSchema = Yup.object().shape({
    securityCode: Yup.string().length(6, t('validationSchema.secCodeValidation',{length: 6})).required(t('validationSchema.required'))
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
              <DialogTitle>{t('forms.authReservation.title')}</DialogTitle>
              <DialogContent>
                <DialogContentText>
                {t('forms.authReservation.description')}
                </DialogContentText>
                <Grid container marginY={3} xs={12}>
                  <Field as={TextField}
                    item
                    xs={12}
                    required
                    id="securityCode"
                    label={t('forms.authReservation.label')}
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
                  {t('forms.confirmButton')}
                </Button>
                <Button onClick={handleOpen} variant="contained">
                {t('forms.cancelButton')}
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