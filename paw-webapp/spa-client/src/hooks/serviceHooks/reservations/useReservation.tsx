import { useState, useEffect } from "react";
import { ReservationModel } from "../../../models";
import { ReservationParams } from "../../../models/Reservations/ReservationParams";
import useReservationService from "./useReservationService";
import useServiceProvider from "../../../context/ServiceProvider";

export const useReservation = (securityCode: string, interval?: number) => {
    const { reservationService } = useServiceProvider();
    const abortController = new AbortController();
    const [reservation, setReservation] = useState<ReservationModel>();
    const [error, setError] = useState<string>();
    const [toggleReloadReservation, setReloadReservation] = useState(false);

    const updateReservation = () => {
        setReloadReservation(!toggleReloadReservation);
    }

    const  getReservation = async () => {
        let resParams = new ReservationParams();
        resParams.securityCode = securityCode;
        const { isOk, data, error } = await reservationService.newGetReservation(resParams, abortController);
        if (isOk) {
            data ? setReservation(data) : setReservation(undefined);
        } else {
            setError(error);
        }

    }
    useEffect(() => {
        getReservation();
        if(interval){
            const intervalCall = setInterval(getReservation, interval); 
            return () => {
                clearInterval(intervalCall);
                abortController.abort();
            }
        }
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