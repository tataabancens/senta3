import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../constants/constants";
import { RestaurantModel} from "../models";
import {RestParams} from "../models/Restaurant/RestParams";
import { buildErrorResponse, buildSuccessResponse } from "./serviceUtils/returnTypesFactory";
import { ResponseDetails } from "./serviceUtils/typings";


export class RestaurantService{
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.RESTAURANTS;

    public async getRestaurant(id: number, abortController: AbortController): Promise<ResponseDetails<RestaurantModel>>{
        try {
            console.log("Hola");
            const response = await this.axios.get<RestaurantModel>(this.basePath + '/'+ id, { signal: abortController.signal });
            console.log(response.data);
            const data: RestaurantModel = response.data;
            return buildSuccessResponse(data);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public editRestaurant(params: RestParams): Promise<AxiosResponse>{
        return this.axios.patch(this.basePath + `/${params.restaurantId}`, params.patchRestaurantPayload)
    }
}