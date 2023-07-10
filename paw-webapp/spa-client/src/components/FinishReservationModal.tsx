import { Alert, Box, Button, CircularProgress, Modal, Snackbar, Typography } from "@mui/material";
import { FC, useContext, useState } from "react";
import useDishService from "../hooks/serviceHooks/dishes/useDishService";
import { DishCategoryModel } from "../models";
import { DishParams } from "../models/Dishes/DishParams";
import useRestaurantMenuContext from "../hooks/useRestaurantMenuContext";
import { useTranslation } from "react-i18next";
import { ReservationContext } from "../context/ReservationContext";
import { useNavigate } from "react-router-dom";
import { paths } from "../constants/constants";
import useAuth from "../hooks/serviceHooks/authentication/useAuth";
import { UserRoles } from "../models/Enums/UserRoles";

type Props = {
  isOpen: boolean;
  handleOpen: () => void;
};

const style = {
  position: 'absolute' as 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 500,
  borderRadius: 4,
  height: 400,
  bgcolor: 'background.paper',
  boxShadow: 24,
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  justifyContent: "space-evenly",
  p: 4,
};

const FinishReservationModal: FC<Props> = ({ isOpen, handleOpen}) => {

  const { t } = useTranslation();
  const navigate = useNavigate();
  const { auth } = useAuth();

  const handleAccept = async () => {
    navigate(paths.ROOT);
  }

  return (
    <>
    <Modal
      open={isOpen}
      onClose={handleOpen}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <Typography id="modal-modal-title" variant="h5" marginBottom={10} marginTop={2}>{t('finishReservationModal.title')}</Typography>
        {auth.roles[0] === UserRoles.ANONYMOUS && <Typography id="modal-modal-title" variant="body1" marginBottom={5}>{t('finishReservationModal.finishedMessageNoPoints')}</Typography>}
        {auth.roles[0] === UserRoles.CUSTOMER && <Typography id="modal-modal-title" variant="body1" marginBottom={5}>{t('finishReservationModal.finishedMessageWithPoints')}</Typography>}
        <Box sx={{ display: "flex", width: 1, justifyContent: "center", marginY: 1 }}>
          <Button variant="contained" fullWidth color="success" onClick={handleAccept}><Typography>{t('finishReservationModal.goToMenu')}</Typography></Button>
        </Box>
      </Box>
    </Modal>
    </>
  );
}

export default FinishReservationModal;