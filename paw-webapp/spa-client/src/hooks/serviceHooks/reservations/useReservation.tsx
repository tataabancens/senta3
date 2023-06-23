import { useState, useEffect } from "react";
import { ReservationModel } from "../../../models";
import { ReservationParams } from "../../../models/Reservations/ReservationParams";
import useReservationService from "./useReservationService";

export const useReservation = (securityCode: string) => {
    const reservationService= useReservationService();
    const abortController = new AbortController();
    const [reservation, setReservation] = useState<ReservationModel>();
    const [error, setError] = useState<string>();
    const [toggleReloadReservation, setReloadReservation] = useState(false);

    const updateReservation = () => {
        setReloadReservation(!toggleReloadReservation);
    }

    useEffect(() => {
        let resParams = new ReservationParams();
        resParams.securityCode = securityCode;
        (async () => {
            const { isOk, data, error } = await reservationService.newGetReservation(resParams, abortController);
            if (isOk) {
                data ? setReservation(data) : setReservation(undefined);
            } else {
                setError(error);
            }
        })();
        return () => {
            abortController.abort();
        }
    }, [toggleReloadReservation]);
    
    return {
        reservation: reservation,
        error,
        loading: !reservation && !error,
        updateReservation
    }
}