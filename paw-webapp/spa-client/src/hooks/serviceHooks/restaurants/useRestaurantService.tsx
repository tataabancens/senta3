import { RestaurantService } from "../../../services/RestaurantService";
import useAxiosPrivate from "../../useAxiosPrivate";

const useRestaurantService = () => {
    return new RestaurantService(useAxiosPrivate());
}

export default useRestaurantService