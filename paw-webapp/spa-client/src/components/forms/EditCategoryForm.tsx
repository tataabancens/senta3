import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField } from "@mui/material";
import { FC, useState } from "react";
import { handleResponse } from "../../Utils";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import { DishCategoryModel } from "../../models";
import { DishParams } from "../../models/Dishes/DishParams";
import useRestaurantMenuContext from "../../hooks/useRestaurantMenuContext";


type Props = {
  isOpen: boolean;
  handleOpen: () => void;
};

const EditCategoryForm: FC<Props> = ({ isOpen, handleOpen }): JSX.Element => {

  const [categoryName, setCategoryName] = useState("");
  const dishService = useDishService();
  const { getDishCategories: { categoryList, categoryMap, setCategories, setCategoryMap }, useCurrentCategory: { categoryId, setCategoryId } } = useRestaurantMenuContext();

  const handleCancel = () => {
    handleOpen();
  }

  const category = categoryMap?.get(categoryId);

  const handleSubmit = async () => {
    if (categoryName.length > 0) {
      let dishParams = new DishParams();
      dishParams.categoryName = categoryName;
      dishParams.categoryId = category?.id;
      const { isOk: editIsOk, error: editError } = await dishService.editCategory(dishParams);

      if (!editIsOk) {
        console.log(editError);
      }
      const editedCategory = {...category, name: categoryName } as DishCategoryModel;
      console.log(editedCategory);

      const updatedCategoryList = categoryList?.map((cat) => {
        if (cat.id === editedCategory!.id) {
          return editedCategory;
        }
        return cat;
      });

      setCategories(updatedCategoryList!);

      const updatedCategoryMap = new Map(categoryMap);
      updatedCategoryMap.set(editedCategory.id, editedCategory);
      setCategoryMap(updatedCategoryMap);

      // setCategoryId(editedCategory.id);
      console.log(categoryId);
    }
    handleOpen();
  }

  return (
    <Dialog open={isOpen} fullWidth>
      <DialogTitle>Edit category</DialogTitle>
      <DialogContent>
        <DialogContentText>
          Enter the new name of the category
        </DialogContentText>
        <Grid container marginY={3}>
          <Grid
            item
            xs={12}
            fullWidth
            marginBottom={3}
            defaultValue={category?.name}
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

export default EditCategoryForm;