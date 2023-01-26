import axios, { AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import { ReservationModel } from "../models";
import {ReservationParams} from "../models/Reservations/ReservationParams";

export class ReservationService{
    private readonly basePath = paths.LOCAL_BASE_URL + paths.RESERVATIONS;

    public getReservation(params: ReservationParams): Promise<AxiosResponse<ReservationModel>>{
        return axios.get<ReservationModel>(this.basePath + '/'+ params.securityCode);
    }

    public getReservations(params: ReservationParams): Promise<AxiosResponse<Array<ReservationModel>>>{
        return axios.get<Array<ReservationModel>>(this.basePath + params.getReservationsQuery);
    }

    public createReservation(params: ReservationParams): Promise<AxiosResponse>{
        return axios.post<ReservationModel>(this.basePath, params.createReservationPayload);
    }

    public patchReservation(params: ReservationParams): Promise<AxiosResponse>{
        return axios.patch(this.basePath + '/' + params.securityCode, params.patchReservationPayload);
    }

    public cancelReservation(securityCode: string): Promise<AxiosResponse>{
        return axios.delete(this.basePath + '/' + securityCode);

    }

}