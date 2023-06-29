import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import { MenuItem, Select } from "@mui/material";
import { createReservationFormValues } from './CreateReservationPage';
import { ErrorMessage, Field, FormikProps } from 'formik';
import { useTranslation } from 'react-i18next';

interface hourFormProps {
    props: FormikProps<createReservationFormValues>
    availableHours: number[]
}

export default function HourForm({ props, availableHours }: hourFormProps) {
    const { handleBlur, handleChange, values, errors } = props;
    const { t } = useTranslation();
    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>{t('createReservation.step3.stepDescription')}</Typography>
            <Grid item xs={12} sm={6}>
                <Field as={Select}
                    required
                    id="Hour"
                    name="hour"
                    label="Select one"
                    fullWidth
                    autoComplete="given-name"
                    variant="standard"
                    value={values.hour}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    helperText={<ErrorMessage name="date" />}
                >
                    <MenuItem value="0"> <em>{t('createReservation.step3.selectLabel')}</em> </MenuItem>
                    {availableHours.map((value) => (
                        <MenuItem key={value} value={value}>{value}:00</MenuItem>

                    ))}
                </Field>
                {errors.hour ? <p>{errors.hour}</p> : null}
            </Grid>
        </React.Fragment>
    );
}