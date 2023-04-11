import { Box, Button, Grid, Typography } from "@mui/material";
import { FC, useState } from "react";
import { themePalette } from "../config/theme.config";
import { OrderItemModel, ReservationModel, RestaurantModel } from "../models";
import AuthReservationForm from "./forms/AuthReservationForm";
import ReservationData from "./ReservationData";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import ShoppingCart from "./ShoppingCart";
import {useNavigate} from "react-router-dom";
import CategoryForm from "./forms/CategoryForm";

type Props = {
    restaurant?: RestaurantModel | undefined;
    role: string;
    reservation?: ReservationModel;
    orderItems?: OrderItemModel[];
    toggleReload?: () => void;
};

const RestaurantHeader: FC<Props> = ({
    restaurant,
    role,
    reservation,
    orderItems,
    toggleReload
  }): JSX.Element => {

    const [authIsOpen, setAuthOpen] = useState(false);
    const [state, setState] = useState(false);
    const [shoppingCartOpen, setShoppingCart] = useState(false);
    let navigate = useNavigate();
    const [createIsOpen, setIsOpen] = useState(false);


    const toggleDrawer = () => {
      setState(!state);
    };

    const toggleShoppingCart = () => {
      setShoppingCart(!shoppingCartOpen);
    };

    const handleAuthReservation = () => {
        setAuthOpen(!authIsOpen);
    };

    const toggleCreateCategoryForm = () => {
      setIsOpen(!createIsOpen);
    }

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
          background: themePalette.PURPLE,
          display: "flex",
          alignItems:"center"
        }}
      >
        <AuthReservationForm handleOpen={handleAuthReservation} isOpen={authIsOpen} />
        <CategoryForm isOpen={createIsOpen} handleOpen={toggleCreateCategoryForm} canReload={toggleReload}/>
        <Grid item component={Typography} variant="h2" sx={{ color: "white" }}>
          {role !== "ROLE_RESTAURANT" ? restaurant?.name : "Menu"}
        </Grid>
        {role === "ROLE_CUSTOMER"? 
            <Grid item>
            <ReservationData toggleDrawer={toggleDrawer} state={state} reservation={reservation}/>
            <ShoppingCart orderItems={orderItems!} toggleCart={toggleShoppingCart} isOpen={shoppingCartOpen} securityCode={reservation?.securityCode} toggleReload={toggleReload}/>
                <Button sx={{margin: 1}} variant="contained" color="secondary" onClick={() => navigate("checkout")}>checkout</Button>
            <Button sx={{margin: 1}} variant="contained" color="success" onClick={toggleShoppingCart}><ShoppingCartIcon/></Button>
            <Button sx={{margin: 1}} variant="contained" color="secondary" onClick={toggleDrawer}>Data</Button>
            <Button sx={{margin: 1}} variant="contained" color="secondary">Orders</Button>
            </Grid>
            :
            role === "ROLE_ANONYMOUS"?
            <Grid item>
            <Button sx={{margin: 1}} variant="contained" color="success" onClick={() => navigate("createReservation")}>Make reservation</Button>
            <Button sx={{margin: 1}} variant="contained" color="secondary" onClick={handleAuthReservation}> Have reservation</Button>
            </Grid>
            :
            <Grid item xs={1}>
              <Button onClick={toggleCreateCategoryForm} variant="contained" fullWidth color="success">Create category</Button>
            </Grid>
        }
      </Grid>
    );
  }


export default RestaurantHeader;