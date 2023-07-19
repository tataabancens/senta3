import { createContext, useState, useEffect } from "react";
import { emptyAuth } from "../constants/constants";
import { CustomerModel, RestaurantModel } from "../models";
import { extractCustomerIdFromContent } from "../pages/SignUpPage";
import useCustomerService from "../hooks/serviceHooks/customers/useCustomerService";
import useRestaurantService from "../hooks/serviceHooks/restaurants/useRestaurantService";
import { UserRoles } from "../models/Enums/UserRoles";
import useServices from "../hooks/useServices";


export interface Authentication {
    user: string;
    id: number;
    authorization: string | undefined;
    refreshToken: string | undefined;
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
    const { customerService, restaurantService } = useServices();
    const abortController = new AbortController();

    useEffect(() => {
        if (auth === emptyAuth) {
            saveAuthInfo(auth);
            return;
        }
        console.log(authUpdated);
        if (authUpdated) {
            console.log("Hola");
            saveAuthInfo(auth);
            setAuthUpdated(false);
            return;
        }
        (async () => {
            switch (auth.roles[0]) {
                case UserRoles.CUSTOMER: {
                    const customerId = extractCustomerIdFromContent(auth.contentURL);
                    const { isOk, data, error } = await customerService.getCustomerByIdNewVersion(customerId);
                    if (isOk) {
                        const newAuth = { ...auth, content: data as CustomerModel };
                        setAuthUpdated(true);
                        setAuth(newAuth);
                    }
                }
                    break;
                case UserRoles.RESTAURANT: {
                    const restaurantId = 1;
                    const { isOk, data, error } = await restaurantService.getRestaurant(restaurantId, abortController);
                    if (isOk) {
                        const newAuth = { ...auth, content: data as RestaurantModel };
                        console.log("Estoy cargando datos");
                        setAuthUpdated(true);
                        setAuth(newAuth);
                    }
                }
                    break;
                case(UserRoles.WAITER):
                case(UserRoles.KITCHEN):
                default:
                    break;
            }
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