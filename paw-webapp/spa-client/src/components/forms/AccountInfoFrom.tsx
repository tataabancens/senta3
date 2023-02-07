import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grid,
  TextField,
} from "@mui/material";
import { FC, useState } from "react";
import { handleResponse } from "../../handleResponse";
import useUserService from "../../hooks/serviceHooks/useUserService";
import useRestaurantService from "../../hooks/serviceHooks/useRestaurantService";
import useCustomerService from "../../hooks/serviceHooks/useCustomerService";
import { CustomerModel, RestaurantModel, UserModel } from "../../models";
import { CustomerParams } from "../../models/Customers/CustomerParams";
import { RestParams } from "../../models/Restaurant/RestParams";
import { UserParams } from "../../models/Users/UserParams";

type Props = {
  user: UserModel | undefined;
  data: CustomerModel | RestaurantModel | undefined;
  isOpen: boolean;
  handleOpen: () => void;
};

const AccountInfoForm: FC<Props> = ({
  user,
  data,
  handleOpen,
  isOpen,
}): JSX.Element => {
  const [name, setName] = useState();
  const [username, setUsername] = useState();
  const [mail, setMail] = useState();
  const [phone, setPhone] = useState();

  const userService = useUserService();
  const restaurantService = useRestaurantService();
  const customerService = useCustomerService();

  const handleSubmit = () => {
    let userData = new UserParams();
    userData.username = username;
    userData.userId = 2;
    if (user?.role === "ROLE_RESTAURANT") {
      let restaurantData = new RestParams();
      restaurantData.mail = mail;
      restaurantData.restaurantId = data?.id;
      restaurantData.restaurantName = name;
      restaurantData.phone = phone;
      handleResponse(
        restaurantService.editRestaurant(restaurantData),
        (response) => console.log(response)
      );

    } else {
      let customerData = new CustomerParams();
      customerData.customerId = data?.id;
      customerData.customerName = name;
      customerData.mail = mail;
      customerData.phone = phone;
      handleResponse(
        customerService.editCustomer(customerData),
        (response) => console.log(response)
      );
    }
    handleResponse(
      userService.editUser(userData),
      (response) => console.log(response)
    );


    handleOpen();
  };

  return (
    <>
      <Dialog open={isOpen}>
        <DialogTitle>Account info</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Write the fields you want to be modified
          </DialogContentText>
          <Grid container marginY={3}>
            {user?.role === "ROLE_RESTAURANT" ? (
              <Grid
                item
                xs={3}
                marginBottom={3}
                component={TextField}
                onChange={(e: any) => setName(e.target.value)}
                label="Restaurant Name"
                defaultValue={data?.name}
              />
            ) : (
              <Grid
                xs={5}
                marginBottom={3}
                item
                component={TextField}
                onChange={(e: any) => setName(e.target.value)}
                label="Customer Name"
                defaultValue={data?.name}
              />
            )}
            <Grid
              item
              xs={5}
              marginBottom={3}
              component={TextField}
              onChange={(e: any) => setUsername(e.target.value)}
              label="Username"
              defaultValue={user?.username}
            />
            <Grid
              item
              xs={5}
              marginBottom={1}
              component={TextField}
              onChange={(e: any) => setMail(e.target.value)}
              label="Mail"
              type="email"
              defaultValue={data?.mail}
            />
            <Grid
              item
              xs={5}
              marginBottom={1}
              component={TextField}
              onChange={(e: any) => setPhone(e.target.value)}
              label="Phone"
              defaultValue={data?.phone}
            />
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleSubmit} variant="contained" color="success">
            Confirm
          </Button>
          <Button onClick={handleOpen} variant="contained">
            Cancel
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default AccountInfoForm;
