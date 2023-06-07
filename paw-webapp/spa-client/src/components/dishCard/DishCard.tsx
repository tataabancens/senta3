import { Typography, Grid } from "@mui/material";
import { FC, useContext, useState} from "react";

import { DishCategoryModel, ReservationModel } from "../../models";
import DishModel from "../../models/Dishes/DishModel";
import Link from '@mui/material/Link';
import ConfirmDishForm from "../forms/ConfirmDishForm";
import { useNavigate } from "react-router-dom";
import {paths} from "../../constants/constants";
import EditDishForm from "../forms/EditDishForm";
import useImageService from "../../hooks/serviceHooks/useImageService";
import useAuth from "../../hooks/useAuth";
import './styles.css';
import { ReservationContext } from "../../context/ReservationContext";


type Props = {
  dish: DishModel;
  categoryList?: DishCategoryModel[];
  categoryId?: number;
  toggleReload?: () => void;
};

const DishCard: FC<Props> = ({
  dish,
  categoryList,
  categoryId,
  toggleReload
}): JSX.Element => {

  const [isCardOpen, setCardOpen] = useState(false);
  let navigate = useNavigate();
  const { auth } = useAuth()
  const imageService = useImageService();
  const reservation = useContext(ReservationContext);
  const [editIsOpen, setEditIsOpen] = useState(false);

  const handleDishForm = () => {
    if(!reservation && auth.roles.length === 0){
      navigate(paths.ROOT + "/createReservation");
    }else if(auth.roles[0] === "ROLE_RESTAURANT"){
      toggleEditForm();
    }else if(reservation || (reservation && auth.roles[0] === "ROLE_RESTAURANT")){
      setCardOpen(!isCardOpen);
    }
  };

  const toggleEditForm = () => {
    setEditIsOpen(!editIsOpen)
  };

  const linkStyle = {
    width: "100%",
    height: 100,
    margin: "1%",
    transition: "0.8s",
    backgroundColor: "white",
    boxShadow: "0 1.4rem 8rem rgba(0,0,0,.35)",
    borderRadius: ".8rem",
    display: "flex"
  };

  return (
  <>
    {reservation?.securityCode && <ConfirmDishForm isOpen={isCardOpen} handleOpen={handleDishForm} dish={dish!} reservation={reservation} toggleReload={toggleReload} />}
    {auth.roles[0] === "ROLE_RESTAURANT" && <EditDishForm isOpen={editIsOpen} handleOpen={toggleEditForm} categoryList={categoryList!} dish={dish} categoryId={categoryId}/>}
    <Link className="dishCardHover" style={linkStyle} onClick={handleDishForm} color="inherit" underline="none">
      <img src={dish.image}  alt="la foto del plato" style={{objectFit:"cover", width: 110, borderRadius: ".8rem", aspectRatio: 1, marginRight: 20}}/>
      <div>
        <Typography variant="h6">{dish.name}</Typography>
        <Typography variant="body1">{dish.description}</Typography>
        <Typography variant="caption">${dish.price}</Typography>
      </div>
    </Link>
  </>
  );
}


export default DishCard;

