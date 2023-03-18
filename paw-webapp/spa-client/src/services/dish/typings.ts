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

  