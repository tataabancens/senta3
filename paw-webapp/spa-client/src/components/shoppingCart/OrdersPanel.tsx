import { Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { FC, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { OrderItemModel } from "../../models";
import OrdersItem from "./OrdersItem";

type TabPanelProps = {
    children?: React.ReactNode;
    index: number;
    value: number;
    orderItems: OrderItemModel[];
}

const OrdersPanel:FC<TabPanelProps> = (props: TabPanelProps) => {
    const { children, value, index, orderItems, ...other } = props;
    const { t } = useTranslation();
    const itemsToDisplay = orderItems.filter(orderItem => (orderItem.status !== "SELECTED" && orderItem.status !== "DELETED"));

    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        {...other}
      >
        {value === index && (
          <Grid container xs={12}>
            {itemsToDisplay && itemsToDisplay.length > 0 &&
            <Grid item xs={12} component={Table}>
              <TableHead>
                <TableRow>
                  <TableCell align="left">{t('shoppingCart.tableHeaders.dish')}</TableCell>
                  <TableCell align="center">{t('shoppingCart.tableHeaders.qty')}</TableCell>
                  <TableCell align="center">{t('shoppingCart.tableHeaders.status')}</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {itemsToDisplay.map(item => 
                  <OrdersItem orderItem={item} />
                )}
              </TableBody>
            </Grid>}
            {itemsToDisplay && itemsToDisplay.length === 0 && <Grid item xs={12} marginTop={20}><Typography variant="h5" align="center">{t('shoppingCart.ordersPanel.noDishes')}</Typography></Grid>}
          </Grid>
        )}
      </div>
    );
}

export default OrdersPanel;