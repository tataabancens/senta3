import { useEffect, useState } from "react";
import { ReservationModel } from "../../../models";
import useReservationService from "./useReservationService";
import { ReservationParams } from "../../../models/Reservations/ReservationParams";

export const useRaisedHands = (raisedHand: boolean, value: number) => {
    const [reservations, setReservations] = useState<ReservationModel[] | undefined>(undefined);
    const [error, setError] = useState<string | undefined>(undefined);
    const reservationService = useReservationService();
    const abortController = new AbortController();
    const [reload, setReload] = useState(false);

    const reloadReservations = () => {setReload(!reload)}

    const getReservations = async () => {
        const reservationParams = new ReservationParams();
            reservationParams.filterStatus = value.toString();
            reservationParams.hand = raisedHand;

        const { isOk, data, error } = await reservationService.getReservationsPaginated(reservationParams, abortController);
        if (isOk) {
            const resservationsWithHandUp = data.reservations.filter(r => r.hand === raisedHand);
            data.status !== 204 ? setReservations(resservationsWithHandUp) : setReservations([])
        }
        else{
            setError(error);
        }
    }

    const removeReservation = (reservationToRemove: ReservationModel) => {
        setReservations(prevItems => prevItems!.filter(reservation => reservation !== reservationToRemove));
    }

    useEffect(() => {
        getReservations();

        const intervalCall = setInterval(getReservations, 25000); 
        return () => {
            clearInterval(intervalCall);
            abortController.abort();
        }
    }, [reload]);

    return {
        reservations: reservations,
        error,
        loading: !reservations && !error,
        toggleReload: reloadReservations,
        removeReservation
    }
}