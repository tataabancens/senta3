import { ImageModel } from "../../models";

type PostImageSuccessResponse = {
    isOk: true;
    data: number;
    error: null;
};

type PostImageDetailsErrorResponse = {
    isOk: false;
    data: undefined;
    error: string;
};

export type PostImageDetailsResponse =
  | PostImageSuccessResponse
  | PostImageDetailsErrorResponse;