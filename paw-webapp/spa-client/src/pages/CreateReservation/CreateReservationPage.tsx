import * as React from 'react';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import QPeopleForm from './QPeopleForm';
import DateForm from './DateForm';
import HourForm from './HourForm';
import InfoForm from './InfoForm';
import InfoFormStatic from './InfoFormStatic';
import Done from './Done';
import { useState, FC } from "react";
import useCustomerService from "../../hooks/serviceHooks/customers/useCustomerService";
import useReservationService from "../../hooks/serviceHooks/reservations/useReservationService";
import useUserService from "../../hooks/serviceHooks/users/useUserService";

import { ReservationParams } from "../../models/Reservations/ReservationParams";
import { CustomerParams } from "../../models/Customers/CustomerParams";
import ShortRegisterForm from "./ShortRegisterForm";
import { UserParams } from "../../models/Users/UserParams";
import { useNavigate } from "react-router-dom";
import { paths } from "../../constants/constants";
import { extractCustomerIdFromContent, extractUserIdFromLocation } from "../SignUpPage";

import * as Yup from "yup";
import { Formik, Form, FormikHelpers } from "formik";
import { awaitWrapper, handleResponse } from '../../Utils';
import ApiErrorDetails from '../../models/ApiError/ApiErrorDetails';
import useAuth from '../../hooks/serviceHooks/authentication/useAuth';
import { CustomerModel, UserModel } from "../../models";
import { useTranslation } from 'react-i18next';
import useRestaurantService from '../../hooks/serviceHooks/restaurants/useRestaurantService';
import useAuthenticationService from '../../hooks/serviceHooks/authentication/useAutenticationService';
import useServices from '../../hooks/useServices';

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

