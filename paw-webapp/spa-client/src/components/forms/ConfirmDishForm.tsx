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
import { FC, useState } from "react";
import { handleResponse } from "../../Utils";
import { DishModel, ReservationModel } from "../../models";
import { CustomerParams } from "../../models/Customers/CustomerParams";
import { OrderitemParams } from "../../models/OrderItems/OrderitemParams";
import useOrderItemService from "../../hooks/serviceHooks/orderItems/useOrderItemService";

  
  type Props = {
    dish: DishModel;
    isOpen: boolean;
    handleOpen: () => void;
    reservation: ReservationModel | undefined;
    toggleReload?: () => void;
  };
  
  const AccountInfoForm: FC<Props> = ({
    dish,
    handleOpen,
    isOpen,
    reservation,
    toggleReload
  }): JSX.Element => {
    const [qty, setQty] = useState(0);
    
    const orderItemService = useOrderItemService();

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
  
    const handleSubmit = () => {
      if(qty > 0){
        let orderItem = new OrderitemParams();
        orderItem.dishId = dish.id;
        orderItem.quantity = qty;
        orderItem.securityCode = reservation?.securityCode;
        handleResponse(
          orderItemService.createOrderItem(orderItem),
          (response) => (toggleReload? toggleReload(): null)
        );
        setQty(0);
      }

      handleOpen();
    };
  
    return (
      <>
        <Dialog open={isOpen}>
          <DialogTitle>{dish.name}</DialogTitle>
          <DialogContent>
            <DialogContentText>
                {dish.description}
            </DialogContentText>
            <Grid container marginY={3}>
                <Grid item xs={12} component={Typography} marginBottom={2}>price: ${dish.price}</Grid>
                <Grid item container xs={12} justifyContent="space-evenly">
                    <Grid item xs={4} component={ButtonGroup}>
                        <Button variant="contained" onClick={handleDecrease}><Typography>-</Typography></Button>
                        <TextField disabled sx={{width: 130}} value={qty}/>
                        <Button variant="contained" onClick={handleIncrease}><Typography>+</Typography></Button>
                    </Grid>
                    <Grid item sx={{display:"flex", alignItems:"center", justifyContent:"center"}} xs={8} ><Typography>Subtotal: ${dish.price * qty}</Typography></Grid>
                </Grid>
            </Grid>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleSubmit} variant="contained" color="success">
              Add to cart
            </Button>
            <Button onClick={handleCancel} variant="contained">
              Cancel
            </Button>
          </DialogActions>
        </Dialog>
      </>
    );
  };
  
  export default AccountInfoForm;