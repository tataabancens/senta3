import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField, Typography } from "@mui/material";
import { FC, useState } from "react";
import { handleResponse } from "../../Utils";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import { DishParams } from "../../models/Dishes/DishParams";


type Props ={
    isOpen: boolean;
    handleOpen: () => void;
    canReload?: () => void;
};

const CategoryForm: FC<Props> = ({isOpen, handleOpen, canReload}): JSX.Element => {

    const [categoryName, setCategoryName] = useState("");
    const dishService = useDishService();

    const handleCancel = () => {
        handleOpen();
    }

    const handleSubmit = () => {
        if(categoryName.length > 0){
            let dishParams = new DishParams();
            dishParams.categoryName = categoryName;
            handleResponse(
                dishService.createCategory(dishParams),
                response => (canReload? canReload() : null)
            );
        }
        handleOpen();
    }

    return(
        <Dialog open={isOpen} fullWidth>
          <DialogTitle>Create category</DialogTitle>
          <DialogContent>
            <DialogContentText>
                Enter the name of the new category
            </DialogContentText>
            <Grid container marginY={3}>
                <Grid
                    item
                    xs={12}
                    fullWidth
                    marginBottom={3}
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

export default CategoryForm;