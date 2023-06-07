import { CustomerModel } from "../../models";

type GetCustomerSuccessResponse = {
    isOk: true;
    data: CustomerModel;
    error: null;
};

type GetCustomerDetailsErrorResponse = {
    isOk: false;
    data: null;
    error: string;
};