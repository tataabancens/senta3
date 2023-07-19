import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grid,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import { FC } from "react";
import { DishCategoryModel, DishModel } from "../../models";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import { DishParams } from "../../models/Dishes/DishParams";
import UploadImage from "../UploadImage";
import { ErrorMessage, Field, Formik, FormikHelpers, Form } from "formik";
import * as Yup from "yup";
import useRestaurantMenuContext from "../../hooks/useRestaurantMenuContext";
import useImageService from "../../hooks/serviceHooks/useImageService";
import { useTranslation } from "react-i18next";
import useServiceProvider from "../../context/ServiceProvider";


type Props = {
  dish: DishModel;
  categoryList: DishCategoryModel[];
  setDishes: (dishes: DishModel[]) => void;
};

export interface createDishFormValue {
  name: string;
  categoryId: number;
  price: number;
  description: string;
  image: string | File;
}

const EditDishForm: FC<Props> = ({
  dish,
  categoryList,
}): JSX.Element => {

  const { dishService } = useServiceProvider();
  const imageService = useImageService();
  const { t } = useTranslation();

  const { useCurrentCategory: { categoryId }, getDishes: { updateDish, removeDish }, useEditDishIsOpen: { editDishIsOpen, setEditDishIsOpen } } = useRestaurantMenuContext();

  const handleSubmit = async (values: createDishFormValue, props: FormikHelpers<createDishFormValue>) => {
    const { name, price, description, categoryId: newCategory, image } = values;
    let imageId = undefined;

    if (typeof image !== 'string') {
      const formData = new FormData();
      formData.append('image', values.image);
      const imagePostResponse = await imageService.createImage(formData);
      imageId = imagePostResponse.data;
    }

    let newDish = new DishParams();
    newDish.name = name;
    newDish.price = price;
    newDish.id = dish.id;
    newDish.description = description;
    newDish.categoryId = newCategory;
    if (imageId) { newDish.imageId = imageId }
    const { isOk } = await dishService.editDish(newDish);

    if (!isOk) {
      // Handle error maybe
      console.log("No okey post");
    }

    const { isOk: isOkGetDish, data: dishData } = await dishService.getDishByIdNew(dish.id);

    if (!isOkGetDish) {
      // handle error
      console.log("No okey get");
    }

    const updatedDish = dishData as DishModel;

    if (newCategory === categoryId) {
      updateDish(updatedDish);
    } else {
      removeDish(updatedDish);
    }

    props.setSubmitting(false);
    setEditDishIsOpen(false);
  };

  const initialValue: createDishFormValue = {
    name: dish.name,
    categoryId: categoryId!,
    price: dish.price,
    description: dish.description,
    image: dish.image,
  }

  const validationSchema = Yup.object().shape({
    name: Yup.string().max(65, t('validationSchema.dishNameMaxCharacters',{characters: 65})),
    categoryId: Yup.number(),
    price: Yup.number(),
    description: Yup.string().max(70, t('validationSchema.dishDescriptioneMaxCharacters',{characters: 70})),
  })

  const handleCancel = () => {
    setEditDishIsOpen(false);
  }

  return (
    <div>
      <Dialog open={editDishIsOpen}>
        <Formik initialValues={initialValue} onSubmit={handleSubmit} validationSchema={validationSchema}>
          {(props) => (
            <Form>
              <DialogTitle align="center">{t('forms.editDish.title')}</DialogTitle>
              <DialogContent>
                <DialogContentText align="center">
                  {t('forms.editDish.description')}
                </DialogContentText>
                <Grid container marginY={3} >
                  <Grid
                    item
                    xs={12}
                    marginBottom={3}
                  >
                  </Grid>
                  <UploadImage props={props}></UploadImage>
                  <Grid
                    item
                    xs={12}
                    marginY={3}
                  ><Field as={TextField}
                    item
                    required
                    fullWidth
                    id="name"
                    label={t('forms.editDish.name')}
                    name="name"
                    helperText={<ErrorMessage name="name" />}
                    error={props.errors.name}
                    />
                  </Grid>
                  <Grid
                    item
                    xs={12}
                    marginBottom={3}
                  >
                    <Field as={TextField}
                      item
                      required
                      id="price"
                      label={t('forms.editDish.price')}
                      name="price"
                      helperText={<ErrorMessage name="price" />}
                      error={props.errors.price}
                    />
                  </Grid>
                  <Grid
                    item
                    xs={12}
                    marginBottom={3}
                  >
                    <Field as={Select}
                      required
                      fullWidth
                      id="categoryId"
                      label={t('forms.editDish.category')}
                      value={props.values.categoryId}
                      name="categoryId"
                      helperText={<ErrorMessage name="category" />}
                      error={props.errors.categoryId}
                    >
                      <MenuItem value={categoryId}>{t('forms.select')}</MenuItem>
                      {categoryList.map((category) => (
                        <MenuItem key={category.id} value={category.id}>
                          {category.name}
                        </MenuItem>
                      ))}
                    </Field>
                  </Grid>
                  <Grid
                    item
                    xs={12}
                    marginBottom={3}
                  >
                    <Field as={TextField}
                      item
                      required
                      fullWidth
                      multiline
                      rows={3}
                      id="description"
                      label={t('forms.editDish.dishDescription')}
                      name="description"
                      helperText={<ErrorMessage name="description" />}
                      error={props.errors.description}
                    />
                  </Grid>
                </Grid>
              </DialogContent>
              <DialogActions>
                <Button type="submit" variant="contained" color="success">
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
    </div>
  );
}

export default EditDishForm;