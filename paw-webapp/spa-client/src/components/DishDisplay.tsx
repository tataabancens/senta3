import { CircularProgress, Grid, Link, Skeleton, Typography } from "@mui/material";
import { FC, useContext, useState } from "react";
import { linkStyle } from "../constants/constants";
import { DishCategoryModel, DishModel, ReservationModel } from "../models";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import DishCard from "./dishCard/DishCard";
import MenuCard from "./menuCard/MenuCard";
import { CategoryContext } from "../context/ReservationContext";
import CreateDishForm from "./forms/CreateDishForm";
import { useDishes } from "../hooks/serviceHooks/dishes/useDishes";

type Props = {
    toggleReload?: () => void;
    dishes: DishModel[];
    isMenu: boolean;
};

const DishDisplay: FC<Props> = ({
    toggleReload,
    dishes,
    isMenu
  }): JSX.Element => {

    const { categoryList, value, categoryMap } = useContext(CategoryContext);
    const [ openForm, setOpenForm ] = useState(false);

    const handleFormOpen = () => { setOpenForm(!openForm)}


    return (
        <Grid item container xs={11} spacing={2} marginTop={1}>
            {isMenu && <CreateDishForm formIsOpen={openForm} handleOpenForm={handleFormOpen} />}
            {dishes.map((dish: DishModel, i) => 
             <Grid item key={i} xl={3} lg={5} md={5} sm={12} xs={12} margin={2} maxHeight={120}>
              {isMenu? <MenuCard dish={dish} key={dish.id} /> : <DishCard dish={dish} key={dish.id} toggleReload={toggleReload} />}
             </Grid> )}
             {isMenu && 
              <Grid item xl={3} lg={5} md={5} sm={12} xs={12} margin={2} maxHeight={120}>
                <Link className="dishCardHover" style={linkStyle} color="inherit" underline="none" sx={{display: "flex", alignItems: "center"}} onClick={handleFormOpen}>
                  <Grid item xs={12}>
                    <Typography variant="h4" align="center">Create Dish</Typography>
                  </Grid>
                </Link>
              </Grid>
              }
        </Grid>
    );
  }


export default DishDisplay;