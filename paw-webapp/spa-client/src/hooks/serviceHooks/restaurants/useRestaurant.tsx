import { useState, useEffect } from "react";
import useRestaurantService from "./useRestaurantService"
import { RestaurantModel } from "../../../models";

export const useRestaurant = (restaurantId: number) => {
    const restaurantService = useRestaurantService();
    const abortController = new AbortController();
    const [restaurant, setRestaurant] = useState<RestaurantModel>();
    const [error, setError] = useState<string>();

    useEffect(() => {
        (async () => {
            const { isOk, data, error } = await restaurantService.getRestaurant(restaurantId, abortController);
            if (isOk) {
                data ? setRestaurant(data) : setRestaurant(undefined);
            } else {
                setError(error);
            }
        })();
        return () => {
            abortController.abort();
        }
    }, []);
    
    return {
        restaurant: restaurant,
        error,
        loading: !restaurant && !error
    }
}