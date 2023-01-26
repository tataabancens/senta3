import axios, { AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import {DishModel, DishCategoryModel, ImageModel} from "../models";
import { DishParams } from "../models/Dishes/DishParams";
import {Dish} from "../models/Dishes/Dish";

export class DishService{
    private readonly basePath = paths.LOCAL_BASE_URL + paths.RESTAURANTS + '/1' + paths.DISHES;
    private readonly categoryPath = paths.LOCAL_BASE_URL + paths.RESTAURANTS + '/1' + paths.DISH_CATEGORIES;
    private readonly imagePath = paths.LOCAL_BASE_URL + paths.IMAGES

    public getDishes(dishCategory?: string): Promise<AxiosResponse<DishModel[]>> {
        const url =  new URL(this.basePath);
        if(dishCategory){
            url.searchParams.append("dishCategory", dishCategory)
        }
        return axios.get<DishModel[]>(url.toString());
    }

    public async getDishById( dishId: number) : Promise<Dish>{
        const req: Promise<AxiosResponse<DishModel>> = axios.get<DishModel>(this.basePath + '/' + dishId); //.then((response: DishModel) => dish=response.data)
        const dish = await req;

        const reqImage: Promise<AxiosResponse<ImageModel>> = axios.get<ImageModel>(this.imagePath + '/' + dish.data.image);
        const resImage = await reqImage;

        return new Dish(dish.data, resImage.data);
    }

    public createDish(params: DishParams): Promise<AxiosResponse>{
        return axios.post(this.basePath, params.createDishPayload)
    }

    public editDish(params: DishParams): Promise<AxiosResponse>{
        return axios.patch(this.basePath + `/${params.id}`, params.patchDishPayload)
    }

    public deleteDish( dishId: number){
        return axios.delete(this.basePath + '/' + dishId);
    }


    ////// Categories:

    public getDishCategories(): Promise<AxiosResponse<Array<DishCategoryModel>>>{
        return axios.get<Array<DishCategoryModel>>(this.categoryPath);
    }

    public getDishCategory(params: DishParams): Promise<AxiosResponse<DishCategoryModel>>{
        return axios.get<DishCategoryModel>(this.categoryPath + `/${params.categoryId}`);
    }

    public createCategory(params: DishParams): Promise<AxiosResponse>{
        console.log(params.createDishCategoryPayload);
        return axios.post(this.categoryPath, params.createDishCategoryPayload);
    }

    public editCategory(params: DishParams): Promise<AxiosResponse>{
        return axios.patch(this.categoryPath + `/${params.categoryId}`, params.patchDishCategoryPayload)
    }

    public deleteCategory(params: DishParams): Promise<AxiosResponse>{
        return axios.delete(this.categoryPath + `/${params.categoryId}`)
    }
}