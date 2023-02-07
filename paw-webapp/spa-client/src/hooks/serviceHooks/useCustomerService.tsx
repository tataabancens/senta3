import { CustomerService } from "../../services/CustomerService";
import useAxiosPrivate from "../useAxiosPrivate";

const useCustomerService = () => {
    return new CustomerService(useAxiosPrivate());
}

export default useCustomerService