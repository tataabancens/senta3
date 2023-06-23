import { AxiosInstance, AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import { DishModel, OrderItemModel, ReservationModel } from "../models";
import {ReservationParams} from "../models/Reservations/ReservationParams";
import { ResponseDetails } from "./serviceUtils/typings";
import { buildErrorResponse, buildSuccessResponse } from "./serviceUtils/returnTypesFactory";

export class ReservationService {
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    public getReservation(params: ReservationParams): Promise<AxiosResponse<ReservationModel>>{
        return this.axios.get<ReservationModel>(paths.RESERVATIONS + '/'+ params.securityCode);
    }

    public async newGetReservation(params: ReservationParams, abortController: AbortController): Promise<ResponseDetails<ReservationModel>>{
        try {
            const response = await this.axios.get<ReservationModel>(paths.RESERVATIONS + '/'+ params.securityCode, { signal: abortController.signal });
            const data: ReservationModel = response.data;
            return buildSuccessResponse(data);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public getReservations(params: ReservationParams): Promise<AxiosResponse<Array<ReservationModel>>>{
        return this.axios.get<Array<ReservationModel>>(paths.RESERVATIONS + params.getReservationsQuery);
    }

    public async getReservationsNewVersion(params: ReservationParams, abortController: AbortController): Promise<ResponseDetails<ReservationModel[]>>{
        try {
            const response = await this.axios.get<Array<ReservationModel>>(paths.RESERVATIONS + params.getReservationsQuery);;
            return buildSuccessResponse(response.data as ReservationModel[]);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    
    }
    

    public createReservation(params: ReservationParams): Promise<AxiosResponse>{
        return this.axios.post(paths.RESERVATIONS, params.createReservationPayload, {headers:{customerId: params.customerId}});
    }

    public getOrderItems(params: ReservationParams): Promise<AxiosResponse>{
        return this.axios.get<OrderItemModel[]>(paths.RESERVATIONS + '/orderItems' + params.getOrderItemsQuery);
    }

    public async getOrderItemsNewVersion(params: ReservationParams, abortController: AbortController): Promise<ResponseDetails<OrderItemModel[]>>{
        try {
            const response = await this.axios.get<OrderItemModel[]>(paths.RESERVATIONS + '/orderItems' + params.getOrderItemsQuery, {signal: abortController.signal});
            return buildSuccessResponse(response.data as OrderItemModel[]);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public getRecommendedDish(securityCode: string): Promise<AxiosResponse>{
        return this.axios.get<DishModel>(paths.RESERVATIONS + '/' + securityCode + '/recommendedDish');
    }

    public patchReservation(params: ReservationParams): Promise<AxiosResponse>{
        return this.axios.patch(paths.RESERVATIONS + '/' + params.securityCode, params.patchReservationPayload);
    }

    public cancelReservation(securityCode: string): Promise<AxiosResponse>{
        return this.axios.delete(paths.RESERVATIONS + '/' + securityCode);
    }

    public async getAvailableHours(params: ReservationParams): Promise<number[]> {
        const response = await this.axios.get(paths.BASE_URL + `/restaurants/${params.restaurantId}/availableHours/${params.date}?qPeople=${params.qPeople}`)
        return response.data;
    }
}