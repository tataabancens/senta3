import { CircularProgress, Grid, Paper, Typography } from "@mui/material";
import { FC } from "react";
import KitchenTable from "../components/KitchenTable";
import { useOrderItems } from "../hooks/serviceHooks/reservations/useOrderItems";
import { OrderItemModel } from "../models";


const Kitchen: FC = () => {
    let filterStatus = "1";
    let orderItemStatus = "1";
    const { orderItems: orderedOrderItems,
            error: orderedOrderItemsError,
            loading: orderedOrderItemsLoading,
            removeItem:removeOrderedItem } = useOrderItems(filterStatus, orderItemStatus)

    orderItemStatus = "2";
    const { orderItems: incomingOrderItems,
            error: incomingOrderItemsError, 
            loading: incomingOrderItemsLoading,
            removeItem: removeIncomingItem,
            addItem: addIncomingItem } = useOrderItems(filterStatus, orderItemStatus)

    orderItemStatus = "3";
    const { orderItems: deliveringOrderItems, 
            error: deliveringOrderItemsError, 
            loading: deliveringOrderItemsLoading,
            removeItem: removeDeliveredItem,
            addItem: addDeliveredItem } = useOrderItems(filterStatus, orderItemStatus)

    const cookItem = (item: OrderItemModel) => {
        removeOrderedItem(item);
        addIncomingItem(item);
    }

    const deliverItem = (item: OrderItemModel) => {
        removeIncomingItem(item)
        addDeliveredItem(item)
    }

    const itemDelivered = (item: OrderItemModel) => {
        removeDeliveredItem(item)
    }

    return (
        <Grid container xs={12} padding={2} justifyContent="space-evenly" >
            <Grid item container xs={3} component={Paper} elevation={5}>
                <Grid item xs={12}><Typography variant="h4" align="center">Ordered</Typography></Grid>
                {orderedOrderItems  && !orderedOrderItemsError && <KitchenTable orderItems={orderedOrderItems} actionFunction={cookItem} processStage={"ORDERED"}/>}
                {orderedOrderItemsLoading  && <CircularProgress />}
                {orderedOrderItemsError && <Grid xs={12} marginY={2}><Typography align="center">There was an error in the system. Try again later.</Typography></Grid>}
            </Grid>
            <Grid item container xs={3} component={Paper} elevation={5}>
                <Grid xs={12}><Typography variant="h4" align="center">Cooking</Typography></Grid>
                {incomingOrderItems && !incomingOrderItemsError && <KitchenTable orderItems={incomingOrderItems} actionFunction={deliverItem} processStage={"INCOMING"}/>}
                {incomingOrderItemsLoading  && <CircularProgress />}
                {incomingOrderItemsError && <Grid xs={12} marginY={2}><Typography align="center">There was an error in the system. Try again later.</Typography></Grid>}
            </Grid>
            <Grid item container xs={3} component={Paper} elevation={5}>
                <Grid xs={12}><Typography variant="h4" align="center">Delivered</Typography></Grid>
                {deliveringOrderItems && !deliveringOrderItemsError && <KitchenTable orderItems={deliveringOrderItems} actionFunction={itemDelivered} processStage={"DELIVERING"}/>}
                {deliveringOrderItemsLoading  && <CircularProgress />}
                {deliveringOrderItemsError && <Grid xs={12} marginY={2}><Typography align="center">There was an error in the system. Try again later.</Typography></Grid>}
            </Grid>
        </Grid>
    );
    
}

export default Kitchen;