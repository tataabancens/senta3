import { Grid, Link, Typography } from "@mui/material";
import { FC, useState } from "react";
import { linkStyle } from "../constants/constants";
import { DishModel } from "../models";
import DishCard from "./dishCard/DishCard";
import MenuCard from "./menuCard/MenuCard";
import CreateDishForm from "./forms/CreateDishForm";
import { useTranslation } from "react-i18next";

type Props = {
    dishes: DishModel[];
    isMenu: boolean;
};

const DishDisplay: FC<Props> = ({
    dishes,
    isMenu
  }): JSX.Element => {

    const [ openForm, setOpenForm ] = useState(false);

    const handleFormOpen = () => { setOpenForm(!openForm)}

    const { t } = useTranslation();

    return (
        <Grid item container xs={11} spacing={2} marginTop={1}>
            {isMenu && <CreateDishForm formIsOpen={openForm} handleOpenForm={handleFormOpen} />}
            {dishes.map((dish: DishModel, i) => 
             <Grid item key={i} xl={3} lg={5} md={5} sm={12} xs={12} margin={2} maxHeight={120}>
              {isMenu? <MenuCard dish={dish} key={dish.id} /> : <DishCard dish={dish} key={dish.id} />}
            </Grid> )}
             {isMenu && 
              <Grid item xl={3} lg={5} md={5} sm={12} xs={12} margin={2} maxHeight={120}>
                <Link className="dishCardHover" style={linkStyle} color="inherit" underline="none" sx={{display: "flex", alignItems: "center"}} onClick={handleFormOpen}>
                  <Grid item xs={12}>
                    <Typography variant="h4" align="center">{t('dishDisplay.createDish')}</Typography>
                  </Grid>
                </Link>
              </Grid>
              }
        </Grid>
    );
  }


export default DishDisplay;