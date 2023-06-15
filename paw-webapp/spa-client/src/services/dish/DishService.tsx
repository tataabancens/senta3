import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../../constants/constants";
import {DishModel, DishCategoryModel } from "../../models";
import { DishParams } from "../../models/Dishes/DishParams";
import { ResponseDetails } from "../serviceUtils/typings";
import { buildErrorResponse, buildSuccessResponse } from "../serviceUtils/returnTypesFactory";
export class DishService{
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.RESTAURANTS + '/1' + paths.DISHES;
    private readonly categoryPath = paths.RESTAURANTS + '/1' + paths.DISH_CATEGORIES;

    public async getDishes(abortController:AbortController, dishCategory?: string): Promise<ResponseDetails<DishModel[]>> {
        let resp;
        try {
            if (dishCategory) {
                resp = await this.axios.get<DishModel[]>(`${this.basePath}?dishCategory=${dishCategory}`, {signal: abortController.signal});
            } else {
                resp = await this.axios.get<DishModel[]>(`${this.basePath}`, {signal: abortController.signal});
            }
            const data: DishModel[] = resp.data;
            return buildSuccessResponse(data);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async getDishById( dishId: number) : Promise<AxiosResponse<DishModel>>{
        return  this.axios.get<DishModel>(this.basePath + '/' + dishId);
    }

    public async createDish(params: DishParams): Promise<ResponseDetails<Number>>{
        try {
            const response = await this.axios.post(this.basePath, params.createDishPayload);
            return buildSuccessResponse(0);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public editDish(params: DishParams): Promise<AxiosResponse>{
        return this.axios.patch(this.basePath + `/${params.id}`, params.patchDishPayload)
    }

    public deleteDish( dishId: number){
        return this.axios.delete(this.basePath + '/' + dishId);
    }


    ////// Categories:

    public getDishCategories(): Promise<AxiosResponse<Array<DishCategoryModel>>>{
        return this.axios.get<Array<DishCategoryModel>>(this.categoryPath);
    }

    public async getDishCategoriesNew(abortController: AbortController): Promise<ResponseDetails<DishCategoryModel[]>> {
        try {
            const response = await this.axios.get<Array<DishCategoryModel>>(this.categoryPath, { signal: abortController.signal });
            const data: DishCategoryModel[] = response.data;
            return buildSuccessResponse(data);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
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