import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../constants/constants";
import { OrderItemModel } from "../models";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";


export class OrderItemService{
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.RESERVATIONS

    public getOrderItems(params: OrderitemParams): Promise<AxiosResponse<Array<OrderItemModel>>>{
        return this.axios.get<Array<OrderItemModel>>(this.basePath + `/${params.securityCode}` + paths.ORDERITEMS);
    }

    public getOrderItem(params: OrderitemParams): Promise<AxiosResponse<OrderItemModel>>{
        return this.axios.get<OrderItemModel>(this.basePath + `/${params.securityCode}` + paths.ORDERITEMS + `/${params.orderItemId}`);
    }

    public createOrderItem(params: OrderitemParams): Promise<AxiosResponse>{
        return this.axios.post(this.basePath + `/${params.securityCode}` + paths.ORDERITEMS , params.createOrderitemPayload)
    }

    public editOrderItem(params: OrderitemParams): Promise<AxiosResponse>{
        return this.axios.patch(this.basePath + `/${params.securityCode}` + paths.ORDERITEMS + `/${params.orderItemId}`, params.patchOrderitemPayload)
    }

}