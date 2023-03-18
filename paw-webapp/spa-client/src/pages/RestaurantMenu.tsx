import { Button, Grid, Tab, Tabs } from "@mui/material";
import { useEffect, useState, Suspense } from "react";
import ConfirmationMessage from "../components/ConfirmationMessage";
import DishDisplay from "../components/DishDisplay";
import EditCategoryForm from "../components/forms/EditCategoryForm";
import RestaurantHeader from "../components/RestaurantHeader";
import { handleResponse } from "../Utils";
import useDishService from "../hooks/serviceHooks/dishes/useDishService";
import { DishCategoryModel, DishModel } from "../models";
import { useDishes } from "../hooks/serviceHooks/dishes/useDishes";

function RestaurantMenu() {
  const [categoryList, setCategories] = useState<DishCategoryModel[]>([]);
  // const [dishes, setDishes] = useState<DishModel[]>([]);
  const [value, setValue] = useState(0);
  const [categoryMap, setMap] = useState<Map<number, DishCategoryModel>>();
  const [editIsOpen, setIsOpen] = useState(false);
  const [deleteIsOpen, setDeleteIsOpen] = useState(false);
  const [canReload, setReload] = useState(false);
  const dishService = useDishService();

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

  useEffect(() => {
    handleResponse(
      dishService.getDishCategories(),
      (categories: DishCategoryModel[]) => {
        setCategories(categories);
        setValue(categories[0].id);
        let myMap = new Map<number, DishCategoryModel>();
        categories.forEach(category => myMap.set(category.id, category));
        setMap(myMap);
      }
    );
  }, [canReload]);

  const { dishes = [], error, loading } = useDishes(value, categoryMap);

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
        </Grid>
      </Grid>
      <DishDisplay dishList={dishes} role={"ROLE_RESTAURANT"} categoryList={categoryList} actualId={value} />
    </Grid>
  );
}

export default RestaurantMenu;