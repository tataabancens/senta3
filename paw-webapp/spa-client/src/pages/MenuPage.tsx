import { FC, useEffect, useState } from "react";
import { DishCategoryModel } from "../models";
import { CircularProgress, Grid, Tab, Tabs } from "@mui/material";
import RestaurantHeader from "../components/RestaurantHeader";
import DishDisplay from "../components/DishDisplay";
import { useDishes } from "../hooks/serviceHooks/dishes/useDishes";
import { useRestaurant } from "../hooks/serviceHooks/restaurants/useRestaurant";
import { useDishCategories } from "../hooks/serviceHooks/dishes/useDishCategories";
import { UserRoles } from "../models/Enums/UserRoles";

const MenuPage: FC = () => {
  const [value, setValue] = useState<number>(1);

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  const { restaurant } = useRestaurant(1);

  const { categoryList, categoryMap } = useDishCategories(restaurant);

  const { dishes = [] } = useDishes(value, categoryMap?.get(value));

  useEffect(() => {
    if (categoryList && categoryList.length > 0) setValue(categoryList[0].id)
  }, [categoryList]);


  return (
    <Grid container spacing={2} justifyContent="center">
      <RestaurantHeader role={UserRoles.ANONYMOUS} restaurantName={restaurant?.name}/>
      {categoryList && dishes &&
      <Grid item xs={11} marginTop={2}>
        <Tabs value={value} onChange={(event, value) => handleChange(event, value)} variant="scrollable" scrollButtons="auto" aria-label="scrollable auto tabs example">
          {categoryList.map((category: DishCategoryModel) => (<Tab key={category.id} value={category.id} label={category.name} />))}
        </Tabs>
      </Grid>}
      {categoryList && dishes &&
      <DishDisplay isMenu={false} dishes={dishes} />}
      {(!categoryList || !dishes) &&
        <div style={{position: "absolute", top:"50%", right:"50%"}}><CircularProgress /></div>
      }
    </Grid>
  );
}

export default MenuPage;
