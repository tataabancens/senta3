import {CardMedia, CardContent, Typography, Card} from "@mui/material";
import {ReservationModel} from "../models";


function ReservationCard(reservation: ReservationModel){
    return (
        <Card sx={  {width: 345
            , display: 'flex'
            , "&hover":{
                width: 360,
                height: 150,
                transition: 0.2
            }}}>
            <CardMedia
                sx={{ height: 140 }}
                image="../public/logo512.png"
                title="green iguana"
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">securityCode: {reservation.securityCode}</Typography>
                <Typography variant="body2" color="text.secondary">hour: {reservation.hour}</Typography>
                <Typography variant="body2" color="text.secondary">date: {reservation.date}</Typography>
                <Typography variant="body2" color="text.secondary">people: {reservation.peopleAmount}</Typography>
                <Typography variant="body2" color="text.secondary">table: {reservation.tableNumber}</Typography>
                <Typography variant="body2" color="text.secondary">hand: {reservation.hand}</Typography>
                <Typography variant="body2" color="text.secondary">orderItems: {reservation.orderItems}</Typography>
                <Typography variant="body2" color="text.secondary">restaurant: {reservation.restaurant}</Typography>
                <Typography variant="body2" color="text.secondary">customer: {reservation.customer}</Typography>
            </CardContent>
        </Card>
    );
}

export default ReservationCard;