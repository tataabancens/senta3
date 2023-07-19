import axios from "../../../api/axios";
import { RestaurantService } from "../../../services/RestaurantService";
import useAxiosPrivate from "../../useAxiosPrivate";

const useRestaurantService = () => {
    return new RestaurantService(axios);
}

export default useRestaurantService