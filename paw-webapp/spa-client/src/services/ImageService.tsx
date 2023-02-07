import { AxiosResponse, AxiosInstance } from "axios";
import { paths } from "../constants/constants";
import { ImageModel } from "../models";

export class ImageService{
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly basePath = paths.IMAGES;

    public getImage(id: number): Promise<AxiosResponse<ImageModel>>{
        return this.axios.get<ImageModel>(this.basePath + '/' + id);
    }
}