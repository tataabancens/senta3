import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../constants/constants";
import { OrderItemModel } from "../models";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import { buildErrorResponse, buildSuccessResponse } from "./serviceUtils/returnTypesFactory";
import { ResponseDetails } from "./serviceUtils/typings";


export class OrderItemService {
    private axios: AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.ORDERITEMS

    private readonly ACCEPT = { "Accept": "application/vnd.sentate.order_item.v1+json" };
    private readonly CONTENT_TYPE = { "Content-type": "application/vnd.sentate.order_item.v1+json" };

    public async getOrderItems(params: OrderitemParams, abortController: AbortController): Promise<ResponseDetails<OrderItemModel[]>> {
        try {
            const response = await this.axios.get<OrderItemModel[]>(this.basePath + params.getOrderItemsQuery, { signal: abortController.signal, headers: this.ACCEPT });
            return buildSuccessResponse(response.data as OrderItemModel[]);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async getOrderItem(params: OrderitemParams, abortController: AbortController): Promise<ResponseDetails<OrderItemModel>> {
        try {
            const response = await this.axios.get<OrderItemModel>(this.basePath + `/${params.orderItemId}`, { signal: abortController.signal, headers: this.ACCEPT });
            return buildSuccessResponse(response.data as OrderItemModel);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async createOrderItem(params: OrderitemParams, abortController: AbortController): Promise<ResponseDetails<number>> {
        try {
            const response = await this.axios.post(this.basePath, params.createOrderitemPayload, { headers: { customerId: params.customerId, "Content-Type": "application/vnd.sentate.order_item.v1+json" }, signal: abortController.signal });
            const location = response.headers['location'] as string;

            return buildSuccessResponse(this.extractOrderItemId(location));
        } catch (e) {
            return buildErrorResponse(e as Error);

        }
    }

    public async editOrderItem(params: OrderitemParams, abortController: AbortController): Promise<ResponseDetails<number>> {
        try {
            await this.axios.patch<OrderItemModel>(this.basePath + `/${params.orderItemId}`, params.patchOrderitemPayload, { signal: abortController.signal, headers: this.CONTENT_TYPE });
            return buildSuccessResponse(0);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    private extractOrderItemId(location: string) {
        const parts = location.split("/");
        const orderItemIdString = parts[parts.length - 1];
        return parseInt(orderItemIdString);
    }
}