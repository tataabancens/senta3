import { AxiosHeaders, AxiosInstance, AxiosResponse, AxiosResponseHeaders } from "axios";
import { paths } from "../constants/constants";
import { DishModel, OrderItemModel, ReservationModel } from "../models";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { ResponseDetails } from "./serviceUtils/typings";
import { buildErrorResponse, buildSuccessResponse } from "./serviceUtils/returnTypesFactory";
import ReservationsPage, { parseLinkHeader } from "../models/Reservations/ReservationsPage";

export class ReservationService {
    private axios: AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly ACCEPT = { "Accept": "application/vnd.sentate.reservation.v1+json" };
    private readonly CONTENT_TYPE = { "Content-type": "application/vnd.sentate.reservation.v1+json" };

    public getReservation(params: ReservationParams): Promise<AxiosResponse<ReservationModel>> {
        return this.axios.get<ReservationModel>(paths.RESERVATIONS + '/' + params.securityCode, { headers: this.ACCEPT });
    }

    public async newGetReservation(params: ReservationParams, abortController: AbortController): Promise<ResponseDetails<ReservationModel>> {
        try {
            const response = await this.axios.get<ReservationModel>(paths.RESERVATIONS + '/' + params.securityCode, { signal: abortController.signal, headers: this.ACCEPT });
            const data: ReservationModel = response.data;
            return buildSuccessResponse(data);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public getReservations(params: ReservationParams): Promise<AxiosResponse<Array<ReservationModel>>> {
        return this.axios.get<Array<ReservationModel>>(paths.RESERVATIONS + params.getReservationsQuery, { headers: this.ACCEPT });
    }

    public async getReservationsNewVersion(params: ReservationParams, abortController: AbortController): Promise<ResponseDetails<ReservationModel[]>> {
        try {
            const response = await this.axios.get<Array<ReservationModel>>(paths.RESERVATIONS + params.getReservationsQuery, { signal: abortController.signal, headers: this.ACCEPT });;
            return buildSuccessResponse(response.data as ReservationModel[]);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    
    }

    public async getReservationsPaginated(params: ReservationParams, abortController: AbortController): Promise<ResponseDetails<ReservationsPage>>{
        try {
            const response = await this.axios.get(paths.RESERVATIONS + params.getReservationsQuery, {signal: abortController.signal, headers: this.ACCEPT});
            const {prev, first, last, next} = parseLinkHeader(response.headers['link']);
            const reservations = response.data as ReservationModel[];
            const reservationsPage = { reservations, prev, next, first, last, page: params.page!, status: response.status}
            return buildSuccessResponse(reservationsPage);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }


    public createReservation(params: ReservationParams): Promise<AxiosResponse> {
        return this.axios.post(paths.RESERVATIONS, params.createReservationPayload, { headers: { customerId: params.customerId, "Content-Type": "application/vnd.sentate.reservation.v1+json" } });
    }

    public async patchReservation(params: ReservationParams): Promise<ResponseDetails<number>> {
        try{
            await this.axios.patch(paths.RESERVATIONS + '/' + params.securityCode, params.patchReservationPayload, { headers: this.CONTENT_TYPE });
            return buildSuccessResponse(1);
        }catch(e) {
            return buildErrorResponse(e as Error);
        }
    }

    public cancelReservation(securityCode: string): Promise<AxiosResponse> {
        return this.axios.delete(paths.RESERVATIONS + '/' + securityCode, { headers: this.ACCEPT });
    }
}