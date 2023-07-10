import { Button, CircularProgress, Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { FC, useContext, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { ReservationContext } from "../../context/ReservationContext";
import { useRecommendedDish } from "../../hooks/serviceHooks/dishes/useRecommendedDish";
import useOrderItemService from "../../hooks/serviceHooks/orderItems/useOrderItemService";
import { OrderItemModel } from "../../models";
import { OrderitemParams } from "../../models/OrderItems/OrderitemParams";
import DishCard from "../dishCard/DishCard";
import ShoppingCartItem from "./ShoppingCartItem";

type TabPanelProps = {
  children?: React.ReactNode;
  index: number;
  value: number;
}

const ShoppingCartPanel: FC<TabPanelProps> = (props: TabPanelProps) => {
  const { value, index } = props;

  const orderItemService = useOrderItemService();
  const { reservation, updateReservation, orderItems, removeItem, discount, restaurant } = useContext(ReservationContext);
  let total = 0;
  const textDecoration = discount && total !== 0? 'line-through' : 'none';
  const abortController = new AbortController();
  const { t } = useTranslation();

  const {recomendedDish, error: systemError, loading: loadingRecomendation, noDish} = useRecommendedDish(reservation!, orderItems!, updateReservation)

  const calculateTotal = (orderItems: OrderItemModel[]) => {
    let total = 0;
    orderItems.filter(orderItem => orderItem.status === "SELECTED").map(filteredItem => { total += filteredItem.quantity * filteredItem.unitPrice })

    return total;
  }

  useEffect(() => {
    total = calculateTotal(orderItems!)
  },[orderItems])

  const cancelDishes = async () => {
    orderItems?.filter(async (orderItem) => orderItem.status === "SELECTED").map(async (filteredItem) => {
      let orderItemParams = new OrderitemParams();
      orderItemParams.securityCode = reservation!.securityCode;
      orderItemParams.orderItemId = filteredItem.orderItemId;
      orderItemParams.status = "DELETED";
      const { isOk } = await orderItemService.editOrderItem(orderItemParams, abortController);

      if (isOk) {
        removeItem(filteredItem)
      }

    }
    );
  }

  const confirmDishes = async () => {
    orderItems?.filter(async (orderItem) => orderItem.status === "SELECTED").map(async (filteredItem) => {
      let orderItemParams = new OrderitemParams();
      orderItemParams.securityCode = reservation!.securityCode;
      orderItemParams.orderItemId = filteredItem.orderItemId;
      orderItemParams.status = "ORDERED";
      const { isOk } = await orderItemService.editOrderItem(orderItemParams, abortController);

      if (isOk) {
        removeItem(filteredItem)
      }
    }
    );
  }

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      style={{ height: '100%' }}
    >
      {value === index && (
        <Grid item container xs={12} sx={{ display: "flex", justifyContent: "space-between" }}>
          <Grid item xs={6.5} component={Table}>
            <TableHead>
              <TableRow>
                <TableCell align="left">{t('shoppingCart.tableHeaders.dish')}</TableCell>
                <TableCell align="center">{t('shoppingCart.tableHeaders.qty')}</TableCell>
                <TableCell align="center">{t('shoppingCart.tableHeaders.subtotal')}</TableCell>
                <TableCell></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {orderItems?.filter(orderItem => orderItem.status === "SELECTED").map((filteredItem) => (
                <ShoppingCartItem key={filteredItem.orderItemId} securityCode={reservation!.securityCode} orderItem={filteredItem} isCartItem={true} usedDiscount={discount}/>
              ))}
            </TableBody>
          </Grid>
          <Grid item container xs={5}>
            <Grid item xs={12} sx={{ display: "flex", justifyContent: "center" }}>
              <Typography variant="h6" >{t('shoppingCart.cartPanel.recommendationTitle')}</Typography>
            </Grid>
            <Grid item xs={11} sx={{ display: "flex", justifyContent: "center" }}>
              {noDish !== undefined && <Typography variant="subtitle1" sx={{ marginTop: 10 }}>{t('shoppingCart.cartPanel.noRecommendation')}</Typography>}
              {loadingRecomendation && <CircularProgress />}
              {recomendedDish && !systemError && noDish === undefined  && <DishCard dish={recomendedDish} />}
            </Grid>
          </Grid>
          <Grid item container xs={12} sx={{ marginTop: 8 }}>
            <Grid item xs={12} sx={{ display: "flex", justifyContent: "center" }} marginY={2}>
              <Typography marginRight={1}>Total:</Typography>
              <Typography style={{textDecoration}} marginRight={discount? 1 : 0}>${calculateTotal(orderItems!)}</Typography>
              {discount && total !== 0 && <Typography color="blue">{(1-restaurant!.discountCoefficient) * calculateTotal(orderItems!)}</Typography>}
            </Grid>
            <Grid item xs={12} sx={{ display: "flex", justifyContent: "space-evenly" }}>
              <Button color="success" variant="outlined" onClick={confirmDishes}><Typography>{t('shoppingCart.cartPanel.orderItems')}</Typography></Button>
              <Button color="error" variant="outlined" onClick={cancelDishes}><Typography>{t('shoppingCart.cartPanel.clearItems')}</Typography></Button>
            </Grid>
          </Grid>
        </Grid>
      )}
    </div>
  );
}

export default ShoppingCartPanel;