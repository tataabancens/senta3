import { CardMedia, CardContent, Typography, Card } from "@mui/material";
import DishModel from "../models/Dishes/DishModel";

function DishCard(dish: DishModel) {
  return (
    <Card
      sx={{
        width: 345,
        display: "flex",
        "&hover": {
          width: 360,
          height: 150,
          transition: 0.2,
        },
      }}
    >
      <CardMedia
        sx={{ height: 140 }}
        image="../public/logo512.png"
        title="green iguana"
      />
      <CardContent>
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
    </Card>
  );
}

export default DishCard;
