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
import useAuth from "../hooks/useAuth";


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

  const { reservation } = useContext(ReservationContext);
  const { t } = useTranslation();
  const { auth } = useAuth();
  const navigate = useNavigate();

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
        {reservation!.status === "CHECK_ORDERED" && <Typography id="modal-modal-title" variant="h5" marginBottom={10} marginTop={2}>{t('finishReservationModal.title')}</Typography>}
        {reservation!.status === "FINISHED" && <Typography id="modal-modal-title" variant="h5" marginBottom={10} marginTop={2}>{t('finishReservationModal.finishedTitle')}</Typography>}
        {reservation!.status === "CHECK_ORDERED" && <CircularProgress />}
        {reservation!.status === "CHECK_ORDERED" && <Typography id="modal-modal-description" marginTop={10} marginBottom={2}>{t('finishReservationModal.waitingMessage')}</Typography>}
        {reservation!.status === "FINISHED" && auth.roles[0] === "ROLE_ANONYMOUS" &&
          <Typography id="modal-modal-description" marginTop={10} marginBottom={2}>{t('finishReservationModal.finishedMessageNoPoints')}</Typography>}
        {reservation!.status === "FINISHED" && auth.roles[0] !== "ROLE_ANONYMOUS" &&
          <Typography id="modal-modal-description" marginTop={10} marginBottom={2}>{t('finishReservationModal.finishedMessageWithPoints')}</Typography>}
        {reservation!.status === "FINISHED" && 
          <Box sx={{ display: "flex", width: 1, justifyContent: "center", marginY: 1 }}>
            <Button variant="contained" fullWidth color="success" onClick={handleAccept}><Typography>{t('finishReservationModal.goToMenu')}</Typography></Button>
          </Box>
        }
      </Box>
    </Modal>
    </>
  );
}

export default FinishReservationModal;