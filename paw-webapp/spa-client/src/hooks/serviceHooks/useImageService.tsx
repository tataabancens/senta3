import { ImageService } from "../../services/image/ImageService";
import useAxiosPrivate from "../useAxiosPrivate";

const useImageService = () => {
    return new ImageService(useAxiosPrivate());
}

export default useImageService;