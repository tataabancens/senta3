import { CustomerService } from "../../../services/customer/CustomerService";
import useAxiosPrivate from "../../useAxiosPrivate";
import axios from "../../../api/axios";
const useCustomerService = () => {
    return new CustomerService(axios);
}

export default useCustomerService