import { CustomerService } from "../../../services/customer/CustomerService";
import useAxiosPrivate from "../../useAxiosPrivate";

const useCustomerService = () => {
    return new CustomerService(useAxiosPrivate());
}

export default useCustomerService