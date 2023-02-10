import { AxiosResponse, AxiosInstance } from "axios";
import { ImageModel } from "../models";

export class ImageService{
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    public getImage(path: string): Promise<AxiosResponse<ImageModel>>{
        return this.axios.get<ImageModel>(path);
    }
}