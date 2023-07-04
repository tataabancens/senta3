import { useEffect, useState } from "react";
import { ReservationModel } from "../../../models";
import useReservationService from "./useReservationService";
import { ReservationParams } from "../../../models/Reservations/ReservationParams";

export const useReservationsPagination = (page: number, value: number, sortDirection: boolean) => {
    const [reservations, setReservations] = useState<ReservationModel[] | undefined>();
    const [error, setError] = useState<string>();
    const [loadingDone, setLoadingDone] = useState<boolean>(false);
    const reservationService = useReservationService();
    const abortController = new AbortController();
    const [reload, setReload] = useState(false);

    const reloadReservations = () => {setReload(!reload)}

    useEffect(() => {
        (async () => {
            const reservationParams = new ReservationParams();
            reservationParams.filterStatus = value.toString();
            reservationParams.page = page;
            sortDirection? reservationParams.direction = "DESC" :  reservationParams.direction = "ASC";   

            const { isOk, data, error } = await reservationService.getReservationsPaginated(reservationParams, abortController);
            if (isOk) {
                data.status !== 204 ? setReservations(data.data) : setReservations([])
                setLoadingDone(true);
                console.log(data.headers)
            }
            else{
                setError(error);
                setLoadingDone(true)
            }
        })();
        return () => {
            abortController.abort();
        }
    }, [value, page, sortDirection, reload]);

    return {
        reservations: reservations,
        error,
        loading: !error && !loadingDone,
        toggleReload: reloadReservations
    }
}