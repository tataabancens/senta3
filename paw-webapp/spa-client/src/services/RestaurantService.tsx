import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../constants/constants";
import { RestaurantModel} from "../models";
import {RestParams} from "../models/Restaurant/RestParams";


export class RestaurantService{
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.RESTAURANTS;

    public getRestaurant(id: number): Promise<AxiosResponse<RestaurantModel>>{
        return this.axios.get<RestaurantModel>(this.basePath + '/'+ id);
    }

    public editRestaurant(params: RestParams): Promise<AxiosResponse>{
        return this.axios.patch(this.basePath + `/${params.restaurantId}`, params.patchRestaurantPayload)
    }
}