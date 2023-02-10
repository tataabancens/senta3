import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Button from '@mui/material/Button';
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import QPeopleForm from './QPeopleForm';
import DateForm from './DateForm';
import HourForm from './HourForm';
import InfoForm from './InfoForm';
import Done from './Done';
import { useState, createContext, useContext } from "react";
// import {customerService, reservationService, userService} from "../../services";
import useCustomerService from "../../hooks/serviceHooks/useCustomerService";
import useReservationService from "../../hooks/serviceHooks/useReservationService";
import useUserService from "../../hooks/serviceHooks/useUserService";
import axios from "../../api/axios";

import { ReservationParams } from "../../models/Reservations/ReservationParams";
import { CustomerParams } from "../../models/Customers/CustomerParams";
import ShortRegisterForm from "./ShortRegisterForm";
import { UserParams } from "../../models/Users/UserParams";
import { useNavigate } from "react-router-dom";
import {paths} from "../../constants/constants";
import { extractUserIdFromLocation } from "../SignUpPage";

import * as Yup from "yup";
import { Formik, Form, Field, FormikHelpers, ErrorMessage } from "formik";
import { awaitWrapper, loginErrorHandler, tryLogin } from '../../Utils';
import ApiErrorDetails from '../../models/ApiError/ApiErrorDetails';
import useAuth from '../../hooks/useAuth';


