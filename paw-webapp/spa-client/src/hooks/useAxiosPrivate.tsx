import { axiosPrivate } from "../api/axios";
import { useEffect } from "react";
import useAuth from "./serviceHooks/authentication/useAuth";
import { AxiosResponse } from "axios";
import { emptyAuth } from "../constants/constants";
import useAuthenticationService from "./serviceHooks/authentication/useAutenticationService";
import { Authentication } from "../context/AuthProvider";

const useAxiosPrivate = () => {
    const { auth, setAuth } = useAuth();
    const authenticationService = useAuthenticationService();

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
                console.log(response);
                if (response.headers['Authorization']) {
                    console.log(response.headers['Authorization']);
                    auth.authorization = response.headers['Authorization'];
                    setAuth(auth);
                }
                return response;
            },
            async (error: any) => {
                
                if (auth?.authorization) {
                        const prevRequest = error?.config;
                        if (!prevRequest?.sent) {
                            // console.log(error);
                        } else {
                            // console.log("Segundo")
                        }
                    if ((error?.response?.status === 403 || error?.response?.status === 401) && !prevRequest?.sent) {
                        prevRequest.sent = true;
                        // TODO: Aca se refresca el token de ser posible
                        // Por el momento vamos a borrar las credenciales del localStorage aca
                        const errorMessage = error.response.data.message as string;
                        if (errorMessage === "The access token expired" ) {
                            if (!prevRequest.refreshTokenSent) {
                                prevRequest.headers['Authorization'] = auth.refreshToken;
                                prevRequest.refreshTokenSent = true;
                                return axiosPrivate(prevRequest);
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