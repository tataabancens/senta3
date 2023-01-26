import { AxiosResponse } from "axios";
import {Dish} from "./models/Dishes/Dish";

export function handleResponse<T>(
    response: Promise<AxiosResponse<T>>,
    setterFunction: (data: T) => void
): void{
        response.then((response: AxiosResponse<T>) => {
            setterFunction(response.data);
        }).catch((error) =>{
            console.log(`ERROR: ${error}`);
        });

}

export function handleDishImageing(dish: Promise<Dish>): void{
    console.log("AAAAAAAAAAAA")
    let toRet: Dish;
    dish.then((dish:Dish)=> {
        console.log(dish);
        toRet = dish;
    })
    // return toRet;
}

export function testAPI<T>(response: Promise<AxiosResponse<T>>): void{
    response.then((response: AxiosResponse<T>) => {
        console.log("response:");
        console.log(response.status);
        console.log(response.data);
        console.log("-----");
    });
}