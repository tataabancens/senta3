import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField } from "@mui/material";
import { FC, useState } from "react";
import { handleResponse } from "../../Utils";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import { DishCategoryModel } from "../../models";
import { DishParams } from "../../models/Dishes/DishParams";


type Props ={
    isOpen: boolean;
    handleOpen: () => void;
    category: DishCategoryModel | undefined;
    canReload: () => void;
};

const EditCategoryForm: FC<Props> = ({isOpen, handleOpen, category, canReload}): JSX.Element => {

    const [categoryName, setCategoryName] = useState("");
    const dishService = useDishService();

    const handleCancel = () => {
        handleOpen();
    }

    const handleSubmit = () => {
        if(categoryName.length > 0){
            let dishParams = new DishParams();
            dishParams.categoryName = categoryName;
            dishParams.categoryId = category?.id;
            handleResponse(
                dishService.editCategory(dishParams),
                response => {canReload()}
            );
        }
        handleOpen();
    }

    return(
        <Dialog open={isOpen} fullWidth>
          <DialogTitle>Edit category</DialogTitle>
          <DialogContent>
            <DialogContentText>
                Enter the new name of the category
            </DialogContentText>
            <Grid container marginY={3}>
                <Grid
                    item
                    xs={12}
                    fullWidth
                    marginBottom={3}
                    defaultValue={category?.name}
                    component={TextField}
                    onChange={(e: any) => setCategoryName(e.target.value)}
                    label="Enter category name"
                />
            </Grid>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleSubmit} variant="contained" color="success">
              Confirm
            </Button>
            <Button onClick={handleCancel} variant="contained">
              Cancel
            </Button>
          </DialogActions>
        </Dialog>
    );
}

export default EditCategoryForm;