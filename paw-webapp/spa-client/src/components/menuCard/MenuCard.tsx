import { Grid, IconButton, Link, Typography } from "@mui/material";
import { FC, useState } from "react";
import { linkStyle } from "../../constants/constants";
import DeleteIcon from '@mui/icons-material/Delete';
import { CategoryContext, ReservationContext } from "../../context/ReservationContext";
import useAuth from "../../hooks/serviceHooks/authentication/useAuth";
import { DishCategoryModel, DishModel } from "../../models";
import EditDishForm from "../forms/EditDishForm";
import './styles.css';
import useRestaurantMenuContext from "../../hooks/useRestaurantMenuContext";
import DeleteDishMessage from "../DeleteDishMessage";

type Props = {
    dish: DishModel;
  };
  
  const MenuCard: FC<Props> = ({
    dish
  }): JSX.Element => {
    const [isHovered, setIsHovered] = useState(false);
    const [confirmationMessage, setConfirmationMessage] = useState(false);

    const { useEditDishIsOpen: { setEditDishIsOpen }, useDish: { setDish }} = useRestaurantMenuContext();

    const toggleEditForm = () => {
      setDish(dish);
      setEditDishIsOpen(true);
    };

    const handleDeleteDish = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
      event.stopPropagation();
      toggleDeleteForm()
    }

    const toggleDeleteForm = () => {
      setConfirmationMessage(!confirmationMessage);
    }
  
    return (
    <>
      <DeleteDishMessage isOpen={confirmationMessage} handleOpen={toggleDeleteForm} dish={dish} />
      <Link 
        className={`menuCardHover ${isHovered ? "hovered" : ""}`}
        style={linkStyle}
        onClick={toggleEditForm}
        color="inherit"
        underline="none"
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
        >
        <img src={dish.image}  alt="la foto del plato" style={{objectFit:"cover", width: 110, borderRadius: ".8rem", aspectRatio: 1, marginRight: 20}}/>
        <Grid item container xs={12}>
        <Grid item xs={12} sx={{ position: "relative", zIndex: 1 }}>
            <IconButton className="iconButton" style={{ position: "absolute", top: 0, right: 0, zIndex: 2 }} onClick={handleDeleteDish}>
              <DeleteIcon  color="error"/>
            </IconButton>
          </Grid>
          <Grid item xs={12}><Typography variant="h6">{dish.name}</Typography></Grid>
          <Grid item xs={12}><Typography variant="body1">{dish.description}</Typography></Grid>
          <Grid item xs={12}><Typography variant="caption">${dish.price}</Typography></Grid>
        </Grid>
      </Link>
    </>
    );
}
  
  
export default MenuCard;