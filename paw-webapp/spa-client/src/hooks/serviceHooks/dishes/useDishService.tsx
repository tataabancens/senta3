import axios from "../../../api/axios";
import { DishService } from "../../../services/dish/DishService";
import useAxiosPrivate from "../../useAxiosPrivate";

const useDishService = () => {
    return new DishService(axios);
}

export default useDishService