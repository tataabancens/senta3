import { Grid, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { FC } from "react";
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
  
    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        {...other}
      >
        {value === index && (
          <Grid container xs={12}>
            <Grid item xs={12} component={Table}>
              <TableHead>
                <TableRow>
                  <TableCell align="left">Dish</TableCell>
                  <TableCell align="center">Qty</TableCell>
                  <TableCell align="center">Status</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {orderItems.filter(orderItem => (orderItem.status !== "SELECTED" && orderItem.status !== "CANCELED")).map(filteredItem => 
                  <OrdersItem orderItem={filteredItem} />
                )}
              </TableBody>
            </Grid>
          </Grid>
        )}
      </div>
    );
}

export default OrdersPanel;