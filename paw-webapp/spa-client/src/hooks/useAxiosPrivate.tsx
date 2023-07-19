import { axiosPrivate } from "../api/axios";
import { useEffect } from "react";
import useAuth from "./serviceHooks/authentication/useAuth";
import { AxiosResponse } from "axios";
import { emptyAuth, paths } from "../constants/constants";
import useAuthenticationService from "./serviceHooks/authentication/useAutenticationService";
import { useNavigate } from "react-router-dom";

const useAxiosPrivate = () => {
    const { auth, setAuth } = useAuth();
    const authenticationService = useAuthenticationService();
    const navigate = useNavigate();

    useEffect(() => {
        const requestIntercept = axiosPrivate.interceptors.request.use(
            (config: any) => {
                if (!config.headers['Authorization'] && !config.sent) {
                    if (auth?.authorization) {
                        config.headers['Authorization'] = auth.authorization;
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

    return axiosPrivate;
}

export default useAxiosPrivate;