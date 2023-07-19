import {
  Box,
  Button,
  Grid,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import { useEffect, useRef, FC } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import Image from "../commons/restaurantPicture.jpg";
import { paths } from "../constants/constants";
import useAuth from "../hooks/serviceHooks/authentication/useAuth";
import { Formik, Form, Field, FormikHelpers, ErrorMessage } from "formik";
import * as Yup from "yup";
import { useTranslation } from "react-i18next";
import useAuthenticationService from "../hooks/serviceHooks/authentication/useAutenticationService";

export interface loginFormValues {
  username: string;
  password: string;
  remember: boolean;
}

const LoginPage: FC = () => {

  const { setAuth } = useAuth();
  const { t } = useTranslation();
  const authenticationService = useAuthenticationService();

  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || paths.ROOT;

  const userRef = useRef<HTMLInputElement>();

  const initialValues: loginFormValues = {
    username:'',
    password:'',
    remember: false,
  }

  const validationSchema = Yup.object().shape({
    username: Yup.string().required(t('validationSchema.required')),
    password: Yup.string().required(t('validationSchema.required'))
  })

  useEffect(() => {
    userRef.current?.focus();
  }, []);

  const handleSubmit = async (values: loginFormValues, props: FormikHelpers<loginFormValues>) => {
    const {username, password} = values;
    
    const auth = await authenticationService.tryLogin(username, password, props)
    
    if (!auth) {
      props.setSubmitting(false);
      return;
    }
    setAuth(auth);
    props.setSubmitting(false);
    navigate(from, { replace: true });
  };

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
          <Typography variant="h5">{t('loginPage.loginTitle')}</Typography>
          <Box
            sx={{ mt: 1 }}
          >
            <Formik initialValues={initialValues} onSubmit={handleSubmit} validationSchema={validationSchema}>
              {(props) => (
                <Form>
                  <Field as={TextField}
                    inputRef={userRef}
                    margin="normal"
                    required
                    fullWidth
                    id="username"
                    label={t('loginPage.label.username')}
                    name="username"
                    autoComplete="username"
                    helperText={<ErrorMessage name="username"/>}
                    error={!!props.errors.username}
                  />
                  <Field as={TextField}
                    margin="normal"
                    required
                    fullWidth
                    name="password"
                    label={t('loginPage.label.password')}
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    helperText={<ErrorMessage name="password"/>}
                    error={!!props.errors.password}
                  />
                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    disabled={props.isSubmitting}
                    sx={{ mt: 3, mb: 2 }}
                  >
                    {!props.isSubmitting? t('loginPage.formSubmit.login'): t('loginPage.formSubmit.loading')}
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
              <Grid item>
                <Link
                    to={paths.ROOT + "/signUp"}
                    style={{ textDecoration: "none", color: "black" }}
                >
                  {t('loginPage.registerCallToAction')}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Grid>
    </Grid>
  );
}

export default LoginPage;
