import { TableCell, TableRow } from "@mui/material";
import { FC } from "react";
import { ReservationModel } from "../models";
import ReservationActions from "./ReservationActions";

type Props = {
    reservation: ReservationModel | undefined;
    toggleReload: () => void;
};

const ReservationRow: FC<Props> = ({reservation, toggleReload}): JSX.Element =>{
    return(
        <TableRow key={reservation?.securityCode} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
            <TableCell align="left">{reservation?.securityCode}</TableCell>
            <TableCell align="left">{reservation?.customerName}</TableCell>
            <TableCell align="left">{reservation?.date}</TableCell>
            <TableCell align="left">{reservation?.hour}</TableCell>
            <TableCell align="left">{reservation?.tableNumber}</TableCell>
            <TableCell align="left">{reservation?.peopleAmount}</TableCell>
            <TableCell align="center"><ReservationActions reservation={reservation} toggleReload={toggleReload} /></TableCell>
        </TableRow> 
    );
}

export default ReservationRow;