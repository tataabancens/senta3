import { UserService } from "../../services/UserService";
import useAxiosPrivate from "../useAxiosPrivate";

const useUserService = () => {
    return new UserService(useAxiosPrivate());
}

export default useUserService