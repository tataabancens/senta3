import { Button, Grid, Tab, Tabs } from "@mui/material";
import { useEffect, useState, Suspense } from "react";
import ConfirmationMessage from "../components/ConfirmationMessage";
import DishDisplay from "../components/DishDisplay";
import EditCategoryForm from "../components/forms/EditCategoryForm";
import CreateDishForm from "../components/forms/CreateDishForm";
import RestaurantHeader from "../components/RestaurantHeader";
import { DishCategoryModel } from "../models";
import { useDishes } from "../hooks/serviceHooks/dishes/useDishes";
import { useDishCategories } from "../hooks/serviceHooks/dishes/useDishCategories";
import { useRestaurant } from "../hooks/serviceHooks/restaurants/useRestaurant";

function RestaurantMenu() {
  const [value, setValue] = useState(0);
  const [editIsOpen, setIsOpen] = useState(false);
  const [deleteIsOpen, setDeleteIsOpen] = useState(false);
  const [canReload, setReload] = useState(false);

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  const toggleEditForm = () => {
    setIsOpen(!editIsOpen);
  }

  const toggleDeleteModal = () => {
    setDeleteIsOpen(!deleteIsOpen);
  }

  const toggleReload = () => {
    setReload(!canReload)
  }

  const { restaurant, error: restaurantError, loading: restaurantLoading } = useRestaurant(1);

  const { categoryList, categoryMap, error: dishCategoriesError, loading: dishCategoriesLoading } = useDishCategories(restaurant)

  useEffect(() => {
    if (categoryList && categoryList.length > 0) setValue(categoryList[0].id)
  }, categoryList);

  const { dishes = [], error, loading } = useDishes(value, categoryMap?.get(value));

  return (
    <Grid container spacing={2} justifyContent="center">
      <EditCategoryForm isOpen={editIsOpen} handleOpen={toggleEditForm} category={categoryMap?.get(value)} canReload={toggleReload} />

      <RestaurantHeader role={"ROLE_RESTAURANT"} toggleReload={toggleReload} />
      <ConfirmationMessage isOpen={deleteIsOpen} handleOpen={toggleDeleteModal} data={categoryMap?.get(value)} canReload={toggleReload} />
      <Grid item container xs={11} marginTop={2} justifyContent="space-between">
        <Grid item xs={10}>
          <Tabs value={value} onChange={(event, value) => handleChange(event, value)} variant="scrollable" scrollButtons="auto" aria-label="scrollable auto tabs example">
            {categoryList?.map((category: DishCategoryModel) => (<Tab key={category.id} value={category.id} label={category.name} />))}
          </Tabs>
        </Grid>
        <Grid item container xs={2} justifyContent="space-evenly">
          <Grid item>
            <Button onClick={toggleEditForm} variant="contained" color="success">Edit category</Button>
          </Grid>
          <Grid item>
            <Button onClick={toggleDeleteModal} variant="contained" color="error">Delete category</Button>
          </Grid>
          <Grid item>
            <CreateDishForm categoryList={categoryList || []} />
          </Grid>
        </Grid>
      </Grid>
      <DishDisplay dishList={dishes} role={"ROLE_RESTAURANT"} categoryList={categoryList} actualId={value} />
    </Grid>
  );
}

export default RestaurantMenu;