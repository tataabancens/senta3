import axios from "../../../api/axios";
import { UserService } from "../../../services/UserService";
import useAxiosPrivate from "../../useAxiosPrivate";

const useUserService = () => {
    return new UserService(axios);
}

export default useUserService;