import { Box, Button, Grid, Paper, Typography } from "@mui/material";
import { AxiosResponse } from "axios";
import { useEffect, useState, FC } from "react";
import AccountInfoForm from "../components/forms/AccountInfoFrom";
import RestaurantInfoForm from "../components/forms/RestaurantInfoForm";
import { handleResponse } from "../Utils";
import { CustomerModel, RestaurantModel, UserModel } from "../models";
import useUserService from "../hooks/serviceHooks/users/useUserService";
import useAuth from "../hooks/useAuth";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { initCustomer, initRestaurant } from "../constants/constants";
import RestaurantInfo from "../components/RestaurantInfo";
import CustomerInfo from "../components/CustomerInfo";
import { useTranslation } from "react-i18next";

const ProfilePage: FC = () => {
  const { auth } = useAuth();

  const initUser: UserModel = {
    username: auth.user,
    role: auth.roles[0],
    content: '',
    id: auth.id,
  }


  const [reloadUser, setReload] = useState(false);
  const [user, setUser] = useState<UserModel>(initUser);
  const [restaurant, setRestaurant] = useState<RestaurantModel>(initRestaurant);
  const [customer, setCustomer] = useState<CustomerModel>(initCustomer);
  const userService = useUserService();
  const axiosPrivate = useAxiosPrivate();
  const { t } = useTranslation();
  
  const handleReload = () => {
    setReload(!reloadUser);
  }

  useEffect(() => {
    handleResponse(userService.getUserById(auth.id), (user: UserModel) =>
      setUser(user)
    );
  }, [reloadUser]);

  useEffect(() => {
    if (user?.role === "ROLE_RESTAURANT") {
      axiosPrivate
        .get(user.content)
        .then((response: AxiosResponse<RestaurantModel>) => {
          setRestaurant(response.data);
        })
        .catch((err) => console.log(err));
    } else if (user?.role === "ROLE_CUSTOMER") {
      axiosPrivate
        .get(user.content)
        .then((response: AxiosResponse<CustomerModel>) => {
          setCustomer(response.data);
        })
        .catch((err) => console.log(err));
    }
  }, [user]);

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
          `linear-gradient(51deg, rgba(217,30,54,1) 40%, rgba(255,253,247,1) 80%);`,
        }}
      >
      <Typography variant="h2" sx={{ color: "white" }}>{t('profilePage.title')}</Typography>
      </Grid>
      {user?.role === "ROLE_RESTAURANT"? 
      <RestaurantInfo user={user} restaurant={restaurant} reloadInfo={handleReload}/> :
      <CustomerInfo user={user} customer={customer} reloadInfo={handleReload} />}
    </Grid>
  );
}

export default ProfilePage;
