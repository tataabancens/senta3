import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../constants/constants";
import { RestaurantModel } from "../models";
import { RestParams } from "../models/Restaurant/RestParams";
import { buildErrorResponse, buildSuccessResponse } from "./serviceUtils/returnTypesFactory";
import { ResponseDetails } from "./serviceUtils/typings";
import { ReservationParams } from "../models/Reservations/ReservationParams";


export class RestaurantService {
    private axios: AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.RESTAURANTS;
    private readonly ACCEPT = { "Accept": "application/vnd.sentate.restaurant.v1+json" };
    private readonly CONTENT_TYPE = { "Content-type": "application/vnd.sentate.restaurant.v1+json" };

    public async getRestaurant(id: number, abortController: AbortController): Promise<ResponseDetails<RestaurantModel>> {
        try {
            const response = await this.axios.get<RestaurantModel>(this.basePath + '/' + id, { signal: abortController.signal, headers: this.ACCEPT });
            const data: RestaurantModel = response.data;
            return buildSuccessResponse(data);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public editRestaurant(params: RestParams): Promise<AxiosResponse> {
        return this.axios.patch(this.basePath + `/${params.restaurantId}`, params.patchRestaurantPayload, { headers: this.CONTENT_TYPE } )
    }

    public async getAvailableHours(params: ReservationParams): Promise<number[]> {
        const response = await this.axios.get(paths.BASE_URL + `/restaurants/${params.restaurantId}/availableHours/${params.date}?qPeople=${params.qPeople}`, { headers: this.ACCEPT })
        return response.data;
    }
}