import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import {MenuItem, Select} from "@mui/material";

interface hourFormProps {
    hour: number,
    setHour: (date:number) => void,
    availableHours: number[]
}

export default function HourForm({hour, setHour, availableHours}: hourFormProps) {
    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                At what time do you wish to attend? sittings last 1 hour.
            </Typography>
            <Grid item xs={12} sm={6}>
                <Select
                    required
                    id="Hour"
                    name="Hour"
                    label="Select one"
                    fullWidth
                    autoComplete="given-name"
                    variant="standard"
                    value={hour}
                    onChange={(event) => setHour(event.target.value as unknown as number)}
                >
                    <MenuItem value="0"> <em>Select one</em> </MenuItem>
                    {availableHours.map((value) => (
                        <MenuItem key={value} value={value}>{value}</MenuItem>
                    ))}
                </Select>
            </Grid>
        </React.Fragment>
    );
}