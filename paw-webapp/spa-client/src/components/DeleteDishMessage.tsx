import { Box, Button, Modal, Typography } from "@mui/material";
import { FC, useContext } from "react";
import useDishService from "../hooks/serviceHooks/dishes/useDishService";
import { DishModel } from "../models";
import { useTranslation } from "react-i18next";
import useRestaurantMenuContext from "../hooks/useRestaurantMenuContext";
import useServiceProvider from "../context/ServiceProvider";


type Props = {
  isOpen: boolean;
  handleOpen: () => void;
  dish: DishModel;
};

const style = {
  position: 'absolute' as 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 500,
  borderRadius: 4,
  height: 300,
  bgcolor: 'background.paper',
  boxShadow: 24,
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  justifyContent: "space-evenly",
  p: 4,
};

const DeleteDishMessage: FC<Props> = ({ isOpen, handleOpen, dish }) => {

  const { dishService: ds } = useServiceProvider();
  const { t } = useTranslation();
  const { getDishes } = useRestaurantMenuContext();

  const handleClose = () => {
    handleOpen();
  }

  const deleteDish = async () => {
    const {isOk} = await ds.deleteDish(dish.id);
    if(isOk) {
        getDishes.removeDish(dish);
        handleOpen();
    }
  }

  return (
    <Modal
      open={isOpen}
      onClose={handleOpen}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <Typography id="modal-modal-title" variant="h5" component="h2" marginY={1}>
          {t('restaurantMenu.deleteDish.title')}
        </Typography>
        <Typography id="modal-modal-description" marginY={1}>
          {t('restaurantMenu.deleteDish.description')}
        </Typography>
        <Box sx={{ display: "flex", width: 1, justifyContent: "space-between", marginY: 1 }}>
          <Button variant="contained" color="success" onClick={deleteDish}><Typography>{t('forms.confirmButton')}</Typography></Button>
          <Button variant="contained" color="error" onClick={handleClose}><Typography>{t('forms.cancelButton')}</Typography></Button>
        </Box>
      </Box>
    </Modal>
  );
}

export default DeleteDishMessage;