function Copyright() {
  return (
    <Typography variant="body2" color="text.secondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="https://www.tribuna.com.mx/u/fotografias/m/2022/8/29/f768x1-284455_284582_92.jpg">
        Pepito Masterchef
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

export interface createReservationFormValues {
  qPeople: number;
  date: Date;
  hour: number;
  customerStep: boolean;
  firstName: string;
  lastName: string;
  mail: string;
  phone: string;
  userStep: boolean;
  username: string;
  password: string;
  repeatPassword: string;
}

interface AvailableHours {
  availableHours: number[];
}

const steps = ['How many?', 'Date?', 'Hour?', 'Contact info', 'Done!'];

const theme = createTheme();

const CreateReservation = () => {
    const customerService = useCustomerService();
    const reservationService = useReservationService();
    const userService = useUserService();
  const { setAuth } = useAuth();

  const initialValues: createReservationFormValues = {
    qPeople: 1,
    date: new Date(new Date().getTime() + 24 * 60 * 60 * 1000),
    hour: 1,
    firstName: "",
    lastName: "",
    mail: "",
    phone: "",
    customerStep: false,
    userStep: false,
    username: "",
    password: "",
    repeatPassword: "",
  }

  const validationSchema = Yup.object().shape({
    qPeople: Yup.number().positive("Greater than 0").required("Required"),
    date: Yup.date().min(new Date(), "Date must be in the future").required("Required"),
    hour: Yup.number().positive("Greater than 0").max(24, "Less than 24"),
    customerStep: Yup.boolean(),
    firstName: Yup.string().when("customerStep", { is: true, then: Yup.string().required("Required") }),
    lastName: Yup.string().when("customerStep", { is: true, then: Yup.string().required("Required") }),
    mail: Yup.string().when("customerStep", { is: true, then: Yup.string().email('Enter valid email').required("Required") }),
    phone: Yup.string().when("customerStep", { is: true, then: Yup.string().required("Required") }),
    userStep: Yup.boolean(),
    username: Yup.string().when("userStep", { is: true, then: Yup.string().min(8, "Minimun 8 caracters long").required("Required") }),
    password: Yup.string().when("userStep", { is: true, then: Yup.string().required("Required") }),
    repeatPassword: Yup.string().when("userStep", {
      is: true, then: Yup.string()
        .oneOf([Yup.ref('password'), undefined], 'Passwords must match').required("Required")
    }),
  })

  const [activeStep, setActiveStep] = React.useState(0);
  const [availableHours, setAvailableHours] = useState<number[]>([]);
  const [secCode, setResCode] = useState<string>("");
  const [customerId, setCustomerId] = useState<number>(0);



  const resParams: ReservationParams = new ReservationParams();
  const cusParams: CustomerParams = new CustomerParams();
  const userParams: UserParams = new UserParams();

  let navigate = useNavigate();

  const handleNext = async (values: createReservationFormValues, props: FormikHelpers<createReservationFormValues>) => {
    const { qPeople, date, hour, firstName, lastName, mail, phone, username, password, repeatPassword } = values;

    switch (activeStep) {
      case 0: //qPeople
        break;
      case 1: //date
        resParams.qPeople = qPeople;
        resParams.date = date.toString();
        resParams.restaurantId = 1;
        const { ok, error, response } = await awaitWrapper<AvailableHours>(reservationService.getAvailableHours(resParams));
        if (!ok) {
          props.setFieldError("date", error.message);
          props.setSubmitting(false);
          return;
        }
        const data = response as any as AvailableHours;
        setAvailableHours(data.availableHours);
        break;
      case 2: //hour
        if (!availableHours.includes(hour)) {
          props.setFieldError("hour", `Select available hour`);
          props.setSubmitting(false);
          return;
        }
        props.setFieldValue("customerStep", true);
        break;
      case 3: //customer info
        cusParams.mail = mail;
        cusParams.customerName = firstName + " " + lastName;
        cusParams.phone = phone;
        const { ok: customerCreated, error: customerError, response: customerResponse } = await awaitWrapper(customerService.createCustomer(cusParams));
        if (!customerCreated) {
          const errorData = customerError.response?.data as ApiErrorDetails;
          props.setFieldError("customerName", errorData.errors?.find((e) => e.property == "customerName")?.description);
          props.setFieldError("email", errorData.errors?.find((e) => e.property == "email")?.description);
          props.setFieldError("phone", errorData.errors?.find((e) => e.property == "phone")?.description);
          props.setSubmitting(false);
          return;
        }
        console.log(customerResponse.headers.location);
        const custId = extractUserIdFromLocation(customerResponse.headers.location!);
        setCustomerId(custId);

        resParams.hour = hour;
        resParams.qPeople = qPeople;
        resParams.date = date.toString();
        resParams.restaurantId = 1;

        resParams.customerId = custId;
        const { ok: resCreated, error: resError, response: resResponse } = await awaitWrapper(reservationService.createReservation(resParams));
        if (!resCreated) {
          console.log(resError);
          props.setSubmitting(false);
          return;
        }
        setResCode(resResponse!.headers["location"]!.split("/reservations/")[1]);
        break;
      case 4:
        props.setFieldValue("userStep", true);
        break;
      case 5:

        userParams.username = username;
        userParams.psPair = { password: password, checkPassword: repeatPassword };
        userParams.role = "CUSTOMER";
        userParams.customerId = customerId;
        const { ok: userCreated, error: userError, response: userResponse } = await awaitWrapper(userService.createUser(userParams));
        
        if (!userCreated) {
          const errorData = userError.response?.data as ApiErrorDetails;
          props.setFieldError("username", errorData.errors?.find((e) => e.property == "username")?.description);
          props.setFieldError("password", errorData.errors?.find((e) => e.property == "psPair")?.description);
          props.setSubmitting(false);
          return;
        }

        const path = `users/auth`;
        await tryLogin<createReservationFormValues>(axios, username, password,
          props, path, setAuth, loginErrorHandler<createReservationFormValues>);

        navigate(`/reservation/${secCode}`);
        return;
        break;
      default:
        break;
    }
    props.setSubmitting(false);
    setActiveStep(activeStep + 1);
  };

  const handleBack = (values: createReservationFormValues, props: FormikHelpers<createReservationFormValues>) => {
    let curr = activeStep;
    if (curr == 4) {
      navigate(`/reservation/${secCode}`);
      return;
    }
    if (curr === 1) {
      props.setFieldValue("date", new Date(new Date().getTime() + 24 * 60 * 60 * 1000));
    }
    curr--;
    if (curr !== 3) {
      props.setFieldValue("customerStep", false);
    }
    if (curr !== 5) {
      props.setFieldValue("userStep", false);
    }
    setActiveStep(curr);
  };

  return (
      <Container component="main" maxWidth="sm" sx={{ mb: 4 }}>
        <Paper variant="outlined" sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
          <Typography component="h1" variant="h4" align="center">
            Create Reservation
          </Typography>
          <Stepper activeStep={activeStep} sx={{ pt: 3, pb: 5 }}>
            {steps.map((label) => (
              <Step key={label}>
                <StepLabel>{label}</StepLabel>
              </Step>
            ))}
          </Stepper>
          <Formik initialValues={initialValues} onSubmit={handleNext} validationSchema={validationSchema}>
            {
              (props) => (
                <Form>
                  {
                    <React.Fragment>
                      {
                        {
                          0: <QPeopleForm props={props} />,
                          1: <DateForm props={props} />,
                          2: <HourForm props={props} availableHours={availableHours} />,
                          3: <InfoForm props={props} />,
                          4: <Done props={props} secCode={secCode} />,
                          5: <ShortRegisterForm props={props} />
                        }[activeStep]
                      }
                      <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                        <Button onClick={() => handleBack(props.values, props)} sx={{ mt: 3, ml: 1 }}>
                          {activeStep == 4 ? 'Continue without signing up' : 'Back'}
                        </Button>
                        <Button type="submit" sx={{ mt: 3, ml: 1 }}
                          disabled={props.isSubmitting}
                        >
                          {
                            {
                              0: 'Next',
                              1: 'Next',
                              2: 'Next',
                              3: 'Place order',
                              4: 'Sign up',
                              5: 'Continue to reservation'
                            }[activeStep]
                          }
                        </Button>
                      </Box>
                    </React.Fragment>
                  }
                </Form>
              )
            }
          </Formik>
        </Paper>
      </Container>
  );
}

export default CreateReservation;