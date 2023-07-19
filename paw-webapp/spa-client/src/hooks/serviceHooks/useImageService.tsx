import axios from "../../api/axios";
import { ImageService } from "../../services/image/ImageService";
import useAxiosPrivate from "../useAxiosPrivate";

const useImageService = () => {
    return new ImageService(axios);
}

export default useImageService;