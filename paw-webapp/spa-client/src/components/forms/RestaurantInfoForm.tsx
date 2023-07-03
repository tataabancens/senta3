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
  import { handleResponse } from "../../Utils";
  import {  RestaurantModel } from "../../models";
  import { RestParams } from "../../models/Restaurant/RestParams";
  import useRestaurantService from "../../hooks/serviceHooks/restaurants/useRestaurantService";
import { useTranslation } from "react-i18next";
import { timeArray } from "../../constants/constants";
  
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
    const { t } = useTranslation();

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
      setOpenHour(parseInt(event.target.value));
    };

    const handleCloseHourChange = (event: SelectChangeEvent) => {
      setCloseHour(parseInt(event.target.value));
    };
  
    return (
      <>
        <Dialog open={isOpen}>
          <DialogTitle>{t('forms.restaurantInfo.title')}</DialogTitle>
          <DialogContent>
            <DialogContentText>
              {t('forms.restaurantInfo.description')}
            </DialogContentText>
            <Grid container>
              <Grid
                item
                xs={9}
                marginX={1}
                marginY={2}
                component={TextField}
                onChange={(e: any) => setTotalChairs(e.target.value)}
                label={t('forms.restaurantInfo.chairs')}
                defaultValue={data?.totalChairs}
              />
              <Grid item component={FormControl} xs={5} marginY={2} marginX={1}>
              <InputLabel id="Open-hour-select">{t('forms.restaurantInfo.openHour')}</InputLabel>
                <Select
                    labelId="Open-hour-select"
                    onChange={handleOpenHourChange}
                    defaultValue={data?.openHour.toString()}
                    fullWidth
                    label={t('forms.restaurantInfo.openHour')}
                >
                    {timeArray.map((time, index) => <MenuItem value={index}>{time}</MenuItem>)}
              </Select>
              </Grid>
              <Grid item component={FormControl} xs={5} marginY={2} marginX={1}>
              <InputLabel>{t('forms.restaurantInfo.closeHour')}</InputLabel>
                <Select
                    onChange={handleCloseHourChange}
                    defaultValue={data?.closeHour.toString()}
                    fullWidth
                >
                  {timeArray.map((time, index) => <MenuItem value={index}>{time}</MenuItem>)}
              </Select>
              </Grid>
            </Grid>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleSubmit} variant="contained" color="success">
              {t('forms.confirmButton')}
            </Button>
            <Button onClick={handleOpen} variant="contained">
              {t('forms.cancelButton')}
            </Button>
          </DialogActions>
        </Dialog>
      </>
    );
};
  
export default RestaurantInfoForm;  