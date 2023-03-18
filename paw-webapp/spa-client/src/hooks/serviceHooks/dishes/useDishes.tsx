import { useEffect, useState } from "react";
import { DishModel } from "../../../models";
import useDishService from "./useDishService";

export const useDishes = (value:number, categoryMap: any) => {
    const [dishes, setDishes] = useState<DishModel[]>();
    const [error, setError] = useState<string>();
    const dishService = useDishService();

    useEffect(() => {
        (async () => {
          const {isOk, data, error} = await dishService.getDishesNewVersion(categoryMap?.get(value)?.name);
          if (isOk) {
            data.length > 0 ? setDishes(data) : setDishes([]);
          }
          else setError(error);
        })();
      }, [value]);

      return {
        dishes: dishes,
        error,
        loading: !dishes && !error,
      }
}