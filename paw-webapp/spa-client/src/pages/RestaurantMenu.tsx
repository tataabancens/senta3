import { Button, Grid, Tab, Tabs } from "@mui/material";
import { useEffect, useState, FC } from "react";
import ConfirmationMessage from "../components/ConfirmationMessage";
import DishDisplay from "../components/DishDisplay";
import EditCategoryForm from "../components/forms/EditCategoryForm";
import CreateDishForm from "../components/forms/CreateDishForm";
import RestaurantHeader from "../components/RestaurantHeader";
import { DishCategoryModel } from "../models";
import { useDishes } from "../hooks/serviceHooks/dishes/useDishes";
import { useDishCategories } from "../hooks/serviceHooks/dishes/useDishCategories";
import EditDishForm from "../components/forms/EditDishForm";
import useRestaurantMenuContext from "../hooks/useRestaurantMenuContext";

const RestaurantMenu: FC = () => {
  const [editCategoryIsOpen, setEditCetgoryIsOpen] = useState(false);
  const [deleteIsOpen, setDeleteIsOpen] = useState(false);
  const [canReload, setReload] = useState(false);

  const { useDish: { dish }, useCurrentCategory: { categoryId, setCategoryId },
    useEditDishIsOpen: { editDishIsOpen, setEditDishIsOpen }, 
    getRestaurant, getDishCategories, getDishes } = useRestaurantMenuContext();

  const { restaurant, error: restaurantError, loading: restaurantLoading } = getRestaurant;

  const { categoryList, categoryMap, error: dishCategoriesError, loading: dishCategoriesLoading } = getDishCategories;

  const { dishes = [], error, loading, setDishes } = getDishes;

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setCategoryId(newValue);
  };

  const toggleEditCategoryForm = () => {
    setEditCetgoryIsOpen(!editCategoryIsOpen);
  }

  const toggleEditDishForm = () => {
    setEditDishIsOpen(!editDishIsOpen);
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
      <RestaurantHeader role={"ROLE_RESTAURANT"} toggleReload={toggleReload} />
      <ConfirmationMessage isOpen={deleteIsOpen} handleOpen={toggleDeleteModal} category={categoryMap?.get(categoryId)!} canReload={toggleReload} />
      <Grid item container xs={11} marginTop={2} justifyContent="space-between">
        <Grid item xs={10}>
          <Tabs value={categoryId} onChange={(event, value) => handleChange(event, value)} variant="scrollable" scrollButtons="auto" aria-label="scrollable auto tabs example">
            {categoryList?.map((category: DishCategoryModel) => (<Tab key={category.id} value={category.id} label={category.name} />))}
          </Tabs>
        </Grid>
        <Grid item container xs={2} justifyContent="space-evenly">
          <Grid item>
            <Button onClick={toggleEditCategoryForm} variant="contained" color="success">Edit category</Button>
          </Grid>
          <Grid item>
            <Button onClick={toggleDeleteModal} variant="contained" color="error">Delete category</Button>
          </Grid>
          <Grid item>
            {/* <CreateDishForm categoryList={categoryList || []} dishes={dishes} setDishes={setDishes} /> */}
          </Grid>
          <Grid item>
            {editDishIsOpen && <EditDishForm isOpen={editDishIsOpen} handleOpen={toggleEditDishForm} categoryList={categoryList!} dish={dish!} dishes={dishes} setDishes={setDishes} />}
          </Grid>
        </Grid>
      </Grid>
      <DishDisplay isMenu={true} dishes={dishes}/>
    </Grid>
  );
}

export default RestaurantMenu;