import { useEffect, useState } from "react";
import { ReservationModel } from "../../../models";
import useReservationService from "./useReservationService";
import { ReservationParams } from "../../../models/Reservations/ReservationParams";

export interface ReservationsPaginated {
    reservations: ReservationModel[];
    error: string;
    loading: boolean;
    toggleReload: () => void;
    lastPage: number
}

export const useReservationsPagination = (page: number, value: number, sortDirection: string, orderBy?: string): ReservationsPaginated => {
    const [reservations, setReservations] = useState<ReservationModel[] | undefined>();
    const [error, setError] = useState<string>();
    const [lastPage, setLastPage] = useState<number>(0);
    const [loadingDone, setLoadingDone] = useState<boolean>(false);
    const reservationService = useReservationService();
    const abortController = new AbortController();
    const [reload, setReload] = useState(false);

    const reloadReservations = () => {setReload(!reload)}

    useEffect(() => {
        
    }, [value])

    useEffect(() => {
        (async () => {
            const reservationParams = new ReservationParams();
            reservationParams.filterStatus = value.toString();
            reservationParams.page = page;
            if(orderBy){
                reservationParams.orderBy = orderBy;
            }
            if(sortDirection){
                reservationParams.direction = sortDirection;
            }
            console.log(reservationParams);
            const { isOk, data, error } = await reservationService.getReservationsPaginated(reservationParams, abortController);
            if (isOk) {
                data.status !== 204 ? setReservations(data.reservations) : setReservations([]);
                setLastPage(data.last);
                setLoadingDone(true);
            }
            else{
                setError(error);
                setLoadingDone(true)
            }
        })();
        return () => {
            abortController.abort();
        }
    }, [value, page, sortDirection, reload, orderBy]);

    return {
        reservations: reservations!,
        error: error!,
        loading: !error && !loadingDone,
        toggleReload: reloadReservations,
        lastPage
    }
}