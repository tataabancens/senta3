import { useState, useEffect } from "react"
import { DishCategoryModel, RestaurantModel } from "../../../models"
import useDishService from "./useDishService";

export const useDishCategories = (restaurant: RestaurantModel | undefined) => {
    const [categories, setCategories] = useState<DishCategoryModel[]>();
    const [categoryMap, setMap] = useState<Map<number, DishCategoryModel>>();
    const [error, setError] = useState<string>();
    const abortController = new AbortController();
    const dishService = useDishService();

    useEffect(() => {
        (async () => {
            if (restaurant) {
                const { isOk, data, error } = await dishService.getDishCategoriesNew(abortController);
                if (isOk) {
                    if (data.length > 0) {
                        setCategories(data);
                        let myMap = new Map<number, DishCategoryModel>();
                        data.forEach(category => myMap.set(category.id, category));
                        setMap(myMap);
                    } else {
                        setCategories([]);
                    }

                }
                else setError(error);
            }
        })();
        return () => {
            abortController.abort();
        }
    }, [restaurant]);

    return {
        categoryList: categories,
        categoryMap: categoryMap,
        error,
        loading: !categories && !error && !categoryMap,
    }
}