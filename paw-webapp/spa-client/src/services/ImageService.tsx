import { AxiosResponse, AxiosInstance } from "axios";
import { ImageModel } from "../models";
import {paths} from "../constants/constants";

export class ImageService{
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    public getImage(imageId: number): Promise<AxiosResponse<ImageModel>>{
        return this.axios.get<ImageModel>(`${paths.IMAGES}/${imageId}`);
    }
}