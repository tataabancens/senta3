import { useDishCategories } from "../hooks/serviceHooks/dishes/useDishCategories";
import { useDishes } from "../hooks/serviceHooks/dishes/useDishes";
import { useRestaurant } from "../hooks/serviceHooks/restaurants/useRestaurant";
import { DishCategoryModel, DishModel, RestaurantModel } from "../models";
import { useState, createContext, useEffect } from "react";

export interface RestaurantPageContextValue {
    useDish: useDish;
    useCurrentCategory: useCurrentCategory;
    useEditDishIsOpen: useEditDishIsOpen;
    getRestaurant: getRestaurant;
    getDishCategories: getDishCategories;
    getDishes: getDishes;
}

interface getDishes {
    dishes: DishModel[] | undefined;
    error: string | undefined;
    loading: boolean;
    setDishes: (dishes: DishModel[]) => void;
    addDish: (dish: DishModel) => void;
    updateDish: (dish: DishModel) => void;
    removeDish: (dish: DishModel) => void;
}

interface getDishCategories {
    categoryList: DishCategoryModel[] | undefined;
    categoryMap: Map<number, DishCategoryModel> | undefined;
    error: string | undefined;
    loading: boolean;
    setCategories: (categories: DishCategoryModel[]) => void;
    setCategoryMap: (categoryMap: Map<number, DishCategoryModel>) => void;
}

interface getRestaurant {
    restaurant: RestaurantModel | undefined;
    error: string | undefined;
    loading: boolean;
}

interface useDish {
    dish: DishModel | undefined;
    setDish: (newDish: DishModel) => void;
}

interface useCurrentCategory {
    categoryId: number;
    setCategoryId: (newCategoryId: number) => void;
}

interface useEditDishIsOpen {
    editDishIsOpen: boolean;
    setEditDishIsOpen: (isOpen: boolean) => void;
}

const restaurantPageContext: RestaurantPageContextValue = {
    useDish: { dish: undefined, setDish: () => null },
    useCurrentCategory: { categoryId: 0, setCategoryId: () => null },
    useEditDishIsOpen: { editDishIsOpen: false, setEditDishIsOpen: () => null },
    getRestaurant: { restaurant: undefined, error: undefined, loading: true },
    getDishCategories: { categoryList: undefined, categoryMap: undefined, error: undefined, loading: true, setCategories: () => null, setCategoryMap: () => null },
    getDishes: { dishes: undefined, error: undefined, loading: true, setDishes: () => null, addDish: () => null, updateDish: () => null, removeDish: () => null }
}

const RestaurantPageContext = createContext<RestaurantPageContextValue>(restaurantPageContext);

interface Props {
    children: React.ReactNode;
}

export const RestaurantMenuProvider: React.FC<Props> = ({ children }) => {
    const [dish, setDish] = useState<DishModel>();
    const [categoryId, setCategoryId] = useState<number>(0);
    const [editDishIsOpen, setEditDishIsOpen] = useState<boolean>(false);

    const useDish = { dish, setDish };
    const useCurrentCategory = { categoryId, setCategoryId }
    const useEditDishIsOpen = { editDishIsOpen, setEditDishIsOpen }

    const getRestaurant = useRestaurant(1);
    const { restaurant } = getRestaurant;

    const getDishCategories = useDishCategories(restaurant);
    const { categoryList, categoryMap } = getDishCategories;

    const getDishes = useDishes(categoryId, categoryMap?.get(categoryId))

    useEffect(() => {
        if (categoryList && categoryList.length > 0) {
            if (categoryId === 0) {
                setCategoryId(categoryList[0].id);
            }
        }
    }, categoryList);

    return (
        <RestaurantPageContext.Provider value={{ useDish, useCurrentCategory, useEditDishIsOpen, getRestaurant, getDishCategories, getDishes }}>
            {children}
        </RestaurantPageContext.Provider>
    )
}

export default RestaurantPageContext;