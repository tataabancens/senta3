import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import { ErrorMessage, Field, FormikProps } from 'formik';
import { createReservationFormValues } from './CreateReservationPage';
import { useTranslation } from 'react-i18next';

interface infoFormProps {
  props: FormikProps<createReservationFormValues>
}


export default function InfoForm({ props }: infoFormProps) {
  const { handleBlur, handleChange, values, errors } = props;
  const {firstName, lastName, mail, phone} = values;
  const { t } = useTranslation();
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>{t('createReservation.step4.stepDescription')}</Typography>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6}>
          <Field as={TextField}
            autoFocus
            id="firstName"
            name="firstName"
            label={t('createReservation.step4.firstName')}
            fullWidth
            autoComplete="given-name"
            variant="standard"
            value={firstName}
            onChange={handleChange}
            onBlur={handleBlur}
            helperText={<ErrorMessage name="firstName" />}
            error={errors.firstName}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            id="lastName"
            name="lastName"
            label={t('createReservation.step4.lastName')}
            fullWidth
            autoComplete="family-name"
            variant="standard"
            value={lastName}
            onChange={handleChange}
            onBlur={handleBlur}
            helperText={<ErrorMessage name="lastName" />}
            error={!!errors.lastName}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            required
            id="mail"
            name="mail"
            label={t('createReservation.step4.mail')}
            fullWidth
            autoComplete="mail"
            type="mail"
            variant="standard"
            value={mail}
            onChange={handleChange}
            onBlur={handleBlur}
            helperText={<ErrorMessage name="mail" />}
            error={!!errors.mail}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            required
            id="phone"
            name="phone"
            label={t('createReservation.step4.phone')}
            fullWidth
            autoComplete="phone"
            variant="standard"
            value={phone}
            onChange={handleChange}
            onBlur={handleBlur}
            helperText={<ErrorMessage name="phone" />}
            error={!!errors.phone}
          />
        </Grid>
      </Grid>
    </React.Fragment>
  );
}