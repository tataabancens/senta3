import { ReservationService } from "../../services/ReservationService";
import useAxiosPrivate from "../useAxiosPrivate";

const useReservationService = () => {
    return new ReservationService(useAxiosPrivate());
}

export default useReservationService