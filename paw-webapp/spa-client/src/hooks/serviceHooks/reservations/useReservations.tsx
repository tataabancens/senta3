import { useEffect, useState } from "react";
import { ReservationModel } from "../../../models";
import useReservationService from "./useReservationService";
import { ReservationParams } from "../../../models/Reservations/ReservationParams";
import useServices from "../../useServices";

export const useReservations = (customerId: number, filterStatus: string) => {
    const [reservations, setReservations] = useState<ReservationModel[]>();
    const [error, setError] = useState<string>();
    const [loadingDone, setLoadingDone] = useState<boolean>(false);
    const { reservationService } = useServices();
    const abortController = new AbortController();



    useEffect(() => {
        (async () => {
            const reservationParams = new ReservationParams();
            reservationParams.customerId = customerId;
            reservationParams.filterStatus = filterStatus;

            const { isOk, data, error } = await reservationService.getReservationsNewVersion(reservationParams, abortController);
            if (isOk) {
                data.length > 0 ? setReservations(data) : 
                (() => {
                    setReservations([]);
                    setLoadingDone(true);
                })();
            }
            else setError(error);
        })();
        return () => {
            abortController.abort();
        }
    }, []);

    return {
        reservations: reservations,
        error,
        loading: !error || !loadingDone,
    }
}