import { Box, Button, Modal, Typography } from "@mui/material";
import { FC, useState } from "react";
import { handleResponse } from "../Utils";
import useDishService from "../hooks/serviceHooks/dishes/useDishService";
import { DishCategoryModel, ReservationModel } from "../models";
import { DishParams } from "../models/Dishes/DishParams";


type Props = {
    isOpen: boolean;
    handleOpen: () => void;
    data?: DishCategoryModel | undefined;
    canReload: () => void;
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

const ConfirmationMessage: FC<Props> = ({isOpen, handleOpen, data, canReload}) => {

    const dishService = useDishService();

    const handleClose = () => {
        handleOpen();
    }

    const handleAccept = () => {
      let dishParams = new DishParams();  
      dishParams.categoryId = data?.id;
      handleResponse(
        dishService.deleteCategory(dishParams),
        (response) => canReload()
      );
      handleClose();
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
              Delete category
            </Typography>
            <Typography id="modal-modal-description" marginY={1}>
              Are you sure you want to delete this category?
            </Typography>
            <Box sx={{display:"flex", width: 1, justifyContent:"space-between", marginY: 1}}>
              <Button variant="contained" color="success" onClick={handleAccept}><Typography>Accept</Typography></Button>
              <Button variant="contained" color="error" onClick={handleClose}><Typography>Cancel</Typography></Button>
            </Box>
          </Box>
        </Modal>
    );
}

export default ConfirmationMessage;