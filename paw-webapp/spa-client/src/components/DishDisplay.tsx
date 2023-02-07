import { Box, Grid, Typography } from "@mui/material";
import { FC } from "react";
import { DishModel, RestaurantModel } from "../models";
import DishCard from "./DishCard";

type Props = {
    dishList: DishModel[],
    role: string
};

const DishDisplay: FC<Props> = ({
    dishList,
    role
  }): JSX.Element => {
    return (
        <Grid item container xs={11} spacing={2} marginTop={1}>
            {dishList.map((dish: DishModel) => <DishCard dish={dish} role={role}/>)}
        </Grid>
    );
  }


export default DishDisplay;