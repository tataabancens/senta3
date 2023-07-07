import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { createReservationFormValues } from './CreateReservationPage';
import { Field, ErrorMessage, FormikProps } from 'formik';
import { useTranslation } from 'react-i18next';

interface dateFormProps {
  props: FormikProps<createReservationFormValues>
}

export default function DateForm({ props }: dateFormProps) {
  const { handleBlur, handleChange, values, errors } = props;
  const { t } = useTranslation();
  return (
    <React.Fragment>
      <Typography variant="h6" align="center" gutterBottom>{t('createReservation.step2.stepDescription')}</Typography>
      <Grid item xs={12} sm={6} sx={{display:"flex", justifyContent:"center"}}>
        <Field as={TextField}
          id="Date"
          name="date"
          type="date"
          sx={{ width: 220 }}
          InputLabelProps={{
            shrink: true,
          }}
          value={values.date}
          onChange={handleChange}
          onBlur={handleBlur}
          helperText={<ErrorMessage name="date" />}
          error={errors.date}
           />
      </Grid>
    </React.Fragment>
  );
}