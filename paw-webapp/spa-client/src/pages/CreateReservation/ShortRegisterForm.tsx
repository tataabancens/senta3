import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { createReservationFormValues } from './CreateReservationPage';
import { Field, ErrorMessage, FormikProps } from 'formik';

interface ShortRegisterFormProps {
    props: FormikProps<createReservationFormValues>;
}


export default function ShortRegisterForm({ props }: ShortRegisterFormProps) {
    const { values:
        { firstName, lastName, mail, phone, username, password, repeatPassword, userStep },
        handleChange,
        handleBlur,
        errors } = props;
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
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <Field as={TextField}
                        required
                        id="username"
                        name="username"
                        label="Username"
                        fullWidth
                        autoComplete="username"
                        variant="standard"
                        value={username}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        helperText={<ErrorMessage name="username" />}
                        error={errors.username}
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <Field as={TextField}
                        required
                        id="password"
                        name="password"
                        label="Password"
                        type="password"
                        fullWidth
                        variant="standard"
                        value={password}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        helperText={<ErrorMessage name="password" />}
                        error={errors.password}
                    />
                    <Field as={TextField}
                        required
                        id="repeatPassword"
                        name="repeatPassword"
                        label="Repeat password"
                        type="password"
                        fullWidth
                        variant="standard"
                        value={repeatPassword}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        helperText={<ErrorMessage name="repeatPassword" />}
                        error={errors.repeatPassword} />
                </Grid>
            </Grid>
        </React.Fragment>
    );
}