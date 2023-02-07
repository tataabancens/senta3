import { axiosPrivate } from "../api/axios";
import { useEffect } from "react";
import useAuth from "./useAuth";
import { AxiosError, AxiosResponse } from "axios";

const useAxiosPrivate = () => {
    const { auth } = useAuth();

    useEffect(() => {
        const requestIntercept = axiosPrivate.interceptors.request.use(
            (config: any) => {
                if (!config.headers['Authorization']) {
                    if (auth?.authorization) {
                        config.headers['Authorization'] = auth.authorization;
                    }
                }
                return config;
            }, (error: any) => Promise.reject(error)
        )

        const responseIntercept = axiosPrivate.interceptors.response.use(
            (response: AxiosResponse) => response,
            async (error: any) => {
                if (auth?.authorization) {
                        const prevRequest = error?.config;
                    if (error?.response?.status === 403 && !prevRequest?.sent) {
                        prevRequest.sent = true;
                        // TODO: Aca se refresca el token de ser posible
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