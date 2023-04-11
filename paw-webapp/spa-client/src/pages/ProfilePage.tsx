import { Box, Button, Grid, Paper, Typography } from "@mui/material";
import axios, { AxiosResponse } from "axios";
import { useEffect, useState } from "react";
import AccountInfoForm from "../components/forms/AccountInfoFrom";
import RestaurantInfoForm from "../components/forms/RestaurantInfoForm";
import { handleResponse } from "../Utils";
import { CustomerModel, RestaurantModel, UserModel } from "../models";
import useUserService from "../hooks/serviceHooks/useUserService";
import useAuth from "../hooks/useAuth";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

function ProfilePage() {
  const { auth } = useAuth();

  const initUser: UserModel = {
    username: auth.user,
    role: auth.roles[0],
    content: '',
    id: auth.id,
  }

  const initRestaurant: RestaurantModel = {
    id: 1,
    name: '',
    phone: '',
    mail: '',
    dishes: '',
    reservations: '',
    dishCategories: '',
    user: '',
    totalChairs: 0,
    openHour: 0,
    closeHour: 0,
    self: ''
  }

  const initCustomer: CustomerModel = {
    id: 0,
    name: '',
    phone: '',
    mail: '',
    points: 0,
    user: '',
    reservations: '',
    self: '',
  }


  const [isOpenAccountForm, setOpenAccountForm] = useState(false);
  const [isOpenRestForm, setOpenRestForm] = useState(false);
  const [user, setUser] = useState<UserModel>(initUser);
  const [restaurant, setRestaurant] = useState<RestaurantModel>(initRestaurant);
  const [customer, setCustomer] = useState<CustomerModel>(initCustomer);
  const userService = useUserService();
  const axiosPrivate = useAxiosPrivate();
  

  const handleOpenAccountForm = () => {
    setOpenAccountForm(!isOpenAccountForm);
  };

  const handleOpenRestForm = () => {
    setOpenRestForm(!isOpenRestForm);
  };

  useEffect(() => {
    handleResponse(userService.getUserById(auth.id), (user: UserModel) =>
      setUser(user)
    );
  }, [isOpenAccountForm]);

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
  }, [user, isOpenAccountForm, isOpenRestForm]);

  function displayRestaurantInfo(
    user: UserModel,
    restaurant: RestaurantModel
  ) {
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
          <AccountInfoForm user={user} data={restaurant} isOpen={isOpenAccountForm} handleOpen={handleOpenAccountForm} />
          <Grid item component={Typography} variant="h4" xl={9} lg={11} md={11} sm={12} xs={12} marginBottom={4}>Account info:</Grid>
          <Grid item component={Button} variant="outlined" xl={2} lg={1} md={1} sm={12} xs={12} onClick={handleOpenAccountForm}><Typography variant="h5">Edit</Typography></Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Restaurant name: {restaurant?.name}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Username: {user?.username}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Mail: {restaurant?.mail}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Phone: {restaurant?.phone}</Grid>
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
          <Grid item component={Typography} variant="h4" xl={8} lg={11} md={11} sm={12} xs={12} marginBottom={4}>Restaurant info:</Grid>
          <Grid item component={Button} variant="outlined" xl={2} lg={1} md={1} sm={12} xs={12} onClick={handleOpenRestForm}><Typography variant="h5">Edit</Typography></Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Chairs: {restaurant?.totalChairs}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Open hour: {restaurant?.openHour}:00</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Close hour: {restaurant?.closeHour}:00</Grid>
        </Grid>
      </Grid>
    );
  }

  function displayCustomerInfo(
    user: UserModel,
    customer: CustomerModel
  ) {
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
          <AccountInfoForm user={user} data={customer} handleOpen={handleOpenAccountForm} isOpen={isOpenAccountForm}/>
          <Grid item component={Typography} variant="h4" xl={11} lg={11} md={11} sm={12} xs={12} marginBottom={4}>Account info:</Grid>
          <Grid item component={Button} variant="outlined" xl={1} lg={1} md={1} sm={12} xs={12} onClick={handleOpenAccountForm}><Typography variant="h5">Edit</Typography></Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Name: {customer?.name}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Username: {user?.username}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Mail: {customer?.mail}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Phone: {customer?.phone}</Grid>
          <Grid item component={Typography} variant="h5" xs={12} marginY={2}>Points: {customer?.points}</Grid>
        </Grid>
      </Grid>
    );
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
            "linear-gradient(51deg, rgba(217,30,54,1) 40%, rgba(255,253,247,1) 80%);",
        }}
      >
        <Typography variant="h2" sx={{ color: "white" }}>
          Profile
        </Typography>
      </Grid>
      {user?.role === "ROLE_RESTAURANT"? displayRestaurantInfo(user, restaurant) : displayCustomerInfo(user, customer)}
    </Grid>
  );
}

export default ProfilePage;
