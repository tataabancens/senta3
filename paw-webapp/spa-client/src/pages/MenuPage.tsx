import { useEffect, useState } from "react";
import DishCard from "../components/DishCard";
import { handleResponse, handleDishImageing } from "../handleResponse";
import { DishModel } from "../models";
import { dishService } from "../services";
import {Dish} from "../models/Dishes/Dish";

function MenuPage(){

    handleDishImageing(dishService.getDishById(5));

    let [ dishList, setDishes] = useState(new Array(0));

    useEffect(() => {
        handleResponse(
            dishService.getDishes(),
            (dishes: DishModel[]) => {
                setDishes(dishes)
            }
        );
    },[]);
    return (
        <div>
            {dishList.map((dish: DishModel) => DishCard(dish))}
        </div>
    );
}

export default MenuPage;