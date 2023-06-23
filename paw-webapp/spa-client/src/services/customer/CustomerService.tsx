import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../../constants/constants";
import { CustomerModel } from "../../models";
import { CustomerParams } from "../../models/Customers/CustomerParams";
import { ResponseDetails } from "../serviceUtils/typings";
import { buildErrorResponse, buildSuccessResponse } from "../serviceUtils/returnTypesFactory";


export class CustomerService {
    private axios: AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.CUSTOMERS;

    public getCustomerById(id: number): Promise<AxiosResponse<CustomerModel>> {
        return this.axios.get<CustomerModel>(this.basePath + "/" + id);
    }

    public async getCustomerByIdNewVersion(id: number): Promise<ResponseDetails<CustomerModel>> {
        try {
            const response = await this.axios.get<CustomerModel>(this.basePath + "/" + id);
            return buildSuccessResponse(response.data);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public getCustomers(page: number): Promise<AxiosResponse<Array<CustomerModel>>> {
        return this.axios.get<Array<CustomerModel>>(this.basePath + '?page=' + page);
    }

    public createCustomer(params: CustomerParams): Promise<AxiosResponse> {
        return this.axios.post(this.basePath, params.createCustomerPayload);
    }

    public editCustomer(params: CustomerParams): Promise<AxiosResponse<CustomerModel>> {
        return this.axios.patch(this.basePath + `/${params.customerId}`, params.patchCustomerPayload)
    }

    public deleteCustomer(id: number) {
        return this.axios.delete(this.basePath + '/' + id);
    }
}
