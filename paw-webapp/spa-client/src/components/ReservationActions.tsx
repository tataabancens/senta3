import { Button, Stack } from "@mui/material";
import { FC } from "react";
import { useNavigate } from "react-router-dom";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import { ReservationModel } from "../models"
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { paths } from "../constants/constants";
import { useTranslation } from "react-i18next";
import { FormikHelpers } from "formik";
import { tableNumberFormValue } from "./ReservationRow";
import useServices from "../hooks/useServices";

type Props = {
    reservation: ReservationModel | undefined;
    toggleReload: () => void;
    props: FormikHelpers<tableNumberFormValue>;
    isSubmitting: boolean;
}

const ReservationActions: FC<Props> = ({ reservation, toggleReload, props, isSubmitting }) => {
    const { reservationService } = useServices();
    const navigate = useNavigate();
    const { t } = useTranslation();

    const cancelReservation = async () => {
        let updateReservation = new ReservationParams();
        updateReservation.securityCode = reservation?.securityCode;
        updateReservation.status = "CANCELED";
        const { isOk } = await reservationService.patchReservation(updateReservation);
        if(isOk){
            toggleReload();
        }
    }

    return (
        <>
            {reservation?.status === "OPEN" &&
                <Stack direction="row" spacing={2} justifyContent="space-evenly">
                    <Button size="small" variant="outlined" sx={{ width: 200 }} color="success" disabled={isSubmitting} onClick={props.submitForm}>{t('reservationActions.seat')}</Button>
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
                </Stack>
            }
        </>
    );
}

export default ReservationActions;