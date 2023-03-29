import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../../constants/constants";
import {DishModel, DishCategoryModel } from "../../models";
import { DishParams } from "../../models/Dishes/DishParams";
import { GetDishDetailsResponse } from "./typings";
export class DishService{
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.RESTAURANTS + '/1' + paths.DISHES;
    private readonly categoryPath = paths.RESTAURANTS + '/1' + paths.DISH_CATEGORIES;

    public getDishes(dishCategory?: string): Promise<AxiosResponse<DishModel[]>> {
        if(dishCategory){
            return this.axios.get<DishModel[]>(`${this.basePath}?dishCategory=${dishCategory}`);
        }
        return this.axios.get<DishModel[]>(`${this.basePath}`);
    }

    public async getDishesNewVersion(abortController:AbortController, dishCategory?: string): Promise<GetDishDetailsResponse> {
        let resp;
        try {
            if (dishCategory) {
                resp = await this.axios.get<DishModel[]>(`${this.basePath}?dishCategory=${dishCategory}`, {signal: abortController.signal});
            } else {
                resp = await this.axios.get<DishModel[]>(`${this.basePath}`, {signal: abortController.signal});
            }
            const data: DishModel[] = resp.data;
            return {
                isOk: true,
                data: data,
                error: null,
            };
        } catch (e) {
            return {
                isOk: false,
                data: null,
                error: (e as Error).message
            }
        }
    }

    public async getDishById( dishId: number) : Promise<AxiosResponse<DishModel>>{
        return  this.axios.get<DishModel>(this.basePath + '/' + dishId);
    }

    public createDish(params: DishParams): Promise<AxiosResponse>{
        return this.axios.post(this.basePath, params.createDishPayload)
    }

    public editDish(params: DishParams): Promise<AxiosResponse>{
        return this.axios.patch(this.basePath + `/${params.id}`, params.patchDishPayload)
    }

    public deleteDish( dishId: number){
        return this.axios.delete(this.basePath + '/' + dishId);
    }


    ////// Categories:

    public getDishCategories(): Promise<AxiosResponse<Array<DishCategoryModel>>>{
        console.log()
        return this.axios.get<Array<DishCategoryModel>>(this.categoryPath);
    }

    public getDishCategory(params: DishParams): Promise<AxiosResponse<DishCategoryModel>>{
        return this.axios.get<DishCategoryModel>(this.categoryPath + `/${params.categoryId}`);
    }

    public createCategory(params: DishParams): Promise<AxiosResponse>{
        return this.axios.post(this.categoryPath, params.createDishCategoryPayload);
    }

    public editCategory(params: DishParams): Promise<AxiosResponse>{
        return this.axios.patch(this.categoryPath + `/${params.categoryId}`, params.patchDishCategoryPayload)
    }

    public deleteCategory(params: DishParams): Promise<AxiosResponse>{
        return this.axios.delete(this.categoryPath + `/${params.categoryId}`)
    }
}