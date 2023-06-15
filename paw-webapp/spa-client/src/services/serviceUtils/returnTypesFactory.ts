import { SuccessResponse, ErrorResponse } from "./typings";

export const buildSuccessResponse = <T>(data: T):SuccessResponse<T> => {
    return {
        isOk: true,
        data: data,
        error: null,
    }
}

export const buildErrorResponse = (e: Error):ErrorResponse => {
    return {
        isOk: false,
        data: null,
        error: e.message
    }
}