import * as React from 'react';
import Typography from '@mui/material/Typography';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import Grid from '@mui/material/Grid';
import { createReservationFormValues } from './CreateReservationPage';
import { FormikProps } from 'formik';

interface doneProps {
  props: FormikProps<createReservationFormValues>  
  secCode: string
}

export default function Done({props, secCode}: doneProps) {
  const { values: {date, hour, qPeople} } = props;
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
      You made a reservation on Atuel, for {date.toString()} at {hour}hs for {qPeople} people. Your reservation code is: {secCode}
      </Typography>
    </React.Fragment>
  );
}