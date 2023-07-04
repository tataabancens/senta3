import { FC } from "react";
import { useParams } from "react-router-dom";
import { Grid } from "@mui/material";
import CheckOutSummary from "../components/CheckoutSummary";
import { useReservation } from "../hooks/serviceHooks/reservations/useReservation";
import { useOrderItemsBySecCode } from "../hooks/serviceHooks/reservations/useOrderItemsBySecCode";


const CheckOutPage: FC = () =>{

    const { securityCode } = useParams();

    const { reservation, loading: reservationLoading, error: reservationError, updateReservation } = useReservation(securityCode!);

    const {orderItems, error: orderItemsError, loading: orderItemLoading} = useOrderItemsBySecCode(reservation);

    return(
        <Grid container xs={12} justifyContent="center">
           <CheckOutSummary reservation={reservation!} orderItems={orderItems!} />  
        </Grid>
    );
}

export default CheckOutPage;