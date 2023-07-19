import axios from "../../../api/axios";
import { ReservationService } from "../../../services/ReservationService";
import useAxiosPrivate from "../../useAxiosPrivate";

const useReservationService = () => {
    return new ReservationService(axios);
}

export default useReservationService