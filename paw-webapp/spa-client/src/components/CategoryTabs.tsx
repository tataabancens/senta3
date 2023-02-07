import { Grid, Tab, Tabs } from "@mui/material";
import React, { FC } from "react";
import { DishCategoryModel } from "../models";

type Props = {
    value: number;
    handleChange: (event: React.SyntheticEvent, value: number) => void;
    categoryList: DishCategoryModel[];
};

const CategoryTabs: FC<Props> = ({
    value,
    handleChange,
    categoryList
  }): JSX.Element => {
    return (
        <Grid item xs={11} marginTop={2}>
        <Tabs value={value} onChange={(event,value) => handleChange(event, value)} variant="scrollable" scrollButtons="auto" aria-label="scrollable auto tabs example">
          {categoryList.map((category: DishCategoryModel) => <Tab value={category.id} label={category.name} />)}
        </Tabs>
    </Grid>
    );
  }


export default CategoryTabs;
  