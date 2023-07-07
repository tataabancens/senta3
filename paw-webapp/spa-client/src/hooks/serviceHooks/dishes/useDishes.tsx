import { useEffect, useState } from "react";
import { DishCategoryModel, DishModel } from "../../../models";
import useDishService from "./useDishService";

export const useDishes = (value: number, category: DishCategoryModel | undefined) => {
    const [dishes, setDishes] = useState<DishModel[] | undefined>(undefined);
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

    const addDish = (dish: DishModel) => {
        setDishes([...dishes!, dish]);
    }

    const updateDish = (dish: DishModel) => {
        const updatedDishes = dishes!.map((d) => {
            if (dish.id === d.id) {
                return dish;
            }
            return d;
        })
        setDishes(updatedDishes);
    }

    const removeDish = (dish: DishModel) => {
        const updatedDishes = dishes!.filter((d) => {
            return dish.id !== d.id;
        })
        setDishes(updatedDishes);
    }

    return {
        dishes: dishes,
        error,
        loading: !dishes && !error,
        setDishes,
        addDish,
        updateDish,
        removeDish
    }
}