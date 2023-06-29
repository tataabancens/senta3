import { Button, Grid, Paper, Typography } from "@mui/material";
import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { CustomerModel, UserModel } from "../models";
import AccountInfoForm from "./forms/AccountInfoFrom";

type Props = {
    user: UserModel,
    customer: CustomerModel,
    reloadInfo: () => void
};

const CustomerInfo: FC<Props> = ({user, customer, reloadInfo}) =>{
    const { t } = useTranslation();
    const [isOpenAccountForm, setOpenAccountForm] = useState(false);

    const handleOpenAccountForm = () => {
        setOpenAccountForm(!isOpenAccountForm);
    };

    return (
        <Grid
        item
        container
        justifyContent="space-around"
        sx={{ height: "40vh" }}
      >
        <Grid
          item
          container
          xs={11}
          sm={11}
          component={Paper}
          borderRadius={3}
          elevation={3}
          padding={2}
        >
          <AccountInfoForm user={user} data={customer} handleOpen={handleOpenAccountForm} isOpen={isOpenAccountForm} reload={reloadInfo} />
          <Grid item component={Typography} variant="h4" xl={11} lg={11} md={11} sm={12} xs={12} marginBottom={4}>{t('profilePage.accountInfo.title')}</Grid>
          <Grid item component={Button} variant="outlined" xl={1} lg={1} md={1} sm={12} xs={12} onClick={handleOpenAccountForm}><Typography variant="h5">{t('profilePage.editButton')}</Typography></Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.accountInfo.clientName',{customer: customer?.name})}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.accountInfo.username',{username: user?.username})}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.accountInfo.mail',{mail: customer?.mail})}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.accountInfo.phone',{phone: customer?.phone})}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>{t('profilePage.accountInfo.points',{points: customer?.points})}</Grid>
        </Grid>
      </Grid>
    );
}

export default CustomerInfo;