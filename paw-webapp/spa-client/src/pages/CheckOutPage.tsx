import { FC } from "react";
import { useParams } from "react-router-dom";
import { Grid } from "@mui/material";
import CheckOutSummary from "../components/checkout/CheckoutSummary";
import { useReservation } from "../hooks/serviceHooks/reservations/useReservation";
import { useOrderItemsBySecCode } from "../hooks/serviceHooks/orderItems/useOrderItemsBySecCode";


const CheckOutPage: FC = () =>{

    const { securityCode } = useParams();

    const { reservation} = useReservation(securityCode!);

    const {orderItems} = useOrderItemsBySecCode(reservation);

    return(
        <Grid container xs={12} justifyContent="center">
           <CheckOutSummary reservation={reservation!} orderItems={orderItems!} />  
        </Grid>
    );
}

export default CheckOutPage;