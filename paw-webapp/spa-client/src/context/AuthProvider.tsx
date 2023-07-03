import { createContext, useState, useEffect } from "react";
import { emptyAuth } from "../constants/constants";
import { CustomerModel, RestaurantModel } from "../models";
import { extractCustomerIdFromContent } from "../pages/SignUpPage";
import useCustomerService from "../hooks/serviceHooks/useCustomerService";
import useRestaurantService from "../hooks/serviceHooks/restaurants/useRestaurantService";

export interface Authentication {
    user: string;
    id: number;
    authorization: string | undefined;
    roles: string[];
    content: CustomerModel | RestaurantModel | undefined;
    contentURL: string;
}

export interface AuthContextValue {
    auth: Authentication;
    setAuth: (newAuth: Authentication) => void;
}

const authContext: AuthContextValue = {
    auth: emptyAuth,
    setAuth: () => null,
}

const AuthContext = createContext(authContext);

interface Props {
    children: React.ReactNode;
}

export const AuthProvider: React.FC<Props> = ({ children }) => {
    const [auth, setAuth] = useState(getAuthInfo());
    const [authUpdated, setAuthUpdated] = useState<boolean>(false);
    const customerService = useCustomerService();
    const restaurantService = useRestaurantService();
    const abortController = new AbortController();

    // if (auth !== emptyAuth) {
    //     const token = auth.authorization?.split(" ")[1];
    //     console.log(token);
    //     const decodedToken = parseJwt(token!);
    //     console.log(decodedToken);
    // }

    useEffect(() => {
        if (auth === emptyAuth) {
            saveAuthInfo(auth);
            return;
        }
        if (authUpdated) {
            setAuthUpdated(false);
            return;
        }
        (async () => {
            if (auth.roles[0] === "ROLE_CUSTOMER") {
                const customerId = extractCustomerIdFromContent(auth.contentURL);
                const { isOk, data, error } = await customerService.getCustomerByIdNewVersion(customerId);
                if (isOk) {
                    auth.content = data as CustomerModel;
                    setAuthUpdated(true);
                    setAuth(auth);
                }
            } else if (auth.roles[0] === "ROLE_RESTAURANT") {
                const restaurantId = 1;
                const { isOk, data, error } = await restaurantService.getRestaurant(restaurantId, abortController);
                if (isOk) {
                    const newAuth = { ...auth, content: data as RestaurantModel };
                    setAuthUpdated(true);
                    setAuth(newAuth);
                }
            }
            saveAuthInfo(auth);
        })();
    }, [auth])

    return (
        <AuthContext.Provider value={{ auth, setAuth }}>
            {children}
        </AuthContext.Provider>
    )
}

// Guardar información en localStorage
const saveAuthInfo = (authInfo: Authentication) => {
    localStorage.setItem('authInfo', JSON.stringify(authInfo));
};

// Obtener información de localStorage
const getAuthInfo = (): Authentication => {
    const authInfo = localStorage.getItem('authInfo');
    if (authInfo !== "undefined") {
        return authInfo ? JSON.parse(authInfo) : emptyAuth;
    }
    return emptyAuth;
};

export default AuthContext;