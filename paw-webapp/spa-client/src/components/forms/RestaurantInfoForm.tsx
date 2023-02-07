import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    FormControl,
    Grid,
    InputLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
    TextField,
  } from "@mui/material";
  import { FC, useState } from "react";
  import { handleResponse } from "../../handleResponse";
  import {  RestaurantModel } from "../../models";
  import { RestParams } from "../../models/Restaurant/RestParams";
  import useRestaurantService from "../../hooks/serviceHooks/useRestaurantService";
  
  type Props = {
    data: RestaurantModel | undefined;
    isOpen: boolean;
    handleOpen: () => void;
  };
  
const RestaurantInfoForm: FC<Props> = ({
    data,
    handleOpen,
    isOpen,
  }): JSX.Element => {

    const [openHour, setOpenHour] = useState<number | undefined>();
    const [totalChairs, setTotalChairs] = useState<number | undefined>();
    const [closeHour, setCloseHour] = useState<number | undefined>();

    const restaurantService = useRestaurantService();
  
    const handleSubmit = () => {

        let restaurantData = new RestParams();
        restaurantData.restaurantId = data?.id;
        restaurantData.openHour = openHour;
        restaurantData.closeHour = closeHour;
        restaurantData.totalChairs = totalChairs;
       handleResponse(
          restaurantService.editRestaurant(restaurantData),
          (response) => console.log(response)
        );
  
      handleOpen();
    };

    const handleOpenHourChange = (event: SelectChangeEvent) => {
      console.log(parseInt(event.target.value))
      setOpenHour(parseInt(event.target.value));
    };

    const handleCloseHourChange = (event: SelectChangeEvent) => {
      console.log(parseInt(event.target.value))
      setCloseHour(parseInt(event.target.value));
    };
  
    return (
      <>
        <Dialog open={isOpen}>
          <DialogTitle>Restaurant info</DialogTitle>
          <DialogContent>
            <DialogContentText>
              Write the fields you want to be modified
            </DialogContentText>
            <Grid container>
              <Grid
                item
                xs={9}
                marginX={1}
                marginY={2}
                component={TextField}
                onChange={(e: any) => setTotalChairs(e.target.value)}
                label="Total chairs"
                defaultValue={data?.totalChairs}
              />
              <Grid item component={FormControl} xs={5} marginY={2} marginX={1}>
              <InputLabel id="Open-hour-select">Open hour</InputLabel>
                <Select
                    labelId="Open-hour-select"
                    onChange={handleOpenHourChange}
                    defaultValue={data?.openHour.toString()}
                    fullWidth
                    label="Open hour"
                >
                    <MenuItem value={0}>0:00</MenuItem>
                    <MenuItem value={1}>1:00</MenuItem>
                    <MenuItem value={2}>2:00</MenuItem>
                    <MenuItem value={3}>3:00</MenuItem>
                    <MenuItem value={4}>4:00</MenuItem>
                    <MenuItem value={5}>5:00</MenuItem>
                    <MenuItem value={6}>6:00</MenuItem>
                    <MenuItem value={7}>7:00</MenuItem>
                    <MenuItem value={8}>8:00</MenuItem>
                    <MenuItem value={9}>9:00</MenuItem>
                    <MenuItem value={10}>10:00</MenuItem>
                    <MenuItem value={11}>11:00</MenuItem>
                    <MenuItem value={12}>12:00</MenuItem>
                    <MenuItem value={13}>13:00</MenuItem>
                    <MenuItem value={14}>14:00</MenuItem>
                    <MenuItem value={15}>15:00</MenuItem>
                    <MenuItem value={16}>16:00</MenuItem>
                    <MenuItem value={17}>17:00</MenuItem>
                    <MenuItem value={18}>18:00</MenuItem>
                    <MenuItem value={19}>19:00</MenuItem>
                    <MenuItem value={20}>20:00</MenuItem>
                    <MenuItem value={21}>21:00</MenuItem>
                    <MenuItem value={22}>22:00</MenuItem>
                    <MenuItem value={23}>23:00</MenuItem>
              </Select>
              </Grid>
              <Grid item component={FormControl} xs={5} marginY={2} marginX={1}>
              <InputLabel>Close hour</InputLabel>
                <Select
                    onChange={handleCloseHourChange}
                    defaultValue={data?.closeHour.toString()}
                    fullWidth
                >
                    <MenuItem value={0}>0:00</MenuItem>
                    <MenuItem value={1}>1:00</MenuItem>
                    <MenuItem value={2}>2:00</MenuItem>
                    <MenuItem value={3}>3:00</MenuItem>
                    <MenuItem value={4}>4:00</MenuItem>
                    <MenuItem value={5}>5:00</MenuItem>
                    <MenuItem value={6}>6:00</MenuItem>
                    <MenuItem value={7}>7:00</MenuItem>
                    <MenuItem value={8}>8:00</MenuItem>
                    <MenuItem value={9}>9:00</MenuItem>
                    <MenuItem value={10}>10:00</MenuItem>
                    <MenuItem value={11}>11:00</MenuItem>
                    <MenuItem value={12}>12:00</MenuItem>
                    <MenuItem value={13}>13:00</MenuItem>
                    <MenuItem value={14}>14:00</MenuItem>
                    <MenuItem value={15}>15:00</MenuItem>
                    <MenuItem value={16}>16:00</MenuItem>
                    <MenuItem value={17}>17:00</MenuItem>
                    <MenuItem value={18}>18:00</MenuItem>
                    <MenuItem value={19}>19:00</MenuItem>
                    <MenuItem value={20}>20:00</MenuItem>
                    <MenuItem value={21}>21:00</MenuItem>
                    <MenuItem value={22}>22:00</MenuItem>
                    <MenuItem value={23}>23:00</MenuItem>
              </Select>
              </Grid>
            </Grid>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleSubmit} variant="contained" color="success">
              Confirm
            </Button>
            <Button onClick={handleOpen} variant="contained">
              Cancel
            </Button>
          </DialogActions>
        </Dialog>
      </>
    );
};
  
export default RestaurantInfoForm;  