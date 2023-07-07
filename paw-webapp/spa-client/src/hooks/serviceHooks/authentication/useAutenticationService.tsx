
import { AuthenticationService } from "../../../services/auth/AuthenticationService";
import useAxiosPrivate from "../../useAxiosPrivate";

const useAuthenticationService = () => {
    return new AuthenticationService();
}

export default useAuthenticationService