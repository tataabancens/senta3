import { OrderItemService } from "../../services/OrderItemService";
import useAxiosPrivate from "../useAxiosPrivate";

const useOrderItemService = () => {
    return new OrderItemService(useAxiosPrivate());
}

export default useOrderItemService