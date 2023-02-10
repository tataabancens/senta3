import { DishService } from "../../services/DishService";
import useAxiosPrivate from "../useAxiosPrivate";

const useDishService = () => {
    return new DishService(useAxiosPrivate());
}

export default useDishService