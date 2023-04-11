import { CardContent, Typography, Card, Grid, CardActionArea } from "@mui/material";
import { FC, useState } from "react";

import { DishCategoryModel, ImageModel, ReservationModel } from "../models";
import DishModel from "../models/Dishes/DishModel";
import ConfirmDishForm from "./forms/ConfirmDishForm";
import { useNavigate } from "react-router-dom";
import {paths} from "../constants/constants";
import EditDishForm from "./forms/EditDishForm";
import useImageService from "../hooks/serviceHooks/useImageService";


type Props = {
  dish: DishModel;
  role: string;
  categoryList?: DishCategoryModel[];
  reservation?: ReservationModel;
  categoryId?: number;
  toggleReload?: () => void;
};

const DishCard: FC<Props> = ({
  dish,
  role,
  categoryList,
  reservation,
  categoryId,
  toggleReload
}): JSX.Element => {

  const [isCardOpen, setCardOpen] = useState(false);
  let navigate = useNavigate();
  const imageService = useImageService();
  const [editIsOpen, setEditIsOpen] = useState(false);

  const handleDishForm = () => {
    if(role === "ROLE_ANONYMOUS"){
      navigate(paths.ROOT + "createReservation");
    }else if(role === "ROLE_CUSTOMER"){
      setCardOpen(!isCardOpen);
    }else{
      toggleEditForm();
    }
  };

  const toggleEditForm = () => {
    setEditIsOpen(!editIsOpen)
  }

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
    {role === "ROLE_CUSTOMER" && <ConfirmDishForm isOpen={isCardOpen} handleOpen={handleDishForm} dish={dish!} reservation={reservation} toggleReload={toggleReload} />}
    {role === "ROLE_RESTAURANT" && <EditDishForm isOpen={editIsOpen} handleOpen={toggleEditForm} categoryList={categoryList!} dish={dish} categoryId={categoryId}/>}
      <CardActionArea>
        <img src={dish.image} alt="La panti rosa gato"></img>
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

