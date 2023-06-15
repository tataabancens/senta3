import { DishModel } from "../../models";

export type SuccessResponse<T> = {
    isOk: true;
    data: T;
    error: null;
};

export type ErrorResponse = {
    isOk: false;
    data: null;
    error: string;
};

export type ResponseDetails<T> =
    | SuccessResponse<T>
    | ErrorResponse;