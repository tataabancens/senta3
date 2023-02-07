import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Toolbar from '@mui/material/Toolbar';
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


import {ReservationParams} from "../../models/Reservations/ReservationParams";
import {CustomerParams} from "../../models/Customers/CustomerParams";
import ShortRegisterForm from "./ShortRegisterForm";
import {UserParams} from "../../models/Users/UserParams";
import { useNavigate } from "react-router-dom";

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

const steps = ['How many?', 'Date?', 'Hour?', 'Contact info', 'Done!'];

const theme = createTheme();

export default function CreateReservation() {
    const customerService = useCustomerService();
    const reservationService = useReservationService();
    const userService = useUserService();
  const [activeStep, setActiveStep] = React.useState(0);
  const [qPeople, setqPeople] = useState<number>(1);
  const [date, setDate] = useState<string>("");
  const [hour, setHour] = useState<number>(0);
  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [mail, setMail] = useState<string>("");
  const [phone, setPhone] = useState<string>("");
  const [availableHours, setAvailableHours] = useState<number[]>([]);
  const [secCode, setResCode] = useState<string>("");
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [repeatPassword, setRepeatPassword] = useState<string>("");
  const [customerId, setCustomerId] = useState<number>(0);



    const resParams: ReservationParams = new ReservationParams();
    const cusParams: CustomerParams = new CustomerParams();
    const userParams: UserParams = new UserParams();

    let navigate = useNavigate();


    const handleNext = async () => {
        switch (activeStep) {
            case 0: //qPeople
                if(qPeople<=0){
                    return;
                }
                break;
            case 1: //date
                resParams.qPeople = qPeople;
                resParams.date = date;
                resParams.restaurantId = 1;
                const a = await reservationService.getAvailableHours(resParams);
                setAvailableHours(a);
                break;
            case 2: //hour
                if(hour == 0){
                    return;
                }
                break;
            case 3: //customer info
                if(firstName == "" || lastName == "" || mail == "" || phone == "") {
                    return;
                }
                cusParams.mail = mail;
                cusParams.customerName = firstName + " " + lastName;
                cusParams.phone = phone;
                const newCustomer = await customerService.createCustomer(cusParams);
                if(newCustomer.status != 201){
                    return;
                }
                resParams.hour=hour;
                resParams.qPeople=qPeople;
                resParams.date = date;
                resParams.restaurantId=1;
                // @ts-ignore
                setCustomerId(newCustomer.headers["location"].split("/customers/")[1]);
                resParams.customerId = customerId;
                const newReservation = await reservationService.createReservation(resParams);
                if(newReservation.status != 201){
                    return;
                }
                // @ts-ignore
                setResCode(newReservation.headers["location"].split("/reservations/")[1])
                break;
            case 4:
                break;
            case 5:
                if(username == "" || password == "" || repeatPassword == "" || password != repeatPassword){
                    return;
                }
                userParams.username = username;
                userParams.psPair = {password: password, checkPassword: repeatPassword};
                userParams.role = "CUSTOMER";
                userParams.customerId = customerId;
                console.log("userParams");
                console.log(userParams);
                const newUser = await userService.createUser(userParams);
                console.log("new user:");
                console.log(newUser);
                navigate(`/reservation/${secCode}`);
                return;
                break;
            default:
                    break;
        }
        setActiveStep(activeStep + 1);
    };

  const handleBack = () => {
      if(activeStep == 4){
          navigate(`/reservation/${secCode}`);
          return;
      }
      setActiveStep(activeStep - 1);
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AppBar
        position="absolute"
        color="default"
        elevation={0}
        sx={{
          position: 'relative',
          borderBottom: (t) => `1px solid ${t.palette.divider}`,
        }}
      >
      </AppBar>
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
          {
            <React.Fragment>
              {
                  {
                      0: <QPeopleForm qPeople={qPeople} setqPeople={setqPeople}/>,
                      1: <DateForm date={date} setDate={setDate}/>,
                      2: <HourForm hour={hour} setHour={setHour} availableHours={availableHours}/>,
                      3: <InfoForm firstName={firstName} lastName={lastName} mail={mail} phone={phone} setFirstName={setFirstName} setLastName={setLastName} setMail={setMail} setPhone={setPhone}/>,
                      4: <Done date={date} hour={hour} qPeople={qPeople} secCode={secCode}/>,
                      5: <ShortRegisterForm firstName={firstName} lastName={lastName} mail={mail} phone={phone} username={username} password={password} repeatPassword={repeatPassword} setUsername={setUsername} setPassword={setPassword} setRepeatPassword={setRepeatPassword}/>
                  }[activeStep]
              }
              <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                <Button onClick={handleBack} sx={{ mt: 3, ml: 1 }}>
                    {activeStep == 4 ? 'Continue without signing up' : 'Back'}
                </Button>
                <Button onClick={handleNext} sx={{ mt: 3, ml: 1 }}>
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
        </Paper>
        <Copyright />
      </Container>
    </ThemeProvider>
  );
}