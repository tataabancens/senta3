import { Badge, Box, Button, Grid, Skeleton, Typography } from "@mui/material";
import { FC, useContext, useEffect, useState } from "react";
import { themePalette } from "../config/theme.config";
import { RestaurantModel } from "../models";
import AuthReservationForm from "./forms/AuthReservationForm";
import ReservationData from "./ReservationData";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import ShoppingCart from "./shoppingCart/ShoppingCart";
import {useNavigate} from "react-router-dom";
import CategoryForm from "./forms/CategoryForm";
import { ReservationContext } from "../context/ReservationContext";
import { paths } from "../constants/constants";
import { useTranslation } from "react-i18next";

type Props = {
    restaurant?: RestaurantModel | undefined;
    role: string;
    toggleReload?: () => void;
};

const RestaurantHeader: FC<Props> = ({
    restaurant,
    role,
    toggleReload
  }): JSX.Element => {

    const [authIsOpen, setAuthOpen] = useState(false);
    const [state, setState] = useState(false);
    const [shoppingCartOpen, setShoppingCart] = useState(false);
    let navigate = useNavigate();
    const [createIsOpen, setIsOpen] = useState(false);
    const { reservation, orderItems } = useContext(ReservationContext);
    const [selectedItemsAmount, setSelectedItemsAmount] = useState(0);
    const { t } = useTranslation();

    useEffect(() => {
      const selectedItems = orderItems?.filter(item => item.status === "SELECTED")
      if(selectedItems){
        setSelectedItemsAmount(selectedItems.length)
      }
    },[orderItems])

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
          {role === "ROLE_RESTAURANT" ? t('restaurantHeader.menuHeader') : restaurant?  restaurant.name : <Skeleton  variant="rounded" animation="wave" width={410} height={50} />}
        </Grid>
        {reservation? 
            <Grid item>
            <ReservationData toggleDrawer={toggleDrawer} state={state} reservation={reservation}/>
            <ShoppingCart toggleCart={toggleShoppingCart} isOpen={shoppingCartOpen} toggleReload={toggleReload}/>
            <Button sx={{margin: 1}} variant="contained" color="success" onClick={toggleShoppingCart}><Badge badgeContent={selectedItemsAmount} color="secondary"><ShoppingCartIcon/></Badge></Button>
            <Button sx={{margin: 1}} variant="contained" color="secondary" onClick={toggleDrawer}>{t('restaurantHeader.myReservation')}</Button>
            </Grid>
            :
            role === "ROLE_ANONYMOUS"?
            <Grid item>
            <Button sx={{margin: 1}} variant="contained" color="success" onClick={() => navigate(paths.ROOT + "/createReservation")}>{t('restaurantHeader.makeReservation')}</Button>
            <Button sx={{margin: 1}} variant="contained" color="secondary" onClick={handleAuthReservation}>{t('restaurantHeader.haveReservation')}</Button>
            </Grid>
            :
            <Grid item xs={1}>
              <Button onClick={toggleCreateCategoryForm} variant="contained" fullWidth color="success">{t('restaurantHeader.createCategory')}</Button>
            </Grid>
        }
      </Grid>
    );
  }


export default RestaurantHeader;