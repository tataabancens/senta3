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

interface createDishFormValue {
    name: string;
    categoryId: number;
    price: number;
    description: string;
    imageId: number;
}

type Props = {
    categoryList: DishCategoryModel[];
}

const CreateDishForm: FC<Props> = ({ categoryList }) => {
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);

    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleSubmit = (values: createDishFormValue, props: FormikHelpers<createDishFormValue>) => {
        console.log(categoryList);
        props.setSubmitting(false);
        // handleClose();
    }

    const initialValue: createDishFormValue = {
        name: "Tengo mas de 6 caracteres",
        categoryId: 3,
        price: 0,
        description: "",
        imageId: 0,
    }

    const validationSchema = Yup.object().shape({
        name: Yup.string(),
        categoryId: Yup.number(),
        price: Yup.number(),
        description: Yup.string(),
        imageId: Yup.number(),
    })

    return (
        <div>
            <Button variant="contained" onClick={handleClickOpen}>
                Create Dish
            </Button>
            <Dialog open={open}>
                <Formik initialValues={initialValue} onSubmit={handleSubmit} validationSchema={validationSchema}>
                    {(props) => (
                        <Form>
                            <DialogTitle>Create Dish</DialogTitle>
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
                            </DialogContent>
                            <DialogActions>
                                <Button onClick={handleClose}>Cancel</Button>
                                <Button
                                    type="submit"
                                    variant="contained"
                                    color="success"
                                    disabled={props.isSubmitting}
                                >Create</Button>
                            </DialogActions>
                        </Form>
                    )}
                </Formik>
            </Dialog>
        </div>
    );
}

export default CreateDishForm;