import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../constants/constants";
import {DishModel, DishCategoryModel, ImageModel} from "../models";
import { DishParams } from "../models/Dishes/DishParams";
import {Dish} from "../models/Dishes/Dish";

export class DishService{
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.RESTAURANTS + '/1' + paths.DISHES;
    private readonly categoryPath = paths.RESTAURANTS + '/1' + paths.DISH_CATEGORIES;
    private readonly imagePath = paths.IMAGES

    public getDishes(dishCategory?: string): Promise<AxiosResponse<DishModel[]>> {
        return this.axios.get<DishModel[]>(`${this.basePath}?dishCategory=${dishCategory}`);
    }

    public async getDishById( dishId: number) : Promise<Dish>{
        const req: Promise<AxiosResponse<DishModel>> = this.axios.get<DishModel>(this.basePath + '/' + dishId); //.then((response: DishModel) => dish=response.data)
        const dish = await req;

        const reqImage: Promise<AxiosResponse<ImageModel>> = this.axios.get<ImageModel>(this.imagePath + '/' + dish.data.image);
        const resImage = await reqImage;

        return new Dish(dish.data, resImage.data);
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