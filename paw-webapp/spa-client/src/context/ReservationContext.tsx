import { createContext } from "react";
import { ReservationModel } from "../models";


interface ReservationContextProps{
    reservation: ReservationModel | undefined;
    updateReservation: () => void;
}
const emptyReservationContext: ReservationContextProps = {
    reservation: undefined,
    updateReservation: () => null
}

export const ReservationContext = createContext<ReservationContextProps>(emptyReservationContext);