import { Box, Grid, Typography } from "@mui/material";
import OrderItemCard from "../components/OrderItemCard";
import { themePalette } from "../config/theme.config";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { useOrderItems } from "../hooks/serviceHooks/reservations/useOrderItems";


const Kitchen = () => {
    let filterStatus = "1";
    let orderItemStatus = "1";
    const { orderItems: orderedOrderItems, error: orderedOrderItemsError, loading: orderedOrderItemsLoading } = useOrderItems(filterStatus, orderItemStatus)

    orderItemStatus = "2";
    const { orderItems: incomingOrderItems, error: incomingOrderItemsError, loading: incomingOrderItemsLoading } = useOrderItems(filterStatus, orderItemStatus)

    orderItemStatus = "3";
    const { orderItems: deliveringOrderItems, error: deliveringOrderItemsError, loading: deliveringOrderItemsLoading } = useOrderItems(filterStatus, orderItemStatus)

    return (
        <Grid container xs={12}>
            <Grid item container component={Box} sx={{ background: themePalette.PURPLE }} marginY={3} xs={4}>
                <Grid item xs={12}><Typography>Ordered</Typography></Grid>
                <Grid item xs={10}>
                    {orderedOrderItems.map(orderItem => <OrderItemCard orderItem={orderItem} />)}
                </Grid>
            </Grid>
            <Grid item container component={Box} sx={{ background: themePalette.RED }} marginY={3} xs={4}>
                <Grid item xs={12}><Typography>Ordered</Typography></Grid>
                <Grid item xs={10}>
                    {incomingOrderItems.map(orderItem => <OrderItemCard orderItem={orderItem} />)}
                </Grid>
            </Grid>
            <Grid item container component={Box} sx={{ background: themePalette.BLUE }} marginY={3} xs={4}>
                <Grid item xs={12}><Typography>Delivering</Typography></Grid>
                <Grid item xs={10}>
                    {deliveringOrderItems.map(orderItem => <OrderItemCard orderItem={orderItem} />)}
                </Grid>
            </Grid>
        </Grid>
    );
}

export default Kitchen