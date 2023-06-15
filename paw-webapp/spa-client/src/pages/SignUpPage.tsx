import {
  Box,
  Button,
  Grid,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import React, { useRef, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import Image from "../commons/restaurantPicture2.jpg";
import { Formik, Form, Field, FormikHelpers, ErrorMessage } from "formik";
import * as Yup from "yup";
import { handleResponse, handleFormResponse, awaitWrapper, loginErrorHandler, tryLogin } from "../Utils";
import useUserService from "../hooks/serviceHooks/useUserService";
import { UserParams } from "../models/Users/UserParams";
import ApiErrorDetails, { ApiError } from "../models/ApiError/ApiErrorDetails";
import { CustomerService } from "../services/customer/CustomerService";
import useCustomerService from "../hooks/serviceHooks/useCustomerService";
import { CustomerParams } from "../models/Customers/CustomerParams";
import axios from "../api/axios"
import useAuth from "../hooks/useAuth";
import { paths } from "../constants/constants";

interface signUpFormValues {
  username: string;
  password: string;
  passwordRepeat: string;
  customerName: string;
  email: string;
  phone: string;
}

export const extractUserIdFromLocation = (location: string): number=> {
  const parts = location.split('/');
  const stringValue = parts.pop();
  if (stringValue === undefined) {
    return -1;
  }
  return parseInt(stringValue);
}

export const extractCustomerIdFromContent = (content: string): number=> {
  const parts = content.split('/');
  const stringValue = parts.pop();
  if (stringValue === undefined) {
    return -1;
  }
  return parseInt(stringValue);
}

const SignUpPage = () => {

  const userService = useUserService();
  const customerService = useCustomerService();

  const navigate = useNavigate();

  const { setAuth } = useAuth();

  const initialValues: signUpFormValues = {
    username: '',
    password: '',
    passwordRepeat: '',
    customerName: '',
    email: '',
    phone: ''
  }

  const validationSchema = Yup.object().shape({
    username: Yup.string().required("Required"),
    password: Yup.string().min(8, "Minimun 8 caracters long").required("Required"),
    passwordRepeat: Yup.string()
      .oneOf([Yup.ref('password'), null], 'Passwords must match').required("Required"),
    customerName: Yup.string().required("Required"),
    email: Yup.string().email('Enter valid email').required("Required"),
    phone: Yup.string().required("Required")
  })

  const handleSubmit = async (values: signUpFormValues, props: FormikHelpers<signUpFormValues>) => {
    const { username, password, passwordRepeat, customerName, email, phone } = values;

    const userParams = new UserParams();
    userParams.username = username;
    userParams.psPair = {
      password,
      checkPassword: passwordRepeat,
    }
    userParams.role = "CUSTOMER";
    const { ok: userCreated, error: userError, response: userResponse } = await awaitWrapper(userService.createUser(userParams));

    if (!userCreated) {
      const errorData = userError.response?.data as ApiErrorDetails;
      props.setFieldError("username", errorData.errors?.find((e) => e.property == "username")?.description);
      props.setFieldError("password", errorData.errors?.find((e) => e.property == "psPair")?.description);
      props.setSubmitting(false);
      return;
    }

    const { location } = userResponse.headers;
    const userId = extractUserIdFromLocation(location!);

    const customerParams = new CustomerParams();
    customerParams.customerName = customerName;
    customerParams.mail = email;
    customerParams.phone = phone;
    customerParams.userId = userId;

    const { ok: customerCreated, error: customerError, response: customerResponse } = await awaitWrapper(customerService.createCustomer(customerParams));
    if (!customerCreated) {
      const errorData = customerError.response?.data as ApiErrorDetails;
      props.setFieldError("customerName", errorData.errors?.find((e) => e.property == "customerName")?.description);
      props.setFieldError("email", errorData.errors?.find((e) => e.property == "email")?.description);
      props.setFieldError("phone", errorData.errors?.find((e) => e.property == "phone")?.description);
      props.setSubmitting(false);
      return;
    }

    const path = `users/auth`;
    await tryLogin<signUpFormValues>(axios, username, password,
      props, path, setAuth, loginErrorHandler<signUpFormValues>);

    navigate(paths.ROOT + "/profile", { replace: true });
  }

  return (
    <Grid container component="main" sx={{ height: "100vh" }}>
      <Grid
        item
        xs={false}
        sm={6}
        md={9}
        sx={{
          backgroundImage: `url(${Image})`,
          backgroundRepeat: "no-repeat",
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      />
      <Grid item xs={12} sm={6} md={3} component={Paper} elevation={6} square>
        <Box
          sx={{
            my: 8,
            mx: 4,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Typography component="h1" variant="h5">
            Register
          </Typography>
          <Box
            sx={{ mt: 1 }}
          >
            <Formik initialValues={initialValues} onSubmit={handleSubmit} validationSchema={validationSchema}>
              {(props) => (
                <Form>
                  <Field as={TextField}
                    // inputRef={userRef}
                    margin="normal"
                    required
                    fullWidth
                    id="email"
                    label="email address"
                    name="email"
                    autoComplete="email"
                    autoFocus
                    helperText={<ErrorMessage name="email"></ErrorMessage>}
                    error={!!props.errors.email}
                  />
                  <Field as={TextField}
                    margin="normal"
                    required
                    fullWidth
                    id="customerName"
                    label="name"
                    name="customerName"
                    autoComplete="username"

                    helperText={<ErrorMessage name="customerName" />}
                    error={!!props.errors.customerName}
                  />
                  <Field as={TextField}
                    margin="normal"
                    required
                    fullWidth
                    id="username"
                    label="username"
                    name="username"
                    autoComplete="username"

                    helperText={<ErrorMessage name="username" />}
                    error={!!props.errors.username}
                  />
                  <Field as={TextField}
                    margin="normal"
                    required
                    fullWidth
                    id="phone"
                    label="phone"
                    name="phone"
                    autoComplete="phone"

                    helperText={<ErrorMessage name="phone" />}
                    error={!!props.errors.phone}
                  />
                  <Field as={TextField}
                    margin="normal"
                    required
                    fullWidth
                    name="password"
                    label="password"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    helperText={<ErrorMessage name="password" />}
                    error={!!props.errors.password}
                  />
                  <Field as={TextField}
                    margin="normal"
                    required
                    fullWidth
                    name="passwordRepeat"
                    label="repeat password"
                    type="password"
                    id="passwordRepeat"
                    autoComplete="current-password"
                    helperText={<ErrorMessage name="passwordRepeat" />}
                    error={!!props.errors.passwordRepeat}
                  />

                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    disabled={props.isSubmitting}
                    sx={{ mt: 3, mb: 2 }}
                  >
                    Sign Up
                  </Button>
                </Form>
              )}

            </Formik>
            <Grid
              container
              sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                mt: 1,
              }}
              spacing={2}
            >
              <Grid item xs>
                <Link
                  to="/login"
                  style={{ textDecoration: "none", color: "black" }}
                >
                  Already have account? login!
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Grid>
    </Grid>
  );
}

export default SignUpPage;