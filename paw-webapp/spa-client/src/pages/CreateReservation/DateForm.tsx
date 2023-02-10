import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { createReservationFormValues } from './CreateReservationPage';
import { Field, ErrorMessage, FormikProps } from 'formik';

interface dateFormProps {
  props: FormikProps<createReservationFormValues>
}

export default function DateForm({ props }: dateFormProps) {
  const { handleBlur, handleChange, values, errors } = props;
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
        Which day do you want to come?
      </Typography>
      <Grid item xs={12} sm={6}>
        <Field as={TextField}
          id="Date"
          name="date"
          // label="Date"
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