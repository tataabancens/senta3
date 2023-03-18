import { useEffect, useState } from "react";
import { handleResponse } from "../Utils";
import { DishCategoryModel, DishModel, RestaurantModel } from "../models";
import { Grid, Tab, Tabs } from "@mui/material";
import RestaurantHeader from "../components/RestaurantHeader";
import DishDisplay from "../components/DishDisplay";
import useDishService from "../hooks/serviceHooks/dishes/useDishService";
import useRestaurantService from "../hooks/serviceHooks/useRestaurantService";

function MenuPage() {
  const [value, setValue] = useState(0);
  const [restaurant, setRestaurant] = useState<RestaurantModel>();
  const [dishList, setDishes] = useState<DishModel[]>([]);
  const [categoryMap, setMap] = useState<Map<number,string>>();
  const [categoryList, setCategories] = useState<DishCategoryModel[]>([]);

  const dishService = useDishService();
  const restaurantService = useRestaurantService();

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };


  useEffect(() => {
    handleResponse(
      restaurantService.getRestaurant(1),
      (restaurantData: RestaurantModel) => {
        setRestaurant(restaurantData);
      }
    );

    handleResponse(
      dishService.getDishCategories(),
      (categories: DishCategoryModel[]) => {
        setCategories(categories);
        setValue(categories[0].id);
        let myMap = new Map<number, string>();
        categories.forEach(category => myMap.set(category.id, category.name));
        setMap(myMap);

        handleResponse(
          dishService.getDishes(categories[0].name),
          (dishes: DishModel[]) => {
            dishes.length > 0 ? setDishes(dishes) : setDishes([]);
          }
        );
      }
    );

  }, []);

  useEffect(() => {
    handleResponse(
      dishService.getDishes(categoryMap?.get(value)),
      (dishes: DishModel[]) => {
        dishes.length > 0 ? setDishes(dishes) : setDishes([]);
        
      }
    );
  }, [value]);

  return (
    <Grid container spacing={2} justifyContent="center">
      <RestaurantHeader restaurant={restaurant} role={"ROLE_ANONYMOUS"}/>
      <Grid item xs={11} marginTop={2}>
          <Tabs value={value} onChange={(event,value) => handleChange(event, value)} variant="scrollable" scrollButtons="auto" aria-label="scrollable auto tabs example">
            {categoryList?.map((category: DishCategoryModel) => (<Tab key={category.id} value={category.id} label={category.name} />))}
          </Tabs>
      </Grid>
      <DishDisplay dishList={dishList} role={"ROLE_ANONYMOUS"} actualId={value}/>
    </Grid>
  );
}

export default MenuPage;
