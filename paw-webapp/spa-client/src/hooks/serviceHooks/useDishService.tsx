import { DishService } from "../../services/DishService";
import useAxiosPrivate from "../useAxiosPrivate";
import axios from "../../api/axios";

const useDishService = () => {
    return new DishService(useAxiosPrivate());
}

export default useDishService