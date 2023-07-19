import { FC } from "react";
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Select from '@mui/material/Select/Select';
import * as Yup from "yup";
import { Formik, Form, Field, FormikHelpers, ErrorMessage } from "formik";
import MenuItem from '@mui/material/MenuItem/MenuItem';
import UploadImage from '../UploadImage';
import useImageService from '../../hooks/serviceHooks/useImageService';
import useDishService from '../../hooks/serviceHooks/dishes/useDishService';
import { DishParams } from '../../models/Dishes/DishParams';
import useRestaurantMenuContext from '../../hooks/useRestaurantMenuContext';
import { useTranslation } from 'react-i18next';
import useServices from "../../hooks/useServices";

export interface createDishFormValue {
    name: string;
    categoryId: number;
    price: number;
    description: string;
    image: string | File;
}

type Props = {
    formIsOpen: boolean;
    handleOpenForm: () => void;
}

const CreateDishForm: FC<Props> = ({ formIsOpen, handleOpenForm }) => {

    const ims = useImageService();
    const { dishService: ds } = useServices();
    const { t } = useTranslation();

    const { useCurrentCategory: { categoryId, setCategoryId }, getDishCategories: { categoryList }, getDishes: { addDish } } = useRestaurantMenuContext();

    const handleSubmit = async (values: createDishFormValue, props: FormikHelpers<createDishFormValue>) => {
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
        const { isOk, data, error } = await ds.createDish(dishParams);

        if (!isOk) {
            // Handle error maybe
            console.log("No okey post");
        }

        const { isOk: isOkGetDish, data: dishData, error: errorGetDish } = await ds.getDishByIdNew(data as number);

        if (!isOkGetDish) {
            // handle error
            console.log("No okey get");
        }

        if (categoryId === values.categoryId) {
            addDish(dishData!);
        } else {
            setCategoryId(values.categoryId);
        }
        

        props.setSubmitting(false);
        handleOpenForm();
    }

    const initialValue: createDishFormValue = {
        name: "",
        categoryId: categoryId!,
        price: 0,
        description: "",
        image: "",
    }

    const validationSchema = Yup.object().shape({
        name: Yup.string().max(65, t('validationSchema.dishNameMaxCharacters',{characters: 65})),
        categoryId: Yup.number(),
        price: Yup.number(),
        description: Yup.string().max(70, t('validationSchema.dishDescriptioneMaxCharacters',{characters: 70})),
        image: Yup.mixed().required(t('validationSchema.required'))
            .test("FILE_TYPE", "Invalid file type", (value) => value && ['image/png', 'image/jpeg'].includes(value.type)),
    })

    return (
        <Dialog open={formIsOpen}>
            <Formik initialValues={initialValue} onSubmit={handleSubmit} validationSchema={validationSchema}>
                {(props) => (
                    <Form>
                        <DialogTitle align="center" >{t('forms.createDish.title')}</DialogTitle>
                        <DialogContent>
                            <UploadImage props={props}></UploadImage>
                            <Field as={TextField}
                                item
                                required
                                variant="standard"
                                fullWidth
                                id="name"
                                label={t('forms.createDish.name')}
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
                                label={t('forms.createDish.price')}
                                name="price"
                                helperText={<ErrorMessage name="price" />}
                                error={props.errors.price}
                            />
                            <Field as={Select}
                                required
                                variant="standard"
                                fullWidth
                                autoComplete="given-name"
                                id="categoryId"
                                label={t('forms.createDish.category')}
                                value={props.values.categoryId.toString()}
                                name="categoryId"
                                helperText={<ErrorMessage name="category" />}
                                error={props.errors.categoryId}
                            >
                                <MenuItem value={-1}>{t('forms.select')}</MenuItem>
                                {categoryList!.map((category) => (
                                    <MenuItem key={category.id} value={category.id}>
                                        {category.name}
                                    </MenuItem>
                                ))}
                            </Field>
                            <Field as={TextField}
                                item
                                required
                                variant="standard"
                                fullWidth
                                multiline
                                rows={3}
                                id="description"
                                label={t('forms.createDish.description')}
                                name="description"
                                helperText={<ErrorMessage name="description" />}
                                error={props.errors.description}
                            />
                        </DialogContent>
                        <DialogActions>
                            <Button
                                type="submit"
                                variant="contained"
                                color="success"
                                disabled={props.isSubmitting}
                            >{t('forms.createDish.createButton')}</Button>
                            <Button onClick={handleOpenForm} variant="contained" color="error">{t('forms.cancelButton')}</Button>
                        </DialogActions>
                    </Form>
                )}
            </Formik>
        </Dialog>
    );
}

export default CreateDishForm;