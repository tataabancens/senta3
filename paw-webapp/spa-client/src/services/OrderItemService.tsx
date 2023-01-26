import axios, { AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import { OrderItemModel } from "../models";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";


export class OrderItemService{
    private readonly basePath = paths.LOCAL_BASE_URL + paths.RESERVATIONS

    public getOrderItems(params: OrderitemParams): Promise<AxiosResponse<Array<OrderItemModel>>>{
        return axios.get<Array<OrderItemModel>>(this.basePath + `/${params.securityCode}` + paths.ORDERITEMS);
    }

    public getOrderItem(params: OrderitemParams): Promise<AxiosResponse<OrderItemModel>>{
        return axios.get<OrderItemModel>(this.basePath + `/${params.securityCode}` + paths.ORDERITEMS + `/${params.orderItemId}`);
    }

    public createOrderItem(params: OrderitemParams): Promise<AxiosResponse>{
        console.log(params.createOrderitemPayload);
        return axios.post(this.basePath + `/${params.securityCode}` + paths.ORDERITEMS , params.createOrderitemPayload)
    }

    public editOrderItem(params: OrderitemParams): Promise<AxiosResponse>{
        return axios.patch(this.basePath + `/${params.securityCode}` + paths.ORDERITEMS + `/${params.orderItemId}`, params.patchOrderitemPayload)
    }

}