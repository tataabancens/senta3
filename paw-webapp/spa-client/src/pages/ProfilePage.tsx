import { Box, CircularProgress, Grid, Typography } from "@mui/material";
import {  useState, FC } from "react";
import { CustomerModel, RestaurantModel } from "../models";
import useAuth from "../hooks/serviceHooks/authentication/useAuth";
import RestaurantInfo from "../components/RestaurantInfo";
import CustomerInfo from "../components/CustomerInfo";
import { useTranslation } from "react-i18next";
import { UserRoles } from "../models/Enums/UserRoles";

const ProfilePage: FC = () => {
  const { auth } = useAuth();

  const [reloadUser, setReload] = useState(false);
  const { t } = useTranslation();

  const handleReload = () => {
    setReload(!reloadUser);
  }

  return (
    <Grid container justifyContent={"center"} marginTop={4}>
      <Grid
        item
        xs={11}
        component={Box}
        marginTop={2}
        marginBottom={7}
        borderRadius={3}
        sx={{
          background:
            `linear-gradient(51deg, rgba(128, 0, 128, 1) 40%, rgba(255,253,247,1) 80%);`,
        }}
      >
        <Typography variant="h2" sx={{ color: "white" }}>{t('profilePage.title')}</Typography>
      </Grid>
      {auth?.roles[0] === UserRoles.RESTAURANT && auth.content ?
        <RestaurantInfo restaurant={auth.content as RestaurantModel} reloadInfo={handleReload} /> :
        <CustomerInfo customer={auth.content as CustomerModel} reloadInfo={handleReload} />}
      {(!auth || !auth.content) && <div style={{position:"absolute", top: "50%", right:"50%"}}><CircularProgress /></div>}
    </Grid>
  );
}

export default ProfilePage;
