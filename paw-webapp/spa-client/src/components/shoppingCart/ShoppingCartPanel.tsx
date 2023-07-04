import { Button, CircularProgress, Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { FC, useContext, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { ReservationContext } from "../../context/ReservationContext";
import { useRecommendedDish } from "../../hooks/serviceHooks/dishes/useRecommendedDish";
import useOrderItemService from "../../hooks/serviceHooks/orderItems/useOrderItemService";
import useReservationService from "../../hooks/serviceHooks/reservations/useReservationService";
import { DishModel, OrderItemModel } from "../../models";
import { OrderitemParams } from "../../models/OrderItems/OrderitemParams";
import { handleResponse } from "../../Utils";
import DishCard from "../dishCard/DishCard";
import ShoppingCartItem from "./ShoppingCartItem";

type TabPanelProps = {
  children?: React.ReactNode;
  index: number;
  value: number;
}

const ShoppingCartPanel: FC<TabPanelProps> = (props: TabPanelProps) => {
  const { children, value, index } = props;

  const orderItemService = useOrderItemService();
  const { reservation, updateReservation, orderItems, removeItem } = useContext(ReservationContext);
  const abortController = new AbortController();
  const { t } = useTranslation();

  const {recomendedDish, error: systemError, loading: loadingRecomendation, noDish} = useRecommendedDish(reservation!, orderItems!, updateReservation)

  const calculateTotal = (orderItems: OrderItemModel[]) => {
    let total = 0;
    orderItems.filter(orderItem => orderItem.status === "SELECTED").map(filteredItem => { total += filteredItem.quantity * filteredItem.unitPrice })

    return total;
  }

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
    >
      {value === index && (
        <Grid item container xs={12} sx={{ display: "flex", justifyContent: "space-between" }}>
          <Grid item xs={6} component={Table}>
            <TableHead>
              <TableRow>
                <TableCell align="left">{t('shoppingCart.tableHeaders.dish')}</TableCell>
                <TableCell>{t('shoppingCart.tableHeaders.qty')}</TableCell>
                <TableCell>{t('shoppingCart.tableHeaders.subtotal')}</TableCell>
                <TableCell></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {orderItems?.filter(orderItem => orderItem.status === "SELECTED").map((filteredItem) => (
                <ShoppingCartItem key={filteredItem.orderItemId} securityCode={reservation!.securityCode} orderItem={filteredItem} isCartItem={true} />
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
            <Grid item xs={12} sx={{ display: "flex", justifyContent: "space-evenly" }} marginY={2}>
              <Typography>Total:</Typography>
              <Typography>${calculateTotal(orderItems!)}</Typography>
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