import { TableCell, TableRow } from "@mui/material";
import { CustomerModel, ReservationModel } from "../models";

function reservationRow(reservation: ReservationModel, customer: CustomerModel | undefined){
    return(
        <TableRow key={reservation.securityCode} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
            <TableCell component="th" scope="row">{reservation.securityCode}</TableCell>
            <TableCell align="right">{customer?.name}</TableCell>
            <TableCell align="right">{reservation.date}</TableCell>
            <TableCell align="right">{reservation.hour}</TableCell>
            <TableCell align="right">{reservation.tableNumber}</TableCell>
            <TableCell align="right">{reservation.peopleAmount}</TableCell>
        </TableRow> 
    );
}

export default reservationRow;