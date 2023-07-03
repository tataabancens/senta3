import { Grid } from "@mui/material";
import { FC, useState } from "react";
import ReservationsTable from "../components/ReservationsTable";
import ReservationTabs from "../components/ReservationTabs";

const Reservations: FC = () => {
  const [value, setValue] = useState(9);
  const [sortDirection, setSorting] = useState(false);

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  const handleSwitchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSorting(event.target.checked);
  };

  return (
      <Grid container xs={12}>
        <ReservationTabs value={value} handleChange={handleChange} sortDirection={sortDirection} handleSwitchChange={handleSwitchChange} />
        <ReservationsTable sortDirection={sortDirection} value={value} />
      </Grid>
  );
}

export default Reservations;