const CreateReservation: FC = () => {
  const { customerService, reservationService, restaurantService, 
    authenticationService, userService } = useServices();
    
  const { setAuth } = useAuth();
  const { auth } = useAuth();
  const { t } = useTranslation();

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

  const today = new Date(); today.setHours(0, 0, 0, 0);
  const validationSchema = Yup.object().shape({
    qPeople: Yup.number().positive(t('validationSchema.positiveValidation')).required(t('validationSchema.required')),
    date: Yup.date().min(today, t('validationSchema.dateValidation')).required(t('validationSchema.required')),
    hour: Yup.number().positive(t('validationSchema.positiveValidation')).max(24, "Less than 24"),
    customerStep: Yup.boolean(),
    firstName: Yup.string().when("customerStep", { is: true, then: Yup.string().required(t('validationSchema.required')) }),
    lastName: Yup.string().when("customerStep", { is: true, then: Yup.string().required(t('validationSchema.required')) }),
    mail: Yup.string().when("customerStep", { is: true, then: Yup.string().email(t('validationSchema.mailValidation')).required(t('validationSchema.required')) }),
    phone: Yup.string().when("customerStep", { is: true, then: Yup.string().required(t('validationSchema.required')) }),
    userStep: Yup.boolean(),
    username: Yup.string().when("userStep", { is: true, then: Yup.string().min(8, t('validationSchema.passwordLength', { length: 8 })).required(t('validationSchema.required')) }),
    password: Yup.string().when("userStep", { is: true, then: Yup.string().required(t('validationSchema.required')) }),
    repeatPassword: Yup.string().when("userStep", {
      is: true, then: Yup.string()
        .oneOf([Yup.ref('password'), undefined], t('validationSchema.passwordMatch')).required(t('validationSchema.required'))
    }),
  })

  const [activeStep, setActiveStep] = React.useState(0);
  const [availableHours, setAvailableHours] = useState<number[]>([]);
  const [secCode, setResCode] = useState<string>("");
  const [customerId, setCustomerId] = useState<number>(0);

  const [user, setUser] = useState<UserModel>();
  const [customer, setCustomer] = useState<CustomerModel>();

  const resParams: ReservationParams = new ReservationParams();
  const cusParams: CustomerParams = new CustomerParams();
  const userParams: UserParams = new UserParams();
  const steps = [t('createReservation.step1.stepTitle'),
  t('createReservation.step2.stepTitle'),
  t('createReservation.step3.stepTitle'),
  t('createReservation.step4.stepTitle'),
  t('createReservation.step5.stepTitle')];

  let navigate = useNavigate();

  const handleNext = async (values: createReservationFormValues, props: FormikHelpers<createReservationFormValues>) => {
    const { qPeople, date, hour, firstName, lastName, mail, phone, username, password, repeatPassword } = values;

    switch (activeStep) {
      case 0: //qPeople
        if (auth.id > 0) { //there is a logged in user
          handleResponse(userService.getUserById(auth.id), (user: UserModel) =>
            setUser(user)
          );
        }
        break;
      case 1: //date
        if (auth.id > 0) { //there is a logged in user
          if (user !== undefined) {
            const custId = extractCustomerIdFromContent(user.content);
            setCustomerId(custId);
            handleResponse(customerService.getCustomerById(custId), (customer: CustomerModel) =>
              setCustomer(customer)
            );
          }
        }
        resParams.qPeople = qPeople;
        resParams.date = date.toString();
        resParams.restaurantId = 1;
        const { isOk, error, data: availableHoursApi } = await restaurantService.getAvailableHours(resParams);
        if (!isOk || !availableHoursApi) {
          props.setFieldError("date", t('forms.selectedDate.error'));
          props.setSubmitting(false);
          return;
        }
        setAvailableHours(availableHoursApi);
        break;

      case 2: //hour
        if (!availableHours.includes(hour)) {
          props.setFieldError("hour", `Select available hour`);
          props.setSubmitting(false);
          return;
        }
        if (customer !== undefined) {
          setActiveStep(activeStep + 2); //skip create customer
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
          props.setFieldError("customerName", errorData.errors?.find((e) => e.property === "customerName")?.description);
          props.setFieldError("email", errorData.errors?.find((e) => e.property === "email")?.description);
          props.setFieldError("phone", errorData.errors?.find((e) => e.property === "phone")?.description);
          props.setSubmitting(false);
          return;
        }

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
        setActiveStep(activeStep + 2); //skip one
        return;

      case 4:
        resParams.hour = hour;
        resParams.qPeople = qPeople;
        resParams.date = date.toString();
        resParams.restaurantId = 1;

        resParams.customerId = customer!.id;
        const { ok: resCreated2, error: resError2, response: resResponse2 } = await awaitWrapper(reservationService.createReservation(resParams));
        if (!resCreated2) {
          console.log(resError2);
          props.setSubmitting(false);
          return;
        }
        setResCode(resResponse2!.headers["location"]!.split("/reservations/")[1]);
        setActiveStep(activeStep + 3); //go to last
        return;

      case 5: //done
        props.setFieldValue("userStep", true);
        break;

      case 6: //short register
        userParams.username = username;
        userParams.psPair = { password: password, checkPassword: repeatPassword };
        userParams.role = "CUSTOMER";
        userParams.customerId = customerId;
        const { ok: userCreated, error: userError } = await awaitWrapper(userService.createUser(userParams));

        if (!userCreated) {
          const errorData = userError.response?.data as ApiErrorDetails;
          props.setFieldError("username", errorData.errors?.find((e) => e.property === "username")?.description);
          props.setFieldError("password", errorData.errors?.find((e) => e.property === "psPair")?.description);
          props.setSubmitting(false);
          return;
        }

        const newAuth = await authenticationService.tryLogin(username, password, props)

        if (!newAuth) {
          props.setSubmitting(false);
          return;
        }
        setAuth(newAuth);

        navigate(`${paths.ROOT}/reservations/${secCode}`);
        return;
        break;

      case 7:
        props.setFieldValue("userStep", true);
        navigate(`${paths.ROOT}/reservations/${secCode}`);
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
    if (curr === 0) {
      navigate(`${paths.ROOT}`);
    }
    if (curr === 4) {
      curr--;
    }
    if (curr === 5 || curr === 7) {
      navigate(`${paths.ROOT}/reservations/${secCode}`);
      return;
    }
    if (curr === 1) {
      props.setFieldValue("date", new Date(new Date().getTime() + 24 * 60 * 60 * 1000));
    }
    curr--;
    if (curr !== 3) {
      props.setFieldValue("customerStep", false);
    }
    if (curr !== 5 && curr !== 7) {
      props.setFieldValue("userStep", false);
    }
    setActiveStep(curr);
  };

  return (
    <Container component="main" maxWidth="md" sx={{ display: "flex", alignItems: "center", justifyContent: "center" }}>
      <Paper variant="outlined" sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
        <Typography variant="h4" align="center">{t('createReservation.pageTitle')}</Typography>
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
                        4: <InfoFormStatic customer={customer!} />,
                        5: <Done props={props} secCode={secCode} />,
                        6: <ShortRegisterForm props={props} />,
                        7: <Done props={props} secCode={secCode} />
                      }[activeStep]
                    }
                    <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                      <Button onClick={() => handleBack(props.values, props)} sx={{ mt: 3, ml: 1 }}
                        disabled={activeStep === 7}
                      >
                        {
                          {
                            0: t('createReservation.back'),
                            1: t('createReservation.back'),
                            2: t('createReservation.back'),
                            3: t('createReservation.back'),
                            4: t('createReservation.back'),
                            5: t('createReservation.continueWithoutSigning'),
                            6: t('createReservation.back'),
                            7: ''
                          }[activeStep]
                        }
                      </Button >
                      <Button type="submit" sx={{ mt: 3, ml: 1 }}
                        disabled={props.isSubmitting}
                      >
                        {
                          {
                            0: t('createReservation.next'),
                            1: t('createReservation.next'),
                            2: t('createReservation.next'),
                            3: t('createReservation.makeReservation'),
                            4: t('createReservation.makeReservation'),
                            5: t('createReservation.signUp'),
                            6: t('createReservation.goToReservation'),
                            7: t('createReservation.goToReservation')
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