import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField, Typography } from "@mui/material";
import { FC, useState } from "react";
import { handleResponse } from "../../Utils";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import { DishParams } from "../../models/Dishes/DishParams";
import useRestaurantMenuContext from "../../hooks/useRestaurantMenuContext";

type Props = {
  isOpen: boolean;
  handleOpen: () => void;
  canReload?: () => void;
};

const CategoryForm: FC<Props> = ({ isOpen, handleOpen, canReload }): JSX.Element => {

  const [categoryName, setCategoryName] = useState("");
  const dishService = useDishService();
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
      <DialogTitle>Create category</DialogTitle>
      <DialogContent>
        <DialogContentText>
          Enter the name of the new category
        </DialogContentText>
        <Grid container marginY={3}>
          <Grid
            item
            xs={12}
            fullWidth
            marginBottom={3}
            component={TextField}
            onChange={(e: any) => setCategoryName(e.target.value)}
            label="Enter category name"
          />
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleSubmit} variant="contained" color="success">
          Confirm
        </Button>
        <Button onClick={handleCancel} variant="contained">
          Cancel
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default CategoryForm;