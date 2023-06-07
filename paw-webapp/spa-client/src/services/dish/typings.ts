import { DishModel } from "../../models";

type GetDishSuccessResponse = {
    isOk: true;
    data: DishModel[];
    error: null;
};

type GetDishDetailsErrorResponse = {
    isOk: false;
    data: null;
    error: string;
};

export type GetDishDetailsResponse =
    | GetDishSuccessResponse
    | GetDishDetailsErrorResponse;



type PostDishSuccessResponse = {
    isOk: true;
    data: Number;
    error: null;
};

type PostDishDetailsErrorResponse = {
    isOk: false;
    data: null;
    error: string;
};

export type PostDishDetailsResponse =
    | PostDishSuccessResponse
    | PostDishDetailsErrorResponse;