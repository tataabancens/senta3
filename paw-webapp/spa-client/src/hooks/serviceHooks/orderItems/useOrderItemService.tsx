import { OrderItemService } from "../../../services/OrderItemService";
import useAxiosPrivate from "../../useAxiosPrivate";
import axios from "../../../api/axios";
const useOrderItemService = () => {
    return new OrderItemService(axios);
}

export default useOrderItemService