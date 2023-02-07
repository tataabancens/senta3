import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';


interface qPeopleFormProps {
  qPeople: number,
  setqPeople: (qPeople:number) => void
}

function handleChange(event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, setqPeople:(qPeople:number) => void){
    if(event.target.value as unknown as number >= 0){
        setqPeople(event.target.value as unknown as number);
    }
}

export default function QPeopleForm({qPeople, setqPeople}:qPeopleFormProps) {
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
      How many people will come?
      </Typography>
        <Grid item xs={12} sm={6}>
          <TextField
            // inputMode='numeric'
            type="number"
            // pattern="[1-9][0-9]*"
            required
            id="qPeople"
            name="qPeople"
            label=""
            fullWidth
            autoComplete="given-name"
            variant="standard"
            value={qPeople}
            onChange={(event)=>{handleChange(event, setqPeople)}}
          />
          
        </Grid>
    </React.Fragment>
  );
}