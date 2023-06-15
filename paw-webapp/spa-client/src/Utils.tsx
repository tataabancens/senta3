import { AxiosError, AxiosInstance, AxiosResponse } from "axios";
import { NavigateFunction } from "react-router-dom";
import ApiErrorDetails, { ApiError } from "./models/ApiError/ApiErrorDetails";
import { FormikHelpers } from "formik";
import { fromByteArray } from "base64-js";
import { paths } from "./constants/constants";

export function handleResponse<T>(
    response: Promise<AxiosResponse<T>>,
    setterFunction: (data: T) => void,
    navigate?: NavigateFunction,
    location?: any
): void {
    response.then((response: AxiosResponse<T>) => {
        setterFunction(response.data);
    }).catch((error) => {
        console.log("error:")
        console.log(error)
        if (navigate && location)
            navigate(`${paths.ROOT}/login`, { state: { from: location }, replace: true });
    });
}

interface ExtendedResponse {
    response: AxiosResponse;
    error: AxiosError;
    ok: boolean;
}

export function awaitWrapper<T>(promise: any): ExtendedResponse {
    return promise
        .then((response: AxiosResponse<T>) => ({ ok: true, response }))
        .catch((error: AxiosError<ApiErrorDetails>) => Promise.resolve({ ok: false, error }))
}

export function handleFormResponse<T>(
    response: Promise<AxiosResponse<T>>,
    setterFunction: (data: T) => void,
    errorCallback: (error: ApiError[]) => void
): void {
    response.then((response: AxiosResponse<T>) => {
        setterFunction(response.data);
    }).catch((error) => {
        // console.log(error.response.data.errors);
        if (error.response?.data?.errors)
            errorCallback(error.response?.data?.errors);
    });
}

export async function tryLogin<T>(axios: AxiosInstance,
    username: string,
    password: string,
    props: FormikHelpers<T>,
    path: string,
    setAuth: (newAuth: any) => void,
    errorHandler: (err: any, props: FormikHelpers<T>) => void) {
        const encoder = new TextEncoder();
        const base64Credentials = fromByteArray(encoder.encode(`${username}:${password}`));

        try {
            const response = await axios.get(path,
                {
                    headers: {
                        'Authorization': `Basic ${base64Credentials}`
                    }
                }
            );

            console.log(response);

            const authorization: string | undefined = response?.headers.authorization;
            const role = response?.data?.role;
            const roles: string[] = [role];
            const id = response?.data?.id;
            console.log("logged in username: " + username);

            setAuth({ username, roles, authorization, id });
            return true;
        } catch (err: any) {
            errorHandler(err, props);
            return false;
        }
}

export function loginErrorHandler<T>(err: any, props: FormikHelpers<T>) {
    if (!err?.response) {
        props.setFieldError("username", "No server response");
    } else if (err.response?.status === 400) {
        props.setFieldError("username", "Missing username or password");
    } else if (err.response?.status === 401) {
        props.setFieldError("username", "Invalid username/password. Please try again.");
        props.setFieldError("password", "Invalid username/password. Please try again.");
    } else {
        props.setFieldError("username", "Login failed");
    }
}