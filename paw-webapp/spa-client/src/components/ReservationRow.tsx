import { TableCell, TableRow, Typography, TextField } from "@mui/material";
import { FC, Fragment } from "react";
import { ReservationModel } from "../models";
import ReservationActions from "./ReservationActions";
import * as Yup from "yup";
import { Formik, Form, FormikHelpers, Field, ErrorMessage } from "formik";
import { useTranslation } from "react-i18next";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";

type Props = {
    reservation: ReservationModel | undefined;
    toggleReload: () => void;
};

export interface tableNumberFormValue {
    tableNumber: number;
}

const ReservationRow: FC<Props> = ({ reservation, toggleReload }): JSX.Element => {
    const { t, i18n } = useTranslation();
    const reservationService = useReservationService();

    const formatDate = (date: string) => {
        const dateParts: string[] = date.split("-");
        const year: string = dateParts[0];
        const month: string = dateParts[1];
        const day: string = dateParts[2];
        if(i18n.language === "en"){
            return `${month}/${day}/${year}`;
        }else if(i18n.language === "es"){
            return `${day}/${month}/${year}`;
        }
        return `${month}/${day}/${year}`;
    };

    const initialValue: tableNumberFormValue = {
        tableNumber: 0,
    }

    const validationSchema = Yup.object().shape({
        tableNumber: Yup.number().positive(t('validationSchema.positiveValidation')).required(t('validationSchema.required'))
    });

    const handleSubmit = async (values: tableNumberFormValue, props: FormikHelpers<tableNumberFormValue>) => {
        let updateReservation = new ReservationParams();
        updateReservation.securityCode = reservation?.securityCode;
        updateReservation.table = values.tableNumber;
        updateReservation.status = "SEATED";
        const { isOk, error } = await reservationService.patchReservation(updateReservation);
        if (!isOk) {
            props.setFieldError("tableNumber", t('forms.tableNumber.error'));
            props.setSubmitting(false);
            return;
        }
        toggleReload();
        props.setSubmitting(false);
    };

    return (
        <TableRow key={reservation?.securityCode} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
            <Formik initialValues={initialValue} onSubmit={handleSubmit} validationSchema={validationSchema}>
                {(props) => (
                    <Fragment>
                        <TableCell align="left">{reservation?.securityCode}</TableCell>
                        <TableCell align="left">{reservation?.customerName}</TableCell>
                        <TableCell align="center">{formatDate(reservation!.date)}</TableCell>
                        <TableCell align="center">{t('reservationsPage.tableRow.hour', {hour: reservation?.hour})}</TableCell>
                        {reservation?.status === "OPEN" &&
                            <TableCell align="center"><Field as={TextField}
                                item
                                required
                                id="tableNumber"
                                label={t('forms.tableNumber.label')}
                                name="tableNumber"
                                helperText={<ErrorMessage name="tableNumber" />}
                                error={props.errors.tableNumber}
                            /></TableCell>}
                        {reservation?.status !== "OPEN" && <TableCell align="center">{reservation?.tableNumber}</TableCell>}
                        <TableCell align="center">{reservation?.peopleAmount}</TableCell>
                        <TableCell><ReservationActions reservation={reservation} toggleReload={toggleReload} props={props} isSubmitting={props.isSubmitting}/></TableCell>
                    </Fragment>)}
            </Formik>
        </TableRow>
    );
}

export default ReservationRow;