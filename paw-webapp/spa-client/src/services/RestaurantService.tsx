import axios, { AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import { RestaurantModel} from "../models";
import {RestParams} from "../models/Restaurant/RestParams";


export class RestaurantService{
    private readonly basePath = paths.LOCAL_BASE_URL + paths.RESTAURANTS;

    public getRestaurant(id: number): Promise<AxiosResponse<RestaurantModel>>{
        return axios.get<RestaurantModel>(this.basePath + '/'+ id);
    }

    public editRestaurant(params: RestParams): Promise<AxiosResponse>{
        return axios.patch(this.basePath + `/${params.restaurantId}`, params.patchRestaurantPayload)
    }
}