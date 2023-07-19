import {
    Button,
    ButtonGroup,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Grid,
    TextField,
    Typography,
  } from "@mui/material";
import { FC, useContext, useState } from "react";
import { DishModel } from "../../models";
import { OrderitemParams } from "../../models/OrderItems/OrderitemParams";
import useOrderItemService from "../../hooks/serviceHooks/orderItems/useOrderItemService";
import { ReservationContext } from "../../context/ReservationContext";
import { useTranslation } from "react-i18next";
import { extractCustomerIdFromContent } from "../../pages/SignUpPage";
import useServices from "../../hooks/useServices";

  
type Props = {
  dish: DishModel;
  isOpen: boolean;
  handleOpen: () => void;
};
  
const ConfirmDishForm: FC<Props> = ({
  dish,
  handleOpen,
  isOpen,
}): JSX.Element => {
  const [qty, setQty] = useState(0);
  const { orderItemService } = useServices();
  const { reservation, reloadItems, updateReservation } = useContext(ReservationContext);
  const { t } = useTranslation();

  const handleDecrease = () => {
    if(qty > 0){
      setQty(qty-1);
    }
  }

  const handleIncrease = () => {
    if(qty < 30){
      setQty(qty+1);
    }
  }

  const handleCancel = () => {
    setQty(0);
    handleOpen();
  }
  
  const handleSubmit = async () => {
    if(qty > 0){
      let orderItem = new OrderitemParams();
      const abortController = new AbortController();
      orderItem.dishId = dish.id;
      orderItem.quantity = qty;
      orderItem.securityCode = reservation?.securityCode;
      orderItem.customerId = extractCustomerIdFromContent(reservation?.customer!);
      const { isOk } = await orderItemService.createOrderItem(orderItem, abortController);

      if(isOk){
        reloadItems();
        updateReservation();
        setQty(0);
      }
        
    }

    handleOpen();
    };
  
    return (
      <>
        <Dialog open={isOpen}>
          <img src={dish.image}  alt="la foto del plato" style={{objectFit:"cover", width: "100%", height: 300, aspectRatio: 1}}/>
          <DialogTitle>{dish.name}</DialogTitle>
          <DialogContent>
            <DialogContentText>
                {dish.description}
            </DialogContentText>
            <Grid container marginY={3}>
                <Grid item xs={12} component={Typography} marginBottom={2}>{t('forms.confirmDish.dishPrice',{price: dish.price})}</Grid>
                <Grid item container xs={12} justifyContent="space-evenly">
                    <Grid item xs={4} component={ButtonGroup}>
                        <Button variant="contained" onClick={handleDecrease}><Typography>-</Typography></Button>
                        <TextField disabled sx={{width: 100}} value={qty}/>
                        <Button variant="contained"  onClick={handleIncrease}><Typography>+</Typography></Button>
                    </Grid>
                    <Grid item sx={{display:"flex", alignItems:"center", justifyContent:"center"}} xs={8} ><Typography>{t('forms.confirmDish.subtotal',{subtotal: dish.price * qty})}</Typography></Grid>
                </Grid>
            </Grid>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleSubmit} variant="contained" color="success">
              {t('forms.confirmDish.addToCart')}
            </Button>
            <Button onClick={handleCancel} variant="contained">
            {t('forms.cancelButton')}
            </Button>
          </DialogActions>
        </Dialog>
      </>
    );
  };
  
  export default ConfirmDishForm;