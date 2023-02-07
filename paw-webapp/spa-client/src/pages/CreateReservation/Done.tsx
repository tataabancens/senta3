import * as React from 'react';
import Typography from '@mui/material/Typography';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import Grid from '@mui/material/Grid';

interface doneProps {
    date: string,
    hour: number,
    qPeople: number,
    secCode: string
}

export default function Done({date, hour, qPeople, secCode}: doneProps) {
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
      You made a reservation on Atuel, for {date} at {hour}hs for {qPeople} people. Your reservation code is: {secCode}
      </Typography>
    </React.Fragment>
  );
}