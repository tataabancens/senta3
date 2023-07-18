import { CircularProgress, Grid, Paper, Typography } from "@mui/material";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import HandsTable from "../components/HandsTable";
import KitchenTable from "../components/KitchenTable";
import useOrderItemService from "../hooks/serviceHooks/orderItems/useOrderItemService";
import { useOrderItems } from "../hooks/serviceHooks/orderItems/useOrderItems";
import useAuth from "../hooks/serviceHooks/authentication/useAuth";
import { OrderItemModel } from "../models";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import { UserRoles } from "../models/Enums/UserRoles";
  

const Kitchen: FC = () => {
    let filterStatus = "1";
    let orderItemStatus = "1";
    const { t } = useTranslation();
    const orderItemService = useOrderItemService();
    const { auth } = useAuth();
    const { orderItems: orderedOrderItems,
            error: orderedOrderItemsError,
            loading: orderedOrderItemsLoading,
            removeItem:removeOrderedItem } = useOrderItems(filterStatus, orderItemStatus,10000);

    orderItemStatus = "2";
    const { orderItems: incomingOrderItems,
            error: incomingOrderItemsError, 
            loading: incomingOrderItemsLoading,
            removeItem: removeIncomingItem,
            addItem: addIncomingItem } = useOrderItems(filterStatus, orderItemStatus);

    orderItemStatus = "3";
    const { orderItems: deliveringOrderItems, 
            error: deliveringOrderItemsError, 
            loading: deliveringOrderItemsLoading,
            removeItem: removeDeliveredItem,
            addItem: addDeliveredItem } = useOrderItems(filterStatus, orderItemStatus);

    const cookItem = async (item: OrderItemModel) => {
        let params = new OrderitemParams();
        params.orderItemId = item.orderItemId;
        params.status = "INCOMING";
        params.securityCode = item.securityCode;
        const { isOk } = await orderItemService.editOrderItem(params, new AbortController());
        if(isOk){
            removeOrderedItem(item);
            addIncomingItem(item);
        }
    }

    const deliverItem = async (item: OrderItemModel) => {
        let params = new OrderitemParams();
        params.orderItemId = item.orderItemId;
        params.status = "DELIVERED";
        params.securityCode = item.securityCode;
        const { isOk } = await orderItemService.editOrderItem(params, new AbortController());
        if(isOk){
            removeIncomingItem(item)
            addDeliveredItem(item)
        }
    }

    const itemDelivered = async (item: OrderItemModel) => {
        let params = new OrderitemParams();
        params.orderItemId = item.orderItemId;
        params.status = "FINISHED";
        params.securityCode = item.securityCode;
        const { isOk } = await orderItemService.editOrderItem(params, new AbortController());
        if(isOk){
            removeDeliveredItem(item);
        }
    }

    return (
        <>
        {(auth.roles[0] === UserRoles.KITCHEN || auth.roles[0] === UserRoles.RESTAURANT) && 
        <Grid container justifyContent="space-evenly">
            <Grid item container xs={12} sm={9} lg={3.8} md={6} component={Paper} elevation={5} marginX={1} marginY={2}>
                <Grid item xs={12}><Typography variant="h4" align="center">{t('kitchenPage.orderedTitle')}</Typography></Grid>
                {orderedOrderItems  && !orderedOrderItemsError && <KitchenTable orderItems={orderedOrderItems} actionFunction={cookItem} processStage={"ORDERED"}/>}
                {orderedOrderItemsLoading  && <Grid xs={12} sx={{display:"flex"}} minHeight={300} justifyContent="center" alignItems="center"><CircularProgress /></Grid>}
                {orderedOrderItemsError && <Grid xs={12} marginY={20}><Typography align="center">{t('systemError')}</Typography></Grid>}
            </Grid>
            <Grid item container xs={12} sm={9} lg={3.8} md={6} component={Paper} elevation={5} marginX={1} marginY={2}>
                <Grid xs={12}><Typography variant="h4" align="center">{t('kitchenPage.cookingTitle')}</Typography></Grid>
                {incomingOrderItems && !incomingOrderItemsError && <KitchenTable orderItems={incomingOrderItems} actionFunction={deliverItem} processStage={"INCOMING"}/>}
                {incomingOrderItemsLoading  && <Grid xs={12} sx={{display:"flex"}} minHeight={300} justifyContent="center" alignItems="center"><CircularProgress /></Grid>}
                {incomingOrderItemsError && <Grid xs={12} marginY={20}><Typography align="center">{t('systemError')}</Typography></Grid>}
            </Grid>
            <Grid item container xs={12} sm={9} lg={3.8} md={6} component={Paper} elevation={5} marginX={1} marginY={2}>
                <Grid xs={12}><Typography variant="h4" align="center">{t('kitchenPage.deliveredTitle')}</Typography></Grid>
                {deliveringOrderItems && !deliveringOrderItemsError && <KitchenTable orderItems={deliveringOrderItems} actionFunction={itemDelivered} processStage={"DELIVERING"}/>}
                {deliveringOrderItemsLoading  && <Grid xs={12} sx={{display:"flex"}} minHeight={300} justifyContent="center" alignItems="center"><CircularProgress /></Grid>}
                {deliveringOrderItemsError && <Grid xs={12} marginY={20}><Typography align="center">{t('systemError')}</Typography></Grid>}
            </Grid>
        </Grid>}
         {auth.roles[0] === UserRoles.WAITER &&
            <Grid container padding={2} justifyContent="space-evenly" height="90vh">
                <Grid item container xs={4} component={Paper} elevation={5}>
                    <Grid xs={12}><Typography variant="h4" align="center">{t('kitchenPage.deliveredTitle')}</Typography></Grid>
                    {deliveringOrderItems && !deliveringOrderItemsError && <KitchenTable orderItems={deliveringOrderItems} actionFunction={itemDelivered} processStage={"DELIVERING"}/>}
                    {deliveringOrderItemsLoading  && <Grid xs={12} sx={{display:"flex"}} minHeight={300} justifyContent="center" alignItems="center"><CircularProgress /></Grid>}
                    {deliveringOrderItemsError && <Grid xs={12} marginY={20}><Typography align="center">{t('systemError')}</Typography></Grid>}
                </Grid>
                <Grid item container xs={4} component={Paper} elevation={5}>
                    <Grid xs={12}><Typography variant="h4" align="center">{t('kitchenPage.handsTitle')}</Typography></Grid>
                    <HandsTable />
                </Grid>
            </Grid>
        }
        </>
    );
    
}

export default Kitchen;