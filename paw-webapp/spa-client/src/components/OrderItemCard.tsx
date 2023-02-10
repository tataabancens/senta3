import { CardContent, Typography, Card, Grid } from "@mui/material";
import { FC, useEffect, useState } from "react";
import axios from "../api/axios";
import { handleResponse } from "../Utils";
import useDishService from "../hooks/serviceHooks/useDishService";
import { DishModel, ImageModel, OrderItemModel } from "../models";

type Props = {
  orderItem: OrderItemModel;
};

const OrderItemCard: FC<Props> = ({
  orderItem,
}): JSX.Element => {

  const [dish, setDish] = useState<DishModel>();
  const [dishImage, setDishImage] = useState<ImageModel>();

  const dishService = useDishService();

  useEffect(() =>{
    handleResponse(
      dishService.getDishById(1),
      (dish) => setDish(dish)
    );
  })

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
        <Typography gutterBottom variant="h5" component="div">
           {dish?.name}
        </Typography>
        <Typography variant="body2" color="text.secondary">
            mesa
        </Typography>
        <Typography variant="body2" color="text.secondary">
            cantidad
        </Typography>
      </CardContent>
    </Card>
  </Grid>
  );
}


export default OrderItemCard;