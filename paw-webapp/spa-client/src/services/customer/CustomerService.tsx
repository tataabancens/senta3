import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../../constants/constants";
import { CustomerModel } from "../../models";
import { CustomerParams } from "../../models/Customers/CustomerParams";
import { ResponseDetails } from "../serviceUtils/typings";
import { buildErrorResponse, buildSuccessResponse } from "../serviceUtils/returnTypesFactory";
import PointsModel from "../../models/Customers/PointsModel";


export class CustomerService {
    private axios: AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.CUSTOMERS;

    private readonly ACCEPT = { "Accept": "application/vnd.sentate.customer.v1+json" };
    private readonly CONTENT_TYPE = { "Content-type": "application/vnd.sentate.customer.v1+json" };

    public getCustomerById(id: number): Promise<AxiosResponse<CustomerModel>> {
        return this.axios.get<CustomerModel>(this.basePath + "/" + id, { headers: this.ACCEPT });
    }

    public async getCustomerByIdNewVersion(id: number): Promise<ResponseDetails<CustomerModel>> {
        try {
            const response = await this.axios.get<CustomerModel>(this.basePath + "/" + id, { headers: this.ACCEPT });
            return buildSuccessResponse(response.data);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async getPoints(customerId: number, abortController: AbortController): Promise<ResponseDetails<PointsModel>>{
        try{
            const response = await this.axios.get<PointsModel>(this.basePath + "/" + customerId + "/points", {signal: abortController.signal, headers: this.ACCEPT });
            return buildSuccessResponse(response.data);
        }catch(e){
            return buildErrorResponse(e as Error);
        }
    }

    public async patchPoints(customerId: number, points: number): Promise<ResponseDetails<number>>{
        const pointsParams = new CustomerParams();
        pointsParams.points = points;
        const abortController = new AbortController();
        try{
            await this.axios.patch(this.basePath + "/" + customerId + "/points",pointsParams.patchCustomerPayload, {signal: abortController.signal, headers: this.ACCEPT });
            return buildSuccessResponse(0);
        }catch(e){
            return buildErrorResponse(e as Error);
        }
    }

    public getCustomers(page: number): Promise<AxiosResponse<Array<CustomerModel>>> {
        return this.axios.get<Array<CustomerModel>>(this.basePath + '?page=' + page, { headers: this.ACCEPT });
    }

    public createCustomer(params: CustomerParams): Promise<AxiosResponse> {
        return this.axios.post(this.basePath, params.createCustomerPayload, { headers: this.CONTENT_TYPE });
    }

    public async editCustomer(params: CustomerParams): Promise<ResponseDetails<number>> {
        const abortController = new AbortController();
        try{
            await this.axios.patch(this.basePath + `/${params.customerId}`, params.patchCustomerPayload, {signal: abortController.signal, headers: this.CONTENT_TYPE });
            return buildSuccessResponse(0);
        }catch(e){
            return buildErrorResponse(e as Error);
        }
    }

    public deleteCustomer(id: number) {
        return this.axios.delete(this.basePath + '/' + id, { headers: this.ACCEPT });
    }
}
