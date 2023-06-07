import { CircularProgress, Grid, Skeleton } from "@mui/material";
import { FC } from "react";
import useAuth from "../hooks/useAuth";
import { DishCategoryModel, DishModel, ReservationModel } from "../models";
import DishCard from "./dishCard/DishCard";

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
    categoryList,
    reservation,
    actualId,
    toggleReload
  }): JSX.Element => {

    const {auth} = useAuth();
    return (
        <Grid item container xs={11} spacing={2} marginTop={1}>
            {dishList?.map((dish: DishModel,i) => 
            (auth.roles[0] === "ROLE_RESTAURANT"? <Grid item key={i} xl={3} lg={5} md={5} sm={12} xs={12} margin={2} maxHeight={120}><DishCard dish={dish} key={dish.id} categoryList={categoryList} toggleReload={toggleReload} /></Grid> :
            auth.roles[0] === "ROLE_CUSTOMER"? <Grid item key={i} xl={3} lg={5} md={5} sm={12} xs={12} margin={2} maxHeight={120}><DishCard dish={dish} key={dish.id} toggleReload={toggleReload}/></Grid>
            : <Grid item key={i} xl={3} lg={5} md={5} sm={12} xs={12} margin={2} maxHeight={120}><DishCard dish={dish} key={dish.id} toggleReload={toggleReload} /></Grid> ))}
        </Grid>
    );
  }


export default DishDisplay;