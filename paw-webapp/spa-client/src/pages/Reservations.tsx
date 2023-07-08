import { Grid } from "@mui/material";
import { FC, useState } from "react";
import ReservationsTable from "../components/ReservationsTable";
import ReservationTabs from "../components/ReservationTabs";
import { useReservationsPagination } from "../hooks/serviceHooks/reservations/usePagination";

const Reservations: FC = () => {
  const [value, setValue] = useState(9);
  const [page, setPage] = useState(1);
  const [sortDirection, setSorting] = useState(false);

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
    setPage(1);
  };

  const handleSwitchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSorting(event.target.checked);
  };

  const reservationsPaginated = useReservationsPagination(page, value, sortDirection);

  return (
      <Grid container xs={12}>
        <ReservationTabs value={value} handleChange={handleChange} sortDirection={sortDirection} handleSwitchChange={handleSwitchChange} />
        <ReservationsTable page={page} reservationsPaginated={reservationsPaginated} setPage={setPage}/>
      </Grid>
  );
}

export default Reservations;
