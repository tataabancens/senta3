import { Alert, Box, Button, Modal, Snackbar, Typography } from "@mui/material";
import { FC, useState } from "react";
import useDishService from "../hooks/serviceHooks/dishes/useDishService";
import { DishCategoryModel } from "../models";
import { DishParams } from "../models/Dishes/DishParams";
import useRestaurantMenuContext from "../hooks/useRestaurantMenuContext";
import { useTranslation } from "react-i18next";


type Props = {
  isOpen: boolean;
  handleOpen: () => void;
  category: DishCategoryModel;
  dishes: number;
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

const ConfirmationMessage: FC<Props> = ({ isOpen, handleOpen, category, dishes}) => {

  const dishService = useDishService();
  const { getDishCategories: { categoryList, categoryMap, setCategories, setCategoryMap }, useCurrentCategory: { setCategoryId } } = useRestaurantMenuContext();
  const { t } = useTranslation();
  const [snackbarOpen, setSnackbarOpen] = useState(false);

  const handleClose = () => {
    handleOpen();
  }

  const handleAccept = async () => {
    let dishParams = new DishParams();
    dishParams.categoryId = category?.id;
    if(dishes === 0){
      const { isOk } = await dishService.deleteCategory(dishParams);
      if (isOk) {
        const updatedCategoryList = categoryList?.filter((c) => c.id !== category.id) || [];
        setCategories(updatedCategoryList!);
  
        const updateCategoryMap = new Map(categoryMap);
        updateCategoryMap.delete(category.id);
        setCategoryMap(updateCategoryMap);
  
        setCategoryId(updatedCategoryList[0].id);
        handleClose();
        setSnackbarOpen(true);
      }
    }
  }

  const handleSnackbarClose = () => {
    setSnackbarOpen(false);
  };

  return (
    <>
        <Modal
      open={isOpen}
      onClose={handleOpen}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <Typography id="modal-modal-title" variant="h5" component="h2" marginY={1}>
          {t('restaurantMenu.deleteMessage.title')}
        </Typography>
        <Typography id="modal-modal-description" marginY={1}>
          {t('restaurantMenu.deleteMessage.description')}
        </Typography>
        { dishes > 0 &&
        <Typography id="modal-modal-description" marginY={1} variant="subtitle2" align="center" color="error">
          {t('restaurantMenu.deleteMessage.disableMessage')}
        </Typography>}
        <Box sx={{ display: "flex", width: 1, justifyContent: "space-between", marginY: 1 }}>
          {dishes === 0 && <Button variant="contained" color="success" onClick={handleAccept}><Typography>{t('forms.confirmButton')}</Typography></Button>}
          {dishes > 0 && <Button variant="contained" disabled color="success" onClick={handleAccept}><Typography>{t('forms.confirmButton')}</Typography></Button>}
          <Button variant="contained" color="error" onClick={handleClose}><Typography>{t('forms.cancelButton')}</Typography></Button>
        </Box>
      </Box>
    </Modal>
    <Snackbar open={snackbarOpen} autoHideDuration={3000} onClose={handleSnackbarClose}>
        <Alert onClose={handleSnackbarClose} severity="success">
          {t('restaurantMenu.deleteMessage.succesfulDeleteMessage')}
        </Alert>
      </Snackbar>
    </>
  );
}

export default ConfirmationMessage;