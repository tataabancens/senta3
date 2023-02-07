import { AxiosInstance, AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import { ReservationModel } from "../models";
import {ReservationParams} from "../models/Reservations/ReservationParams";

export class ReservationService {
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    public getReservation(params: ReservationParams): Promise<AxiosResponse<ReservationModel>>{
        return this.axios.get<ReservationModel>(paths.RESERVATIONS + '/'+ params.securityCode);
    }

    public getReservations(params: ReservationParams): Promise<AxiosResponse<Array<ReservationModel>>>{
        return this.axios.get<Array<ReservationModel>>(paths.RESERVATIONS + params.getReservationsQuery);
    }

    public createReservation(params: ReservationParams): Promise<AxiosResponse>{
        return this.axios.post<ReservationModel>(paths.RESERVATIONS, params.createReservationPayload);
    }

    public patchReservation(params: ReservationParams): Promise<AxiosResponse>{
        return this.axios.patch(paths.RESERVATIONS + '/' + params.securityCode, params.patchReservationPayload);
    }

    public cancelReservation(securityCode: string): Promise<AxiosResponse>{
        return this.axios.delete(paths.RESERVATIONS + '/' + securityCode);
    }

    public async getAvailableHours(params: ReservationParams): Promise<number[]> {
        const response = await this.axios.get(paths.LOCAL_BASE_URL + `/restaurants/${params.restaurantId}/availableHours/${params.date}?qPeople=${params.qPeople}`)
        return response.data;
    }
}