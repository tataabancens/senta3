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
import { FC } from "react";
import { awaitWrapper, handleResponse } from "../../Utils";
import useUserService from "../../hooks/serviceHooks/users/useUserService";
import useRestaurantService from "../../hooks/serviceHooks/restaurants/useRestaurantService";
import useCustomerService from "../../hooks/serviceHooks/customers/useCustomerService";
import { CustomerModel, RestaurantModel, UserModel } from "../../models";
import { CustomerParams } from "../../models/Customers/CustomerParams";
import { RestParams } from "../../models/Restaurant/RestParams";
import { UserParams } from "../../models/Users/UserParams";
import { Formik, Form, Field, FormikHelpers, ErrorMessage } from "formik";
import * as Yup from "yup";
import ApiErrorDetails from "../../models/ApiError/ApiErrorDetails";
import { useTranslation } from "react-i18next";
import { UserRoles } from "../../models/Enums/UserRoles";
import useAuth from "../../hooks/serviceHooks/authentication/useAuth";
import useServiceProvider from "../../context/ServiceProvider";

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
  data: CustomerModel | RestaurantModel;
  isOpen: boolean;
  handleOpen: () => void;
  reload: () => void;
};

const AccountInfoForm: FC<Props> = ({
  data,
  handleOpen,
  isOpen,
  reload,
}): JSX.Element => {

  const { auth, setAuth } = useAuth();
  const { t } = useTranslation();
  const initialValues: accountInfoFormValues = {
    restaurantName: data.name,
    customerName: data.name,
    username: auth.user,
    phone: data.phone,
    mail: data.mail,
    isRestaurantUser: true,
    password: '',
    repeatPassword: '',
  }

  const validationSchema = Yup.object().shape({
    restaurantName: Yup.string().when("isRestaurantUser", { is: true, then: Yup.string().required(t('validationSchema.required')) }),
    customerName: Yup.string().when("isRestaurantUser", { is: false, then: Yup.string().required(t('validationSchema.required')) }),
    isRestaurantUser: Yup.boolean(),
    username: Yup.string().required(t('validationSchema.required')),
    phone: Yup.string().required(t('validationSchema.required')),
    mail: Yup.string().required(t('validationSchema.required')),
    password: Yup.string().min(8, t('validationSchema.passwordLength', { length: 8 })),
    repeatPassword: Yup.string()
      .oneOf([Yup.ref('password'), null], t('validationSchema.passwordMatch')),

  })

  const { userService, restaurantService, customerService: cs } = useServiceProvider();

  const handleSubmit = async (values: accountInfoFormValues, props: FormikHelpers<accountInfoFormValues>) => {
    const { restaurantName, customerName, mail, phone, password, repeatPassword } = values;

    if (auth?.roles[0] === UserRoles.RESTAURANT) {
      let restaurantData = new RestParams();
      restaurantData.mail = mail;
      restaurantData.restaurantId = data?.id;
      restaurantData.restaurantName = restaurantName;
      restaurantData.phone = phone;
      const { isOk } = await restaurantService.editRestaurant(restaurantData);
      if (isOk) {
        setAuth({ ...auth });
      }

    } else {
      let customerData = new CustomerParams();
      customerData.customerId = data?.id;
      customerData.customerName = customerName;
      customerData.mail = mail;
      customerData.phone = phone;
      const { isOk } = await cs.editCustomer(customerData);
      if (isOk) {
        setAuth({ ...auth });
      }
    }

    let userData = new UserParams();
    userData.userId = auth.id;
    if (password !== '' && repeatPassword !== '') {
      userData.psPair = { password: password, checkPassword: repeatPassword };
    }

    const { ok: userEdited, error: userError, response: userResponse } = await awaitWrapper(userService.editUser(userData));

    if (!userEdited) {
      const errorData = userError.response?.data as ApiErrorDetails;
      props.setFieldError("username", errorData.errors?.find((e) => e.property == "username")?.description);
      props.setSubmitting(false);
      return;
    }

    handleOpen();
  };

  return (
    <>
      <Dialog open={isOpen}>
        <Formik initialValues={initialValues} onSubmit={handleSubmit} validationSchema={validationSchema}>
          {(props) => (
            <Form>
              <DialogTitle>{t('forms.accountInfo.title')}</DialogTitle>
              <DialogContent>
                <DialogContentText>
                  {t('forms.accountInfo.description')}
                </DialogContentText>
                <Grid container marginY={3}>
                  {auth?.roles[0] === UserRoles.RESTAURANT ? (
                    <Grid
                      item
                      xs={3}
                      marginBottom={3}
                    >
                      <Field as={TextField}
                        required
                        id="restaurantName"
                        label={t('forms.accountInfo.restaurantName')}
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
                        label={t('forms.accountInfo.customerName')}
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
                    marginBottom={1}
                  >
                    <Field as={TextField}
                      required
                      id="mail"
                      label={t('forms.accountInfo.mail')}
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
                      label={t('forms.accountInfo.phone')}
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
                      label={t('forms.accountInfo.password')}
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
                      label={t('forms.accountInfo.passwordRepeat')}
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
                  {t('forms.confirmButton')}
                </Button>
                <Button onClick={handleOpen} variant="contained">
                  {t('forms.cancelButton')}
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
