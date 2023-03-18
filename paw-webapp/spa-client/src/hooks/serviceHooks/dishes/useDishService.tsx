import { DishService } from "../../../services/dish/DishService";
import useAxiosPrivate from "../../useAxiosPrivate";

const useDishService = () => {
    return new DishService(useAxiosPrivate());
}

export default useDishService