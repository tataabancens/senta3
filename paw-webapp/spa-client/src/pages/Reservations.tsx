import { Grid } from "@mui/material";
import { FC, useState } from "react";
import ReservationsTable from "../components/ReservationsTable";
import ReservationTabs from "../components/ReservationTabs";
import { useReservationsPagination } from "../hooks/serviceHooks/reservations/usePagination";

const Reservations: FC = () => {
  const [value, setValue] = useState(9);
  const [page, setPage] = useState(1);
  const [sortDirection, setSorting] = useState("DESC");
  const [orderBy, setOrderBy] = useState("reservationid");

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
    setPage(1);
  };

  const setOrderByCriteria = (sortDirection: string, orderBy: string) => {
    setSorting(sortDirection);
    setOrderBy(orderBy);
  };

  const reservationsPaginated = useReservationsPagination(page, value, sortDirection, orderBy);

  return (
      <Grid container xs={12}>
        <ReservationTabs value={value} handleChange={handleChange} />
        <ReservationsTable page={page} reservationsPaginated={reservationsPaginated} setPage={setPage} setOrderByCriteria={setOrderByCriteria} />
      </Grid>
  );
}

export default Reservations;
