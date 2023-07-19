import { useEffect, useState } from "react";
import { DishModel, OrderItemModel, ReservationModel } from "../../../models";
import useDishService from "./useDishService";


export const useRecommendedDish = (reservation: ReservationModel, orderItems: OrderItemModel[], updateReservation: () => void) => {
    const [dish, setDish] = useState<DishModel | undefined>(undefined);
    const [noDish, setNoDish] = useState<Boolean | undefined>();
    const [error, setError] = useState<string>();
    const dishService = useDishService();
    const abortController = new AbortController();

    useEffect(() => {
        (async () =>{
            setNoDish(undefined);
            if(reservation.recommendedDish){
                const dishId = extractDishIdFromPath();
                const {isOk, data, error} = await dishService.getDishByIdNew(dishId);
                if(isOk){
                    setDish(data);
                    updateReservation();
                }else{
                    setError(error)
                }
            }else{
                setNoDish(true);
            }
        })();
        return () => {
            abortController.abort();
        }
    }, []);

    const extractDishIdFromPath = (): number => {
        const parts = reservation.recommendedDish!.split('/');
        const dishId = parts[parts.length - 1];
      
      
        return parseInt(dishId, 10);
    }
    return {
        recomendedDish: dish,
        noDish,
        error,
        loading: !dish && !error && noDish === undefined
    }
}