import { Button, CircularProgress, Grid, Tab, Tabs } from "@mui/material";
import { useState, FC } from "react";
import ConfirmationMessage from "../components/ConfirmationMessage";
import DishDisplay from "../components/DishDisplay";
import EditCategoryForm from "../components/forms/EditCategoryForm";
import RestaurantHeader from "../components/RestaurantHeader";
import { DishCategoryModel } from "../models";
import EditDishForm from "../components/forms/EditDishForm";
import useRestaurantMenuContext from "../hooks/useRestaurantMenuContext";
import { useTranslation } from "react-i18next";
import { UserRoles } from "../models/Enums/UserRoles";

const RestaurantMenu: FC = () => {
  const [editCategoryIsOpen, setEditCetgoryIsOpen] = useState(false);
  const [deleteIsOpen, setDeleteIsOpen] = useState(false);
  const [canReload, setReload] = useState(false);
  const { t } = useTranslation();

  const { useDish: { dish }, useCurrentCategory: { categoryId, setCategoryId },
    useEditDishIsOpen: { editDishIsOpen }, getDishCategories, getDishes } = useRestaurantMenuContext();

  const { categoryList, categoryMap } = getDishCategories;

  const { dishes = [], setDishes } = getDishes;

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setCategoryId(newValue);
  };

  const toggleEditCategoryForm = () => {
    setEditCetgoryIsOpen(!editCategoryIsOpen);
  }

  const toggleDeleteModal = () => {
    setDeleteIsOpen(!deleteIsOpen);
  }

  const toggleReload = () => {
    setReload(!canReload)
  }


  return (
    <Grid container spacing={2} justifyContent="center">
        <EditCategoryForm isOpen={editCategoryIsOpen} handleOpen={toggleEditCategoryForm} />
        <RestaurantHeader role={UserRoles.RESTAURANT} toggleReload={toggleReload} />
        <ConfirmationMessage isOpen={deleteIsOpen} handleOpen={toggleDeleteModal} category={categoryMap?.get(categoryId)!} dishes={dishes.length} />
        {categoryList &&
        <Grid item container xs={11} marginTop={2} justifyContent="space-between">
          <Grid item xs={9}>
            <Tabs value={categoryId} onChange={(event, value) => handleChange(event, value)} variant="scrollable" scrollButtons="auto" aria-label="scrollable auto tabs example">
              {categoryList.map((category: DishCategoryModel) => (<Tab key={category.id} value={category.id} label={category.name} />))}
            </Tabs>
          </Grid>
          <Grid item container xs={3} justifyContent="right">
            <Grid item>
              <Button onClick={toggleEditCategoryForm} variant="contained" color="success">{t('restaurantMenu.editCategory')}</Button>
            </Grid>
            <Grid item marginLeft={2}>
              <Button onClick={toggleDeleteModal} variant="contained" color="error">{t('restaurantMenu.deleteCategory')}</Button>
            </Grid>
            <Grid item>
              {editDishIsOpen && <EditDishForm categoryList={categoryList!} dish={dish!} setDishes={setDishes} />}
            </Grid>
          </Grid>
        </Grid>}
        {categoryList && dishes &&
        <DishDisplay isMenu={true} dishes={dishes}/>}
        {(!categoryList || !dishes) && <div style={{position: "absolute", top:"50%", right:"50%"}}><CircularProgress/></div>}
    </Grid>
  );
}

export default RestaurantMenu;