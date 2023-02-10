import { Grid } from "@mui/material";
import { FC } from "react";
import { DishCategoryModel, DishModel, ReservationModel } from "../models";
import DishCard from "./DishCard";

type Props = {
    dishList: DishModel[];
    role: string;
    categoryList?: DishCategoryModel[];
    reservation?: ReservationModel;
    actualId?: number;
    toggleReload?: () => void;
};

const DishDisplay: FC<Props> = ({
    dishList,
    role,
    categoryList,
    reservation,
    actualId,
    toggleReload
  }): JSX.Element => {
    return (
        <Grid item container xs={11} spacing={2} marginTop={1}>
            {dishList.map((dish: DishModel) => (role === "ROLE_RESTAURANT"? <DishCard dish={dish} key={dish.id} categoryList={categoryList} role={role} toggleReload={toggleReload} /> :
            role === "ROLE_CUSTOMER"? <DishCard dish={dish} key={dish.id} role={role}  reservation={reservation} toggleReload={toggleReload}/>
            : <DishCard dish={dish} key={dish.id} role={role} reservation={reservation} toggleReload={toggleReload} /> ))}
        </Grid>
    );
  }


export default DishDisplay;