import { Link, Typography } from "@mui/material";
import { FC, useContext, useState } from "react";
import { linkStyle } from "../../constants/constants";
import { CategoryContext, ReservationContext } from "../../context/ReservationContext";
import useAuth from "../../hooks/useAuth";
import { DishCategoryModel, DishModel } from "../../models";
import EditDishForm from "../forms/EditDishForm";
import './styles.css';
import useRestaurantMenuContext from "../../hooks/useRestaurantMenuContext";

type Props = {
    dish: DishModel;
  };
  
  const MenuCard: FC<Props> = ({
    dish
  }): JSX.Element => {
  
    const [editIsOpen, setEditIsOpen] = useState(false);

    const { useEditDishIsOpen: { setEditDishIsOpen }, useDish: { setDish }} = useRestaurantMenuContext();
  
    const toggleEditForm = () => {
      setDish(dish);
      setEditDishIsOpen(true);
    };
  
    return (
    <>
      <Link className="menuCardHover" style={linkStyle} onClick={toggleEditForm} color="inherit" underline="none">
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
  
  
  export default MenuCard;