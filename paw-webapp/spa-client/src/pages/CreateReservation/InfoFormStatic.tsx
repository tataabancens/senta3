import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import { ErrorMessage, Field, FormikProps } from 'formik';
import { createReservationFormValues } from './CreateReservationPage';
import {CustomerModel} from "../../models";


export default function InfoForm( customer: { customer: CustomerModel;}) {
  const name = customer.customer.name;
  const mail = customer.customer.mail;
  const phone = customer.customer.phone;
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
        Your reservation will be set with your User data:
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6}>
          <Field as={TextField}
                 disabled
            autoFocus
            id="name"
            name="name"
            label="Name"
            fullWidth
            autoComplete="given-name"
            variant="standard"
            value={name}
            helperText={<ErrorMessage name="name" />}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
              disabled
            id="mail"
            name="mail"
            label="Mail"
            fullWidth
            autoComplete="mail"
            type="mail"
            variant="standard"
            value={mail}
            helperText={<ErrorMessage name="mail" />}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
              disabled
            id="phone"
            name="phone"
            label="Phone"
            fullWidth
            autoComplete="phone"
            variant="standard"
            value={phone}
            helperText={<ErrorMessage name="phone" />}
          />
        </Grid>
      </Grid>
    </React.Fragment>
  );
}