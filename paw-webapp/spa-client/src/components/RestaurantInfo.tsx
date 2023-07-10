import { Button, Grid, Paper, Typography } from "@mui/material";
import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { RestaurantModel, UserModel } from "../models";
import AccountInfoForm from "./forms/AccountInfoFrom";
import RestaurantInfoForm from "./forms/RestaurantInfoForm";
import useAuth from "../hooks/serviceHooks/authentication/useAuth";

type Props = {
    restaurant: RestaurantModel,
    reloadInfo: () => void
};

const RestaurantInfo: FC<Props> = ({restaurant, reloadInfo}): JSX.Element => {

    const { t } = useTranslation();
    const [isOpenAccountForm, setOpenAccountForm] = useState(false);
    const [isOpenRestForm, setOpenRestForm] = useState(false);
    const { auth } = useAuth();

    const handleOpenAccountForm = () => {
        setOpenAccountForm(!isOpenAccountForm);
    };

    const handleOpenRestForm = () => {
        setOpenRestForm(!isOpenRestForm);
    };


    return (
        <Grid
        item
        container
        spacing={6}
        justifyContent="center"
        sx={{ height: "50vh" }}
        padding={1}
      >
        <Grid
          item
          container
          xl={5}
          md={5}
          sm={12}
          xs={12}
          component={Paper}
          borderRadius={3}
          elevation={3}
          padding={2}
          margin={2}
        >
          <AccountInfoForm data={restaurant} isOpen={isOpenAccountForm} handleOpen={handleOpenAccountForm} reload={reloadInfo}/>
          <Grid item component={Typography} variant="h4" xl={9} lg={11} md={11} sm={12} xs={12} marginBottom={4}>{t('profilePage.accountInfo.title')}</Grid>
          <Grid item component={Button} variant="outlined" xl={2} lg={1} md={1} sm={12} xs={12} onClick={handleOpenAccountForm}><Typography variant="h5">{t('profilePage.editButton')}</Typography></Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.accountInfo.restaurantName',{name: restaurant?.name})}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.accountInfo.username',{username: auth.user})}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.accountInfo.mail',{mail: restaurant?.mail})}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.accountInfo.phone',{phone: restaurant?.phone})}</Grid>
        </Grid>
        <Grid
          item
          container
          xl={5}
          md={5}
          sm={12}
          xs={12}
          component={Paper}
          borderRadius={3}
          elevation={3}
          padding={2}
          margin={2}
        >
          <RestaurantInfoForm data={restaurant} isOpen={isOpenRestForm} handleOpen={handleOpenRestForm} />
          <Grid item component={Typography} variant="h4" xl={8} lg={11} md={11} sm={12} xs={12} marginBottom={4}>{t('profilePage.restaurantInfo.title')}</Grid>
          <Grid item component={Button} variant="outlined" xl={2} lg={1} md={1} sm={12} xs={12} onClick={handleOpenRestForm}><Typography variant="h5">{t('profilePage.editButton')}</Typography></Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.restaurantInfo.chairs',{chairs: restaurant?.totalChairs})}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.restaurantInfo.openHours',{hour: restaurant?.openHour})}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.restaurantInfo.closeHours',{hour: restaurant?.closeHour})}</Grid>
        </Grid>
      </Grid>
    );
  }


export default RestaurantInfo;