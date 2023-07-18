import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../../constants/constants";
import { DishModel, DishCategoryModel } from "../../models";
import { DishParams } from "../../models/Dishes/DishParams";
import { ResponseDetails } from "../serviceUtils/typings";
import { buildErrorResponse, buildSuccessResponse } from "../serviceUtils/returnTypesFactory";
export class DishService {
    private axios: AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.RESTAURANTS + '/1' + paths.DISHES;
    private readonly categoryPath = paths.RESTAURANTS + '/1' + paths.DISH_CATEGORIES;

    private readonly ACCEPT = { "Accept": "application/vnd.sentate.dish.v1+json" };
    private readonly CONTENT_TYPE = { "Content-type": "application/vnd.sentate.dish.v1+json" };

    public async getDishes(abortController: AbortController, dishCategory?: string): Promise<ResponseDetails<DishModel[]>> {
        let resp;
        try {
            if (dishCategory) {
                resp = await this.axios.get<DishModel[]>(`${this.basePath}?dishCategory=${dishCategory}`, { signal: abortController.signal, headers: this.ACCEPT });
            } else {
                resp = await this.axios.get<DishModel[]>(`${this.basePath}`, { signal: abortController.signal, headers: this.ACCEPT });
            }
            const data: DishModel[] = resp.data;
            return buildSuccessResponse(data);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async getDishById(dishId: number): Promise<AxiosResponse<DishModel>> {
        return this.axios.get<DishModel>(this.basePath + '/' + dishId, { headers: this.ACCEPT });
    }

    public async getDishByIdNew(dishId: number): Promise<ResponseDetails<DishModel>> {
        try {
            const response = await this.axios.get<DishModel>(this.basePath + '/' + dishId, { headers: this.ACCEPT });
            return buildSuccessResponse(response.data as DishModel);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async createDish(params: DishParams): Promise<ResponseDetails<Number>> {
        try {
            const response = await this.axios.post(this.basePath, params.createDishPayload, { headers: this.CONTENT_TYPE });

            const dishLocation = response.headers['location'] as string;
            const parts = dishLocation.split("/");
            const dishIdString = parts[parts.length - 1];
            const dishId = parseInt(dishIdString);

            return buildSuccessResponse(dishId);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async editDish(params: DishParams): Promise<ResponseDetails<Number>> {
        try {
            await this.axios.patch(this.basePath + `/${params.id}`, params.patchDishPayload, { headers: this.CONTENT_TYPE })
            return buildSuccessResponse(0);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async deleteDish(dishId: number): Promise<ResponseDetails<number>> {
        try {
            await this.axios.delete(this.basePath + '/' + dishId, { headers: this.ACCEPT })
            return buildSuccessResponse(0);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }


    ////// Categories:

    private readonly ACCEPT_CATEGORY = { "Accept": "application/vnd.sentate.dish_category.v1+json" };
    private readonly CONTENT_TYPE_CATEGORY = { "Content-type": "application/vnd.sentate.dish_category.v1+json" };

    public async getDishCategories(abortController: AbortController): Promise<ResponseDetails<DishCategoryModel[]>> {
        try {
            const response = await this.axios.get<Array<DishCategoryModel>>(this.categoryPath, { signal: abortController.signal, headers: this.ACCEPT_CATEGORY });
            const data: DishCategoryModel[] = response.data;
            return buildSuccessResponse(data);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async getDishCategory(params: DishParams): Promise<ResponseDetails<DishCategoryModel>> {
        try {
            const response = await this.axios.get<DishCategoryModel>(this.categoryPath + `/${params.categoryId}`, { headers: this.ACCEPT_CATEGORY });
            return buildSuccessResponse(response.data as DishCategoryModel);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async createCategory(params: DishParams): Promise<ResponseDetails<number>> {
        try {
            const response = await this.axios.post(this.categoryPath, params.createDishCategoryPayload, { headers: this.CONTENT_TYPE_CATEGORY } );

            const categoryLocation = response.headers['location'] as string;
            const parts = categoryLocation.split("/");
            const categoryIdString = parts[parts.length - 1];
            const categoryId = parseInt(categoryIdString);

            return buildSuccessResponse(categoryId);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async editCategory(params: DishParams): Promise<ResponseDetails<number>> {
        try {
            await this.axios.patch(this.categoryPath + `/${params.categoryId}`, params.patchDishCategoryPayload, { headers: this.CONTENT_TYPE_CATEGORY })
            return buildSuccessResponse(0);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }

    public async deleteCategory(params: DishParams): Promise<ResponseDetails<number>> {
        try {
            await this.axios.delete(this.categoryPath + `/${params.categoryId}`, { headers: this.ACCEPT_CATEGORY })
            return buildSuccessResponse(0);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }
}