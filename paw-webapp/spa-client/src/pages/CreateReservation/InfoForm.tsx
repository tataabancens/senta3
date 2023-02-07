import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';

interface infoFormProps {
    firstName: string,
    lastName: string,
    mail: string,
    phone: string,
    setFirstName: (date:string) => void
    setLastName: (date:string) => void
    setMail: (date:string) => void
    setPhone: (date:string) => void
}


export default function InfoForm({firstName, lastName, mail, phone, setFirstName, setLastName, setMail, setPhone}:infoFormProps) {
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
      We need some info to confirm your reservation
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6}>
          <TextField
            required
            id="firstName"
            name="firstName"
            label="First name"
            fullWidth
            autoComplete="given-name"
            variant="standard"
            value={firstName}
            onChange={(event)=>setFirstName(event.target.value)}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            required
            id="lastName"
            name="lastName"
            label="Last name"
            fullWidth
            autoComplete="family-name"
            variant="standard"
            value={lastName}
            onChange={(event)=>setLastName(event.target.value)}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            required
            id="mail"
            name="mail"
            label="Mail"
            fullWidth
            autoComplete="mail"
            variant="standard"
            value={mail}
            onChange={(event)=>setMail(event.target.value)}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            required
            id="phone"
            name="phone"
            label="Phone"
            fullWidth
            autoComplete="phone"
            variant="standard"
            value={phone}
            onChange={(event)=>setPhone(event.target.value)}
          />
        </Grid>
      </Grid>
    </React.Fragment>
  );
}