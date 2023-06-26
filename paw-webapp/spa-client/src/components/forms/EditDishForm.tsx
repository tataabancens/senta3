import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  SelectChangeEvent,
  TextField,
} from "@mui/material";
import { FC, useState } from "react";
import { handleResponse } from "../../Utils";
import { DishCategoryModel, DishModel } from "../../models";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import { DishParams } from "../../models/Dishes/DishParams";
import UploadImage from "../UploadImage";
import { ErrorMessage, Field, Formik, FormikHelpers, Form } from "formik";
import * as Yup from "yup";
import useRestaurantMenuContext from "../../hooks/useRestaurantMenuContext";
import useImageService from "../../hooks/serviceHooks/useImageService";


type Props = {
  dish: DishModel;
  isOpen: boolean;
  categoryList: DishCategoryModel[];
  handleOpen: () => void;
  setDishes: (dishes: DishModel[]) => void;
  dishes: DishModel[];
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
  handleOpen,
  isOpen,
  categoryList,
  setDishes,
  dishes
}): JSX.Element => {

  const dishService = useDishService();
  const imageService = useImageService();

  const { useCurrentCategory: { categoryId }, getDishes: { updateDish, removeDish } } = useRestaurantMenuContext();
  
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
    const { isOk, data, error } = await dishService.editDish(newDish);

    if (!isOk) {
      // Handle error maybe
      console.log("No okey post");
    }

    const { isOk: isOkGetDish, data: dishData, error: errorGetDish } = await dishService.getDishByIdNew(dish.id);

    if (!isOkGetDish) {
      // handle error
      console.log("No okey get");
    }

    const updatedDish = dishData as DishModel;

    if (newCategory === categoryId) {
      updateDish(updatedDish);
    } else {
      console.log("Hola");
      removeDish(updatedDish);
    }

    props.setSubmitting(false);
    handleOpen();
  };

  const initialValue: createDishFormValue = {
    name: dish.name,
    categoryId: categoryId!,
    price: dish.price,
    description: dish.description,
    image: dish.image,
  }

  const validationSchema = Yup.object().shape({
    name: Yup.string(),
    categoryId: Yup.number(),
    price: Yup.number(),
    description: Yup.string(),
    // image: Yup.mixed().optional()
    //   .test("FILE_TYPE", "Invalid file type", (value: any) => value && ['image/png', 'image/jpeg'].includes(value.type)),
  })

  const handleCancel = () => {
    handleOpen()
  }

  return (
    <div>
      <Dialog open={isOpen}>
        <Formik initialValues={initialValue} onSubmit={handleSubmit} validationSchema={validationSchema}>
          {(props) => (
            <Form>
              <DialogTitle>Dish edition</DialogTitle>
              <DialogContent>
                <DialogContentText>
                  edit any or all the fields you want changed
                </DialogContentText>
                <Grid container marginY={3} >
                  <Grid
                    item
                    xs={12}
                    marginBottom={3}
                  >
                  </Grid>
                  <Grid
                    item
                    xs={12}
                    marginBottom={3}
                  ><Field as={TextField}
                    item
                    required
                    id="name"
                    label="Name"
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
                      id="description"
                      label="Description"
                      name="description"
                      helperText={<ErrorMessage name="description" />}
                      error={props.errors.description}
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
                      label="Price"
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
                    {/* <InputLabel id="demo-simple-select-helper-label">Category</InputLabel> */}
                    <Field as={Select}
                      required
                      fullWidth
                      id="categoryId"
                      label="Category"
                      value={props.values.categoryId}
                      name="categoryId"
                      helperText={<ErrorMessage name="category" />}
                      error={props.errors.categoryId}
                    >
                      <MenuItem value={categoryId}>Select one
                      </MenuItem>
                      {categoryList.map((category) => (
                        <MenuItem key={category.id} value={category.id}>
                          {category.name}
                        </MenuItem>
                      ))}
                    </Field>
                  </Grid>
                  <UploadImage props={props}></UploadImage>
                </Grid>
              </DialogContent>
              <DialogActions>
                <Button type="submit" variant="contained" color="success">
                  Confirm
                </Button>
                <Button onClick={handleCancel} variant="contained">
                  Cancel
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