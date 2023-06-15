import { useEffect, useState } from "react";
import { DishCategoryModel, DishModel } from "../../../models";
import useDishService from "./useDishService";

export const useDishes = (value: number, category: DishCategoryModel | undefined) => {
    const [dishes, setDishes] = useState<DishModel[]>();
    const [error, setError] = useState<string>();
    const dishService = useDishService();
    const abortController = new AbortController();

    useEffect(() => {
        (async () => {
            if (category) {
                const { isOk, data, error } = await dishService.getDishes(abortController, category.name);
                if (isOk) {
                    data.length > 0 ? setDishes(data) : setDishes([]);
                }
                else setError(error);
            }
        })();
        return () => {
            abortController.abort();
        }
    }, [value, category]);

    return {
        dishes: dishes,
        error,
        loading: !dishes && !error,
    }
}