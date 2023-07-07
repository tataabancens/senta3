import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { createReservationFormValues } from './CreateReservationPage';
import { Field, ErrorMessage, FormikProps } from 'formik';
import { useTranslation } from 'react-i18next';


interface qPeopleFormProps {
  props: FormikProps<createReservationFormValues> 
}

function handleChange(event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, setqPeople: (qPeople: number) => void) {
  if (event.target.value as unknown as number >= 0) {
    setqPeople(event.target.value as unknown as number);
  }
}

export default function QPeopleForm({ props }: qPeopleFormProps) {
  const {handleBlur, handleChange, values, errors} = props;
  const { t } = useTranslation();
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>{t('createReservation.step1.stepDescription')}</Typography>
      <Grid item xs={12} sm={6}>
        <Field as={TextField}
          type="number"
          required
          id="qPeople"
          name="qPeople"
          label=""
          fullWidth
          autoComplete="given-name"
          variant="standard"
          value={values.qPeople}
          onChange={handleChange}
          onBlur={handleBlur}
          helperText={<ErrorMessage name="qPeople" />}
          error={errors.qPeople}
        />
      </Grid>
    </React.Fragment>
  );
}