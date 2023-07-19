import { createContext, useState, useEffect } from "react";
import { emptyAuth } from "../constants/constants";
import { CustomerModel, RestaurantModel } from "../models";
import { extractCustomerIdFromContent } from "../pages/SignUpPage";
import useCustomerService from "../hooks/serviceHooks/customers/useCustomerService";
import useRestaurantService from "../hooks/serviceHooks/restaurants/useRestaurantService";
import { UserRoles } from "../models/Enums/UserRoles";
import { axiosPrivate } from "../api/axios";
import { AxiosInstance, AxiosResponse } from "axios";
import useAuthenticationService from "../hooks/serviceHooks/authentication/useAutenticationService";
import { CustomerService } from "../services/customer/CustomerService";
import { useServiceProvider } from "./ServiceProvider";


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
    authUpdated: boolean;
    setAuthUpdated: (authUpdated: boolean) => void;
    axiosPrivate: AxiosInstance;
}

const authContext: AuthContextValue = {
    auth: emptyAuth,
    setAuth: () => null,
    authUpdated: false,
    setAuthUpdated: () => null,
    axiosPrivate: axiosPrivate,
}

const AuthContext = createContext(authContext);

interface Props {
    children: React.ReactNode;
}

export const AuthProvider: React.FC<Props> = ({ children }) => {
    const [auth, setAuth] = useState(getAuthInfo());
    const [axiosReady, setAxiosReady] = useState(false);
    const [authUpdated, setAuthUpdated] = useState<boolean>(false);
    const { restaurantService } = useServiceProvider();
    const { customerService } = useServiceProvider();
    const abortController = new AbortController();
    const authenticationService = useAuthenticationService();

    useEffect(() => {
        const requestIntercept = axiosPrivate.interceptors.request.use(
            (config: any) => {
                if (!config.headers['Authorization'] && !config.sent) {
                    if (auth?.authorization) {
                        config.headers['Authorization'] = auth.authorization;
                        setAxiosReady(true);
                    }
                }
                return config;
            }, (error: any) => Promise.reject(error)
        )

        const responseIntercept = axiosPrivate.interceptors.response.use(
            (response: AxiosResponse) => {
                return response;
            },
            async (error: any) => {
                if (auth?.authorization) {
                    const prevRequest = error?.config;
                    if ((error?.response?.status === 403 || error?.response?.status === 401) && (!prevRequest?.sent || !prevRequest.refreshToken)) {
                        prevRequest.sent = true;
                        // TODO: Aca se refresca el token de ser posible
                        // Por el momento vamos a borrar las credenciales del localStorage aca
                        const errorMessage = error.response.data.message as string;
                        if (errorMessage === "The access token expired") {
                            if (!prevRequest.refreshTokenSent) {
                                const { isOk, data: newAccessToken, error } = await authenticationService.refreshAccessToken(auth.refreshToken!);
                                if (isOk) {
                                    prevRequest.headers['Authorization'] = newAccessToken;
                                    const newAuth = { ...auth, authorization: newAccessToken! }
                                    setAuth(newAuth);
                                    prevRequest.refreshTokenSent = true;
                                    return axiosPrivate(prevRequest);
                                }
                            }
                            setAuth(emptyAuth);
                        }
                        prevRequest.headers['Authorization'] = "";
                        return axiosPrivate(prevRequest);
                    }
                }
                return Promise.reject(error);
            }
        )
        return () => {
            axiosPrivate.interceptors.response.eject(responseIntercept);
            axiosPrivate.interceptors.request.eject(requestIntercept);
        }
    }, [auth])

    useEffect(() => {
        (async () => {
            switch (auth.roles[0]) {
                case UserRoles.CUSTOMER: {
                    const customerId = extractCustomerIdFromContent(auth.contentURL);
                    console.log("Llegue antesgil");
                    
                    const { isOk, data, error } = await customerService.getCustomerByIdNewVersion(customerId);
                    if (isOk) {
                        const newAuth = { ...auth, content: data as CustomerModel };
                        console.log("Estoy cargando datos");
                        setAuth(newAuth);
                        return;
                    }
                }
                    break;
                case UserRoles.RESTAURANT: {
                    const restaurantId = 1;
                    const { isOk, data, error } = await restaurantService.getRestaurant(restaurantId, abortController);
                    if (isOk) {
                        const newAuth = { ...auth, content: data as RestaurantModel };
                        console.log("Estoy cargando datos");
                        setAuth(newAuth);
                        return;
                    }
                }
                    break;
                case(UserRoles.WAITER):
                case(UserRoles.KITCHEN):
                default:
                    break;
            }
        })();
    }, [authUpdated, axiosReady])

    useEffect(() => {
        if (auth === emptyAuth) {
            saveAuthInfo(auth);
            return;
        }
        saveAuthInfo(auth)
    }, [auth])

    return (
        <AuthContext.Provider value={{ auth, setAuth, authUpdated, setAuthUpdated, axiosPrivate }}>
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