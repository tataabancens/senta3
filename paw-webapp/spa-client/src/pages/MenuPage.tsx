import { FC, useEffect, useState } from "react";
import { DishCategoryModel } from "../models";
import { Grid, Tab, Tabs } from "@mui/material";
import RestaurantHeader from "../components/RestaurantHeader";
import DishDisplay from "../components/DishDisplay";
import { useDishes } from "../hooks/serviceHooks/dishes/useDishes";
import { useRestaurant } from "../hooks/serviceHooks/restaurants/useRestaurant";
import { useDishCategories } from "../hooks/serviceHooks/dishes/useDishCategories";

const MenuPage: FC = () => {
  const [value, setValue] = useState(0);

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  const { restaurant, error: restaurantError, loading: restaurantLoading } = useRestaurant(1);

  const { categoryList, categoryMap, error: dishCategoriesError, loading: dishCategoriesLoading } = useDishCategories(restaurant);

  const { dishes = [], error: dishesError, loading: dishesLoading } = useDishes(value, categoryMap?.get(value));

  useEffect(() => {
    if (categoryList && categoryList.length > 0) setValue(categoryList[0].id)
  }, categoryList);


  return (
    <Grid container spacing={2} justifyContent="center">
      <RestaurantHeader restaurant={restaurant} role={"ROLE_ANONYMOUS"} />
      <Grid item xs={11} marginTop={2}>
        <Tabs value={value} onChange={(event, value) => handleChange(event, value)} variant="scrollable" scrollButtons="auto" aria-label="scrollable auto tabs example">
          {categoryList?.map((category: DishCategoryModel) => (<Tab key={category.id} value={category.id} label={category.name} />))}
        </Tabs>
      </Grid>
      <DishDisplay isMenu={false} dishes={dishes} />
    </Grid>
  );
}

export default MenuPage;
