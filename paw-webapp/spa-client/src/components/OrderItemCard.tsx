import { CardContent, Typography, Card, Grid } from "@mui/material";
import { FC, useEffect, useState } from "react";
import axios from "../api/axios";
import { handleResponse } from "../Utils";
import useDishService from "../hooks/serviceHooks/dishes/useDishService";
import { DishModel, ImageModel, OrderItemModel } from "../models";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import { ReservationParams } from "../models/Reservations/ReservationParams";

type Props = {
  orderItem: OrderItemModel;
};

const OrderItemCard: FC<Props> = ({
  orderItem,
}): JSX.Element => {
  return (
    <Grid
    item
    xl={3}
    lg={5}
    md={2}
    sm={12}
    xs={12}
    margin={2}
    maxHeight={180}
  >
    <Card sx={{display: "flex"}}>
        <CardContent>
        <Typography gutterBottom variant="subtitle1">{orderItem.dishName}</Typography>
        <Typography variant="body2" color="text.secondary">mesa: {orderItem.tableNmbr}</Typography>
        <Typography variant="body2" color="text.secondary">cantidad: {orderItem.quantity}</Typography>
      </CardContent>
    </Card>
  </Grid>
  );
}


export default OrderItemCard;