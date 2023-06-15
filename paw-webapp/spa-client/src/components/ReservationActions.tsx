import { Button, Divider, Stack } from "@mui/material";
import { FC, useState } from "react";
import { useNavigate } from "react-router-dom";
import { handleResponse } from "../Utils";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import { ReservationModel } from "../models"
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { paths } from "../constants/constants";

type Props = {
    reservation: ReservationModel | undefined;
    toggleReload: () => void;
}

const ReservationActions: FC<Props> = ({ reservation, toggleReload }) => {


    const reservationService = useReservationService();
    const navigate = useNavigate();

    const seatClient = () => {
        let updateReservation = new ReservationParams();
        updateReservation.securityCode = reservation?.securityCode;
        updateReservation.status = "SEATED";
        handleResponse(
            reservationService.patchReservation(updateReservation),
            (response) => {
                toggleReload()
            }
        )
    }

    const cancelReservation = () => {
        let updateReservation = new ReservationParams();
        updateReservation.securityCode = reservation?.securityCode;
        updateReservation.status = "CANCELED";
        handleResponse(
            reservationService.patchReservation(updateReservation),
            (response) => {
                toggleReload()
            }
        )
    }

    const endReservation = () => {
        let updateReservation = new ReservationParams();
        updateReservation.securityCode = reservation?.securityCode;
        updateReservation.status = "FINISHED";
        handleResponse(
            reservationService.patchReservation(updateReservation),
            (response) => {
                toggleReload()
            }
        )
    }

    return (
        <>
            {reservation?.status === "OPEN" &&
                <Stack direction="row" spacing={2} justifyContent="space-evenly">
                    <Button variant="outlined" sx={{ width: 10 }} color="success" onClick={seatClient}>SEAT</Button>
                    <Button variant="outlined" sx={{ width: 200 }} color="error" onClick={cancelReservation}>CANCEL RESERVATION</Button>
                </Stack>
            }
            {reservation?.status === "SEATED" &&
                <Stack direction="row" spacing={2} justifyContent="space-evenly">
                    <Button variant="outlined" sx={{ width: 200 }} color="success" onClick={() => navigate(paths.ROOT + "/reservations/" + reservation.securityCode + "/checkOut")}>MAKE CHECK</Button>
                    <Button variant="outlined" sx={{ width: 200 }} color="secondary" onClick={() => navigate(paths.ROOT + "/reservations/" + reservation.securityCode)}>ACCESS RESERVATION</Button>
                </Stack>
            }
            {reservation?.status === "CHECK_ORDERED" &&
                <Stack direction="row" spacing={2} justifyContent="space-evenly">
                    <Button variant="outlined" sx={{ width: 200 }} color="success" onClick={() => navigate(paths.ROOT + "/reservations/" + reservation.securityCode + "/checkOut")}>MAKE CHECK</Button>
                    <Button variant="outlined" sx={{ width: 200 }} color="success" onClick={endReservation}>END RESERVATION</Button>
                </Stack>
            }
        </>
    );
}

export default ReservationActions;