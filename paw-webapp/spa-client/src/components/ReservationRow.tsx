import { TableCell, TableRow, Typography } from "@mui/material";
import { FC } from "react";
import { ReservationModel } from "../models";
import ReservationActions from "./ReservationActions";

type Props = {
    reservation: ReservationModel | undefined;
    toggleReload: () => void;
};

const ReservationRow: FC<Props> = ({reservation, toggleReload}): JSX.Element =>{
    const formatDate = (date: string) => {
        const dateParts: string[] = date.split("-");
        const year: string = dateParts[0];
        const month: string = dateParts[1];
        const day: string = dateParts[2];

        return `${day}/${month}/${year}`;
    };

    return(
        <TableRow key={reservation?.securityCode} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
            <TableCell align="left">{reservation?.securityCode}</TableCell>
            <TableCell align="left">{reservation?.customerName}</TableCell>
            <TableCell align="center">{formatDate(reservation!.date)}</TableCell>
            <TableCell align="center">{reservation?.hour}</TableCell>
            <TableCell align="center">{reservation?.tableNumber}</TableCell>
            <TableCell align="center">{reservation?.peopleAmount}</TableCell>
            <TableCell><ReservationActions reservation={reservation} toggleReload={toggleReload} /></TableCell>
        </TableRow> 
    );
}

export default ReservationRow;