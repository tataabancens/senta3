import axios, { AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import { ImageModel } from "../models";

export class ImageService{
    
    private readonly basePath = paths.LOCAL_BASE_URL + paths.IMAGES;

    public getImage(id: number): Promise<AxiosResponse<ImageModel>>{
        return axios.get<ImageModel>(this.basePath + '/' + id);
    }
}