import * as React from 'react';
import { FC } from "react";
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Select from '@mui/material/Select/Select';
import * as Yup from "yup";
import { Formik, Form, Field, FormikHelpers, ErrorMessage } from "formik";
import { DishCategoryModel } from '../../models';
import MenuItem from '@mui/material/MenuItem/MenuItem';
import UploadImage from '../UploadImage';
import useImageService from '../../hooks/serviceHooks/useImageService';
import useDishService from '../../hooks/serviceHooks/dishes/useDishService';
import { DishParams } from '../../models/Dishes/DishParams';

export interface createDishFormValue {
    name: string;
    categoryId: number;
    price: number;
    description: string;
    image: string | File;
}

type Props = {
    categoryList: DishCategoryModel[];
    formIsOpen: boolean;
    handleOpenForm: () => void
}

const CreateDishForm: FC<Props> = ({ categoryList, formIsOpen, handleOpenForm }) => {
    const ims = useImageService();
    const ds = useDishService();


    const handleSubmit = async (values: createDishFormValue, props: FormikHelpers<createDishFormValue>) => {
        console.log(values);

        const formData = new FormData();
        formData.append('image', values.image);
        const imagePostResponse = await ims.createImage(formData);
        const imageId = imagePostResponse.data;
        
        const dishParams = new DishParams();
        dishParams.name = values.name;
        dishParams.price = values.price;
        dishParams.description = values.description;
        dishParams.categoryId = values.categoryId;
        dishParams.imageId = imageId!;
        const dishPostResponse = await ds.createDish(dishParams);

        props.setSubmitting(false);
        handleOpenForm();
    }

    const initialValue: createDishFormValue = {
        name: "Tengo mas de 6 caracteres",
        categoryId: 3,
        price: 0,
        description: "",
        image: "",
    }

    const validationSchema = Yup.object().shape({
        name: Yup.string(),
        categoryId: Yup.number(),
        price: Yup.number(),
        description: Yup.string(),
        image: Yup.mixed().required("Required")
            .test("FILE_TYPE", "Invalid file type", (value) => value && ['image/png', 'image/jpeg'].includes(value.type)),
    })

    return (
        <Dialog open={formIsOpen}>
            <Formik initialValues={initialValue} onSubmit={handleSubmit} validationSchema={validationSchema}>
                {(props) => (
                    <Form>
                        <DialogTitle align="center">Create Dish</DialogTitle>
                        <DialogContent>
                            <Field as={TextField}
                                item
                                required
                                variant="standard"
                                fullWidth
                                id="name"
                                label="Name"
                                name="name"
                                helperText={<ErrorMessage name="name" />}
                                error={props.errors.name}
                            />
                            <Field as={TextField}
                                type="number"
                                item
                                required
                                variant="standard"
                                fullWidth
                                id="price"
                                label="Price"
                                name="price"
                                helperText={<ErrorMessage name="price" />}
                                error={props.errors.price}
                            />
                            <Field as={TextField}
                                item
                                required
                                variant="standard"
                                fullWidth
                                id="description"
                                label="Description"
                                name="description"
                                helperText={<ErrorMessage name="description" />}
                                error={props.errors.description}
                            />
                            <Field as={Select}
                                 required
                                variant="standard"
                                fullWidth
                                autoComplete="given-name"
                                id="categoryId"
                                label="Category"
                                value={props.values.categoryId.toString()}
                                name="categoryId"
                                helperText={<ErrorMessage name="category" />}
                                error={props.errors.categoryId}
                            >
                                <MenuItem value={-1}>Select one
                                </MenuItem>
                                {categoryList.map((category) => (
                                    <MenuItem key={category.id} value={category.id}>
                                        {category.name}
                                    </MenuItem>
                                ))}
                            </Field>
                            <UploadImage props={props}></UploadImage>
                        </DialogContent>
                        <DialogActions>
                            <Button
                                type="submit"
                                variant="contained"
                                color="success"
                                disabled={props.isSubmitting}
                            >Create</Button>
                            <Button onClick={handleOpenForm} variant="contained">Cancel</Button>
                        </DialogActions>
                    </Form>
                )}
            </Formik>
        </Dialog>
    );
}

export default CreateDishForm;