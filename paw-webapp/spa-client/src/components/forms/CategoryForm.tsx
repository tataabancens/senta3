import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField } from "@mui/material";
import { FC, useState } from "react";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import { DishParams } from "../../models/Dishes/DishParams";
import useRestaurantMenuContext from "../../hooks/useRestaurantMenuContext";
import { useTranslation } from "react-i18next";

type Props = {
  isOpen: boolean;
  handleOpen: () => void;
  canReload?: () => void;
};

const CategoryForm: FC<Props> = ({ isOpen, handleOpen, canReload }): JSX.Element => {

  const [categoryName, setCategoryName] = useState("");
  const dishService = useDishService();
  const { t } = useTranslation();
  const { getDishCategories: { categoryList, categoryMap, setCategories, setCategoryMap }, useCurrentCategory: { setCategoryId } } = useRestaurantMenuContext();

  const handleCancel = () => {
    handleOpen();
  }

  const handleSubmit = async () => {
    if (categoryName.length > 0) {
      let dishParams = new DishParams();
      dishParams.categoryName = categoryName;
      const { isOk, data: categoryId, error } = await dishService.createCategory(dishParams);

      if (!isOk) {
        console.log(error);
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
      <DialogTitle>{t('forms.createCategory.title')}</DialogTitle>
      <DialogContent>
        <DialogContentText>
        {t('forms.createCategory.description')}
        </DialogContentText>
        <Grid container marginY={3}>
          <Grid
            item
            xs={12}
            fullWidth
            marginBottom={3}
            component={TextField}
            onChange={(e: any) => setCategoryName(e.target.value)}
            label={t('forms.createCategory.label')}
          />
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleSubmit} variant="contained" color="success">
        {t('forms.confirmButton')}
        </Button>
        <Button onClick={handleCancel} variant="contained">
        {t('forms.cancelButton')}
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default CategoryForm;