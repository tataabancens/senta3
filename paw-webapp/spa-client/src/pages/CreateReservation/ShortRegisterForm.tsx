import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';

interface ShortRegisterFormProps {
    readonly firstName: string,
    readonly lastName: string,
    readonly mail: string,
    readonly phone: string,
    username: string,
    password: string,
    repeatPassword: string,
    setUsername: (date:string) => void,
    setPassword: (date:string) => void,
    setRepeatPassword: (date:string) => void

}


export default function ShortRegisterForm({firstName, lastName, mail, phone, username, password, repeatPassword, setUsername, setPassword, setRepeatPassword}:ShortRegisterFormProps) {
    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Create an account
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={6}>
                    <TextField
                        disabled
                        id="firstName"
                        name="firstName"
                        label="First name"
                        fullWidth
                        autoComplete="firstName"
                        variant="standard"
                        value={firstName}
                        // onChange={(event)=>setFirstName(event.target.value)}
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        disabled
                        id="lastName"
                        name="lastName"
                        label="Last name"
                        fullWidth
                        autoComplete="lastName"
                        variant="standard"
                        value={lastName}
                        // onChange={(event)=>setLastName(event.target.value)}
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
                        variant="standard"
                        value={mail}
                        // onChange={(event)=>setMail(event.target.value)}
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
                        // onChange={(event)=>setPhone(event.target.value)}
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="username"
                        name="username"
                        label="Username"
                        fullWidth
                        autoComplete="username"
                        variant="standard"
                        value={username}
                        onChange={(event)=>setUsername(event.target.value)}
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="password"
                        name="password"
                        label="Password"
                        fullWidth
                        variant="standard"
                        value={password}
                        onChange={(event)=>setPassword(event.target.value)}
                    />
                    <TextField
                        required
                        id="repeatPassword"
                        name="repeatPassword"
                        label="Repeat password"
                        fullWidth
                        variant="standard"
                        value={repeatPassword}
                        onChange={(event)=>setRepeatPassword(event.target.value)}
                    />
                </Grid>

            </Grid>
        </React.Fragment>
    );
}