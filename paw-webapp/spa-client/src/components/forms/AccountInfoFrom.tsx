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
import { awaitWrapper, handleResponse, loginErrorHandler, tryLogin } from "../../Utils";
import useUserService from "../../hooks/serviceHooks/users/useUserService";
import useRestaurantService from "../../hooks/serviceHooks/restaurants/useRestaurantService";
import useCustomerService from "../../hooks/serviceHooks/useCustomerService";
import { CustomerModel, RestaurantModel, UserModel } from "../../models";
import { CustomerParams } from "../../models/Customers/CustomerParams";
import { RestParams } from "../../models/Restaurant/RestParams";
import { UserParams } from "../../models/Users/UserParams";
import { Formik, Form, Field, FormikHelpers, ErrorMessage } from "formik";
import * as Yup from "yup";
import ApiErrorDetails from "../../models/ApiError/ApiErrorDetails";
import axios from "../../api/axios";

interface accountInfoFormValues {
  restaurantName: string;
  customerName: string;
  username: string;
  phone: string;
  mail: string;
  isRestaurantUser: boolean;
  password: string;
  repeatPassword: string;
}

type Props = {
  user: UserModel;
  data: CustomerModel | RestaurantModel;
  isOpen: boolean;
  handleOpen: () => void;
};

const AccountInfoForm: FC<Props> = ({
  user,
  data,
  handleOpen,
  isOpen,
}): JSX.Element => {
  
  const initialValues: accountInfoFormValues = {
    restaurantName: data.name,
    customerName: data.name,
    username: user.username,
    phone: data.phone,
    mail: data.mail,
    isRestaurantUser: true,
    password: '',
    repeatPassword: '',
  }

  const validationSchema = Yup.object().shape({
    restaurantName: Yup.string().when("isRestaurantUser", { is: true, then: Yup.string().required("Required") }),
    customerName: Yup.string().when("isRestaurantUser", { is: false, then: Yup.string().required("Required") }),
    isRestaurantUser: Yup.boolean(),
    username: Yup.string().required("Required"),
    phone: Yup.string().required("Required"),
    mail: Yup.string().required("Required"),
    password: Yup.string().min(8, "Minimun 8 caracters long"),
    repeatPassword: Yup.string()
      .oneOf([Yup.ref('password'), null], 'Passwords must match'),
  
  })

  const [name, setName] = useState();
  const [username, setUsername] = useState();
  const [mail, setMail] = useState();
  const [phone, setPhone] = useState();

  const userService = useUserService();
  const restaurantService = useRestaurantService();
  const customerService = useCustomerService();

  const handleSubmit = async (values: accountInfoFormValues, props: FormikHelpers<accountInfoFormValues>) => {
    const {restaurantName, customerName, mail, phone, password, repeatPassword} = values;
    
    if (user?.role === "ROLE_RESTAURANT") {
      let restaurantData = new RestParams();
      restaurantData.mail = mail;
      restaurantData.restaurantId = data?.id;
      restaurantData.restaurantName = restaurantName;
      restaurantData.phone = phone;
      handleResponse(
        restaurantService.editRestaurant(restaurantData),
        (response) => console.log(response)
      );

    } else {
      let customerData = new CustomerParams();
      customerData.customerId = data?.id;
      customerData.customerName = customerName;
      customerData.mail = mail;
      customerData.phone = phone;
      handleResponse(
        customerService.editCustomer(customerData),
        (response) => console.log(response)
      );
    }

    let userData = new UserParams();
    userData.userId = user.id;
    if (password !== '' && repeatPassword !== '') {
      userData.psPair = {password: password, checkPassword: repeatPassword};
    }
    
    const { ok: userEdited, error: userError, response: userResponse } = await awaitWrapper(userService.editUser(userData));

    if (!userEdited) {
      console.log(userError)
      const errorData = userError.response?.data as ApiErrorDetails;
      props.setFieldError("username", errorData.errors?.find((e) => e.property == "username")?.description);
      props.setSubmitting(false);
      return;
    }


    // handleResponse(
    //   ,
    //   (response) => console.log(response)
    // );


    handleOpen();
  };

  return (
    <>
      <Dialog open={isOpen}>
        <Formik initialValues={initialValues} onSubmit={handleSubmit} validationSchema={validationSchema}>
          {(props) => (
            <Form>
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
                    >
                      <Field as={TextField}
                        required
                        id="restaurantName"
                        label="Restaurant Name"
                        name="restaurantName"
                        value={props.values.restaurantName}
                        helperText={<ErrorMessage name="restaurantName"></ErrorMessage>}
                        error={props.errors.restaurantName}
                      />
                    </Grid>
                  ) : (
                    <Grid
                      xs={5}
                      marginBottom={3}
                      item
                    >
                      <Field as={TextField}
                        required
                        id="customerName"
                        label="Customer Name"
                        name="customerName"
                        value={props.values.customerName}
                        helperText={<ErrorMessage name="customerName"></ErrorMessage>}
                        error={props.errors.customerName}
                      />
                    </Grid>
                  )}
                  <Grid
                    item
                    xs={5}
                    marginBottom={3}
                  >
                    <Field as={TextField}
                      required
                      id="username"
                      label="Username"
                      name="username"
                      value={props.values.username}
                      helperText={<ErrorMessage name="username"></ErrorMessage>}
                      error={props.errors.username}
                    />
                  </Grid>
                  <Grid
                    item
                    xs={5}
                    marginBottom={1}
                  >
                    <Field as={TextField}
                      required
                      id="mail"
                      label="Mail"
                      name="mail"
                      value={props.values.mail}
                      helperText={<ErrorMessage name="mail"></ErrorMessage>}
                      error={props.errors.mail}
                    />
                  </Grid>
                  <Grid
                    item
                    xs={5}
                    marginBottom={1}
                  >
                    <Field as={TextField}
                      required
                      id="phone"
                      label="Phone"
                      name="phone"
                      value={props.values.phone}
                      helperText={<ErrorMessage name="phone"></ErrorMessage>}
                      error={props.errors.phone}
                    />
                  </Grid>
                  <Grid
                    item
                    xs={5}
                    marginBottom={1}
                  >
                    <Field as={TextField}
                      
                      id="password"
                      label="Password"
                      name="password"
                      type="password"
                      value={props.values.password}
                      helperText={<ErrorMessage name="password"></ErrorMessage>}
                      error={props.errors.password}
                    />
                  </Grid>
                  <Grid
                    item
                    xs={5}
                    marginBottom={1}
                  >
                    <Field as={TextField}
            
                      id="repeatPassword"
                      label="Repeat Password"
                      type="password"
                      name="repeatPassword"
                      value={props.values.repeatPassword}
                      helperText={<ErrorMessage name="repeatPassword"></ErrorMessage>}
                      error={props.errors.repeatPassword}
                    />
                  </Grid>
                </Grid>
              </DialogContent>
              <DialogActions>
                <Button type="submit" disabled={props.isSubmitting} variant="contained" color="success">
                  Confirm
                </Button>
                <Button onClick={handleOpen} variant="contained">
                  Cancel
                </Button>
              </DialogActions>
            </Form>
          )}
        </Formik>
      </Dialog>
    </>
  );
};

export default AccountInfoForm;
