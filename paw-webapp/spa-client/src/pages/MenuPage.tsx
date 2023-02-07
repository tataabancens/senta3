import { useEffect, useState } from "react";
import { handleResponse } from "../handleResponse";
import { DishCategoryModel, DishModel, RestaurantModel } from "../models";
import { Grid } from "@mui/material";
import CategoryTabs from "../components/CategoryTabs";
import RestaurantHeader from "../components/RestaurantHeader";
import DishDisplay from "../components/DishDisplay";
import useDishService from "../hooks/serviceHooks/useDishService";
import useRestaurantService from "../hooks/serviceHooks/useRestaurantService";

function MenuPage() {
  const [value, setValue] = useState(0);
  const [restaurant, setRestaurant] = useState<RestaurantModel>();
  const [dishList, setDishes] = useState<DishModel[]>([]);
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
        }
    );
  }, []);

  useEffect(() => {

    handleResponse(
      dishService.getDishes(categoryList[value]? categoryList[value].name : "DRINKS"),
      (dishes: DishModel[]) => {
        dishes.length > 0 ? setDishes(dishes) : setDishes([]);
      }
    );
  }, [value]);

  return (
    <Grid container spacing={2} justifyContent="center">
      <RestaurantHeader restaurant={restaurant} role={"ROLE_ANONYMOUS"}/>
      <CategoryTabs value={value} handleChange={handleChange} categoryList={categoryList}  />
      <DishDisplay dishList={dishList} role={"ROLE_ANONYMOUS"}/>
    </Grid>
  );
}

export default MenuPage;
