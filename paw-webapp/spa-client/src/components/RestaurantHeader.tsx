import { Box, Button, Grid, Typography } from "@mui/material";
import { FC, useState } from "react";
import { themePalette } from "../config/theme.config";
import { OrderItemModel, ReservationModel, RestaurantModel } from "../models";
import AuthReservationForm from "./forms/AuthReservationForm";
import ReservationData from "./ReservationData";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import ShoppingCart from "./ShoppingCart";

type Props = {
    restaurant: RestaurantModel | undefined;
    role: string;
    reservation?: ReservationModel;
    orderItems?: OrderItemModel[];
};

const RestaurantHeader: FC<Props> = ({
    restaurant,
    role,
    reservation,
    orderItems
  }): JSX.Element => {

    const [authIsOpen, setAuthOpen] = useState(false);
    const [state, setState] = useState(false);
    const [shoppingCartOpen, setShoppingCart] = useState(false);


    const toggleDrawer = () => {
      setState(!state);
    };

    const toggleShoppingCart = () => {
      setShoppingCart(!shoppingCartOpen);
    };

    const handleAuthReservation = () => {
        setAuthOpen(!authIsOpen);
    };

    return (
        <Grid
        item
        container
        xs={11}
        component={Box}
        marginTop={3}
        borderRadius={3}
        justifyContent="space-between"
        padding={1}
        sx={{
          background: themePalette.RED,
        }}
      >
        <AuthReservationForm handleOpen={handleAuthReservation} isOpen={authIsOpen} />
        <Grid item component={Typography} variant="h2" sx={{ color: "white" }}>
          {restaurant?.name}
        </Grid>
        {role === "ROLE_CUSTOMER"? 
            <Grid item>
            <ReservationData toggleDrawer={toggleDrawer} state={state} reservation={reservation}/>
            <ShoppingCart orderItems={orderItems!} toggleCart={toggleShoppingCart} isOpen={shoppingCartOpen} />
            <Button sx={{margin: 1}} variant="contained" color="success" onClick={toggleShoppingCart}><ShoppingCartIcon/></Button>
            <Button sx={{margin: 1}} variant="contained" color="secondary" onClick={toggleDrawer}>Data</Button>
            <Button sx={{margin: 1}} variant="contained" color="secondary">Orders</Button>
            </Grid>
            :
            <Grid item>
            <Button sx={{margin: 1}} variant="contained" color="success" href="createReservation">Make reservation</Button>
            <Button sx={{margin: 1}} variant="contained" color="secondary" onClick={handleAuthReservation}> Have reservation</Button>
            </Grid>
        }
      </Grid>
    );
  }


export default RestaurantHeader;