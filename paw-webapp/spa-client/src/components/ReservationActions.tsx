import { Button, Stack } from "@mui/material";
import { FC } from "react";
import { useNavigate } from "react-router-dom";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import { ReservationModel } from "../models"
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { paths } from "../constants/constants";
import { useTranslation } from "react-i18next";

type Props = {
    reservation: ReservationModel | undefined;
    toggleReload: () => void;
}

const ReservationActions: FC<Props> = ({ reservation, toggleReload }) => {


    const reservationService = useReservationService();
    const navigate = useNavigate();
    const { t } = useTranslation();

    const seatClient = async () => {
        let updateReservation = new ReservationParams();
        updateReservation.securityCode = reservation?.securityCode;
        updateReservation.status = "SEATED";
        const { isOk } = await reservationService.patchReservation(updateReservation);
        if(isOk){
            toggleReload();
        }
    }

    const cancelReservation = async () => {
        let updateReservation = new ReservationParams();
        updateReservation.securityCode = reservation?.securityCode;
        updateReservation.status = "CANCELED";
        const { isOk } = await reservationService.patchReservation(updateReservation);
        if(isOk){
            toggleReload();
        }
    }

    const endReservation = async () => {
        let updateReservation = new ReservationParams();
        updateReservation.securityCode = reservation?.securityCode;
        updateReservation.status = "FINISHED";
        const { isOk } = await reservationService.patchReservation(updateReservation);
        if(isOk){
            toggleReload();
        }
    }

    return (
        <>
            {reservation?.status === "OPEN" &&
                <Stack direction="row" spacing={2} justifyContent="space-evenly">
                    <Button size="small" variant="outlined" sx={{ width: 10 }} color="success" onClick={seatClient}>{t('reservationActions.seat')}</Button>
                    <Button size="small" variant="outlined" sx={{ width: 200 }} color="error" onClick={cancelReservation}>{t('reservationActions.cancel')}</Button>
                </Stack>
            }
            {reservation?.status === "SEATED" &&
                <Stack direction="row" spacing={2} justifyContent="space-evenly">
                    <Button size="small" variant="outlined" sx={{ width: 200 }} color="success" onClick={() => navigate(paths.ROOT + "/reservations/" + reservation.securityCode + "/checkOut")}>{t('reservationActions.makeCheck')}</Button>
                    <Button size="small" variant="outlined" sx={{ width: 200 }} color="secondary" onClick={() => navigate(paths.ROOT + "/reservations/" + reservation.securityCode)}>{t('reservationActions.accessReservation')}</Button>
                </Stack>
            }
            {reservation?.status === "CHECK_ORDERED" &&
                <Stack direction="row" spacing={2} justifyContent="space-evenly">
                    <Button size="small" variant="outlined" sx={{ width: 200 }} color="secondary" onClick={() => navigate(paths.ROOT + "/reservations/" + reservation.securityCode + "/checkOut")}>{t('reservationActions.makeCheck')}</Button>
                    <Button size="small" variant="outlined" sx={{ width: 200 }} color="success" onClick={endReservation}>{t('reservationActions.endReservation')}</Button>
                </Stack>
            }
        </>
    );
}

export default ReservationActions;