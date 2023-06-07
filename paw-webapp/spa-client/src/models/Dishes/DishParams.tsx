import { DishPayloadModel } from "./DishPayloadModel";
import { DishCategoryPayloadModel } from "./DishCategoryPayloadModel";

export class DishParams {
    private _id: number | undefined;
    private _name: string | undefined;
    private _self: string | undefined;
    private _restaurant: string | undefined;
    private _imageId: number | undefined;
    private _categoryId: number | undefined;
    private _description: string | undefined;
    private _price: number | undefined;
    private _categoryName: string | undefined;

    get createDishPayload(): DishPayloadModel | null {
        if(this._name == undefined || this._description == undefined || this._price == undefined || this._categoryId == undefined){
            return null;
        }
        return{
            "name": this._name,
            "description": this._description,
            "price": this._price,
            "categoryId": this._categoryId,
            "imageId": this._imageId,
        }
    }

    get patchDishPayload(): DishPayloadModel{
        return{
            "name": this._name,
            "description": this._description,
            "price": this._price,
            "categoryId": this._categoryId,
            "imageId": this._imageId,
        }
    }

    get createDishCategoryPayload(): DishCategoryPayloadModel | null{
        if(this._categoryName == undefined){
            return null;
        }
        return{
            "categoryName": this._categoryName,
        }
    }

    get patchDishCategoryPayload(): DishCategoryPayloadModel {
        return {
            "categoryName": this._categoryName,
        }
    }


    get categoryName(): string | undefined {
        return this._categoryName;
    }

    set categoryName(value: string | undefined) {
        this._categoryName = value;
    }

    get id(): number | undefined {
        return this._id;
    }

    set id(value: number | undefined) {
        this._id = value;
    }

    get name(): string | undefined {
        return this._name;
    }

    set name(value: string | undefined) {
        this._name = value;
    }

    get self(): string | undefined {
        return this._self;
    }

    set self(value: string | undefined) {
        this._self = value;
    }

    get restaurant(): string | undefined {
        return this._restaurant;
    }

    set restaurant(value: string | undefined) {
        this._restaurant = value;
    }

    get imageId(): number | undefined {
        return this._imageId;
    }

    set imageId(value: number | undefined) {
        this._imageId = value;
    }

    get categoryId(): number | undefined {
        return this._categoryId;
    }

    set categoryId(value: number | undefined) {
        this._categoryId = value;
    }

    get description(): string | undefined {
        return this._description;
    }

    set description(value: string | undefined) {
        this._description = value;
    }

    get price(): number | undefined {
        return this._price;
    }

    set price(value: number | undefined) {
        this._price = value;
    }
}