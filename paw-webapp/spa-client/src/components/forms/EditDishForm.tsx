import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Grid,
    InputLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
    TextField,
  } from "@mui/material";
import { FC, useState } from "react";
import { handleResponse } from "../../Utils";
import { DishCategoryModel, DishModel } from "../../models";
import useDishService from "../../hooks/serviceHooks/dishes/useDishService";
import { DishParams } from "../../models/Dishes/DishParams";

  
type Props = {
    dish: DishModel;
    isOpen: boolean;
    categoryList: DishCategoryModel[];
    handleOpen: () => void;
    categoryId: number | undefined;
};
  
const EditDishForm: FC<Props> = ({
    dish,
    handleOpen,
    isOpen,
    categoryList,
    categoryId
  }): JSX.Element => {

    const [name, setName] = useState();
    const [image, setImage] = useState();
    const [description, setDescription] = useState();
    const [price, setPrice] = useState();
    const [selectState, setState] = useState("0");
    
    const dishService = useDishService();

  
    const handleSubmit = () => {
        let newDish = new DishParams();
        newDish.name = name;
        newDish.price = price;
        newDish.id = dish.id;
        newDish.description = description;
        newDish.categoryId = parseInt(selectState);
        handleResponse(
          dishService.editDish(newDish),
          (response:any) => {}
        );
        handleOpen();
    };

    const handleCancel = () => {
      handleOpen()
    }

    const handleChange = (event: SelectChangeEvent) => {
        setState(event.target.value)
    }
  
    return (
      <>
        <Dialog open={isOpen}>
          <DialogTitle>Dish edition</DialogTitle>
          <DialogContent>
            <DialogContentText>
                edit any or all the fields you want changed
            </DialogContentText>
            <Grid container marginY={3} >
                <Grid
                  item
                  xs={12}
                  marginBottom={3}
                >
                  <Button variant="contained" component="label">
                                     Upload image
                  <input hidden accept="image/*" multiple type="file" />
                  </Button>
                </Grid>
                <Grid
                    item
                    xs={12}
                    marginBottom={3}
                    component={TextField}
                    onChange={(e: any) => setName(e.target.value)}
                    label="Name"
                    defaultValue={dish.name}
                />
                <Grid
                    item
                    xs={12}
                    marginBottom={3}
                    component={TextField}
                    fullWidth
                    onChange={(e: any) => setDescription(e.target.value)}
                    label="Description"
                    defaultValue={dish.description}
                />
                <Grid
                    item
                    xs={12}
                    marginBottom={3}
                    component={TextField}
                    onChange={(e: any) => setPrice(e.target.value)}
                    label="Price"
                    defaultValue={dish.price}
                />
                <Grid
                    item
                    xs={12}
                    marginBottom={3}
                >
                    <InputLabel id="demo-simple-select-helper-label">Category</InputLabel>
                    <Select
                        labelId="demo-simple-select-helper-label"
                        value={categoryId?.toString()}
                        autoWidth
                        label="Category"
                        onChange={handleChange}
                    >
                        {categoryList?.map((category) => <MenuItem value={category.id.toString()} key={category.id}>{category.name}</MenuItem>)}
                    </Select>
                </Grid>
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
      </>
    );
}
  
export default EditDishForm;