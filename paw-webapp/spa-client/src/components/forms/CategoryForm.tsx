import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField } from "@mui/material";
import { FC, useState } from "react";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import { DishParams } from "../../models/Dishes/DishParams";
import useRestaurantMenuContext from "../../hooks/useRestaurantMenuContext";
import { useTranslation } from "react-i18next";
import { Formik, Form, Field, FormikHelpers, ErrorMessage } from "formik";
import * as Yup from "yup";
import useServiceProvider from "../../context/ServiceProvider";

type Props = {
  isOpen: boolean;
  handleOpen: () => void;
  canReload?: () => void;
};

interface createCategoryFormValue {
  categoryName: string;
}

const CategoryForm: FC<Props> = ({ isOpen, handleOpen, canReload }): JSX.Element => {

  // const [categoryName, setCategoryName] = useState("");
  const { dishService } = useServiceProvider();
  const { t } = useTranslation();
  const { getDishCategories: { categoryList, categoryMap, setCategories, setCategoryMap }, useCurrentCategory: { setCategoryId } } = useRestaurantMenuContext();

  const handleCancel = () => {
    handleOpen();
  }

  const initialValue: createCategoryFormValue = {
    categoryName: '',
  }

  const validationSchema = Yup.object().shape({
    categoryName: Yup.string().matches(/^[a-zA-Z 0-9,.'-]+$/, t('validationSchema.categoryNameValidation')).required(t('validationSchema.required'))
  })

  const handleSubmit = async (values: createCategoryFormValue, props: FormikHelpers<createCategoryFormValue>) => {
    const { categoryName } = values;
    if (categoryName.length > 0) {

      let dishParams = new DishParams();
      dishParams.categoryName = categoryName;
      const { isOk, data: categoryId, error } = await dishService.createCategory(dishParams);

      if (!isOk) {
        props.setFieldError("categoryName", t('forms.categoryName.duplicate'));
        props.setSubmitting(false);
        return;
      }

      const getDishParams = new DishParams();
      getDishParams.categoryId = categoryId!;
      const { isOk: getCategoryIsOk, data: newCategory, error: getCategoryError } = await dishService.getDishCategory(getDishParams);

      if (!getCategoryIsOk) {
        console.log(getCategoryError);
      }

      setCategories([...categoryList!, newCategory!]);

      const updatedCategoryMap = new Map(categoryMap);
      updatedCategoryMap.set(newCategory!.id, newCategory!);
      setCategoryMap(updatedCategoryMap);

      setCategoryId(newCategory!.id);
    }
    handleOpen();
  }

  return (
    <Dialog open={isOpen} fullWidth>
      <Formik initialValues={initialValue} onSubmit={handleSubmit} validationSchema={validationSchema}>
        {(props) => (
          <Form>
            <DialogTitle>{t('forms.createCategory.title')}</DialogTitle>
            <DialogContent>
              <DialogContentText>
                {t('forms.createCategory.description')}
              </DialogContentText>
              <Grid container marginY={3}>
                <Grid item xs={12} marginBottom={3}>
                  <Field as={TextField}
                    item
                    fullWidth
                    xs={12}
                    required
                    id="categoryName"
                    label={t('forms.createCategory.label')}
                    name="categoryName"
                    helperText={<ErrorMessage name="categoryName" />}
                    error={props.errors.categoryName}
                  />
                </Grid>
              </Grid>
            </DialogContent>
            <DialogActions>
              <Button type="submit" variant="contained" color="success" disabled={props.isSubmitting}>
                {t('forms.confirmButton')}
              </Button>
              <Button onClick={handleCancel} variant="contained">
                {t('forms.cancelButton')}
              </Button>
            </DialogActions>
          </Form>
        )}
      </Formik>
    </Dialog>
  );
}

export default CategoryForm;