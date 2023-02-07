import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';

interface dateFormProps {
    date: string,
    setDate: (date:string) => void
}

export default function DateForm({date, setDate}:dateFormProps) {
    const [value, setValue] = React.useState<string | null>(null);
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
      Which day do you want to come?
      </Typography>
        <Grid item xs={12} sm={6}>
            <TextField
                id="Date"
                name="Date"
                // label="Date"
                type="date"
                defaultValue="2017-05-24"
                sx={{ width: 220 }}
                InputLabelProps={{
                    shrink: true,
                }}
                value={date}
                onChange={(event)=>setDate(event.target.value as string)}
            />
        </Grid>
    </React.Fragment>
  );
}