import { CardMedia, CardContent, Typography, Card, Grid, CardActionArea, Link } from "@mui/material";
import { FC, useState } from "react";
import DishModel from "../models/Dishes/DishModel";
import ConfirmDishForm from "./forms/ConfirmDishForm";

type Props = {
  dish: DishModel
  role: string
};

const DishCard: FC<Props> = ({
  dish,
  role
}): JSX.Element => {

  const [isCardOpen, setCardOpen] = useState(false);
  
  const handleDishForm = () => {
    if(role !== "ROLE_CUSTOMER"){
      window.location.href = "/createReservation"
    }else{
      setCardOpen(!isCardOpen);
    }
  };

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
    <ConfirmDishForm isOpen={isCardOpen} handleOpen={handleDishForm} dish={dish}/>
    <Card sx={{display: "flex"}}>
      <CardActionArea>
        <CardContent onClick={handleDishForm}>
          <Typography gutterBottom variant="h5" component="div">
              {dish.name}
          </Typography>
          <Typography variant="body2" color="text.secondary">
              {dish.description}
          </Typography>
          <Typography variant="body2" color="text.secondary">
              ${dish.price}
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  </Grid>
  );
}


export default DishCard;
