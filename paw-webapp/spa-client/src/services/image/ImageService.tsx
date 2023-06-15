import { AxiosResponse, AxiosInstance } from "axios";
import { ImageModel } from "../../models";
import { paths } from "../../constants/constants";
import { ResponseDetails } from "../serviceUtils/typings";

export class ImageService {
    private axios: AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    public getImage(imageId: number): Promise<AxiosResponse<ImageModel>> {
        return this.axios.get<ImageModel>(`${paths.IMAGES}/${imageId}`);
    }

    public async createImage(formData: FormData): Promise<ResponseDetails<number>> {
        try {
            const response = await this.axios.post(paths.IMAGES, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                }
            })
            const imageLocation = response.headers['location'] as string;
            const imageIdString = imageLocation.split("/resources/images/")[1];
            const imageId = parseInt(imageIdString)
            return {
                isOk: true,
                data: imageId,
                error: null,
            };
        } catch (e) {
            return {
                isOk: false,
                data: null,
                error: (e as Error).message
            };
        }
    }
}