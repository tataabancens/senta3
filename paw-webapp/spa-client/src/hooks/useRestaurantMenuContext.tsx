import { useContext, useState } from "react";
import RestaurantPageContext from "../context/RestaurantMenuContext";

const useRestaurantMenuContext = () => {
    return useContext(RestaurantPageContext);
}

export default useRestaurantMenuContext;