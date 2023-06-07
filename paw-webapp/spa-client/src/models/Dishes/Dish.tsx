import DishModel from "./DishModel";
import ImageModel from "../Images/ImageModel";

export class Dish {
    private _dishModel: DishModel;
    private _imageModel: ImageModel;


    constructor(dishModel: DishModel, imageModel: ImageModel) {
        this._dishModel = dishModel;
        this._imageModel = imageModel;
    }

    get dishModel(): DishModel {
        return this._dishModel;
    }

    set dishModel(value: DishModel) {
        this._dishModel = value;
    }

    get imageModel(): ImageModel {
        return this._imageModel;
    }

    set imageModel(value: ImageModel) {
        this._imageModel = value;
    }
}