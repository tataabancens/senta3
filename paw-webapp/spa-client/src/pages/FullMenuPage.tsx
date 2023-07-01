import { FC, useEffect, useState } from "react";
import { handleResponse } from "../Utils";
import { DishCategoryModel, OrderItemModel, ReservationModel } from "../models";
import { Grid, Tab, Tabs } from "@mui/material";
import RestaurantHeader from "../components/RestaurantHeader";
import DishDisplay from "../components/DishDisplay";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import useOrderItemService from "../hooks/serviceHooks/orderItems/useOrderItemService";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import {useNavigate, useParams} from "react-router-dom";
import { ReservationContext } from "../context/ReservationContext";
import { useDishes } from "../hooks/serviceHooks/dishes/useDishes";
import { useRestaurant } from "../hooks/serviceHooks/restaurants/useRestaurant";
import { useDishCategories } from "../hooks/serviceHooks/dishes/useDishCategories";
import { paths } from "../constants/constants";
import { useReservation } from "../hooks/serviceHooks/reservations/useReservation";
import { useOrderItems } from "../hooks/serviceHooks/reservations/useOrderItems";
import { useOrderItemsBySecCode } from "../hooks/serviceHooks/reservations/useOrderItemsBySecCode";

const FullMenuPage: FC = () => {

    const [value, setValue] = useState(0);
    // const [orderItems, setOrderItems] = useState<OrderItemModel[]>([]);
    const [reloadOrderItems, setReload] = useState(false);

    const orderItemService = useOrderItemService();

    const { securityCode } = useParams();

    let navigate = useNavigate();

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    const updateOrderItems = () => {
        setReload(!reloadOrderItems);
    }


    const { restaurant, error: restaurantError, loading: restaurantLoading } = useRestaurant(1);

    const { categoryList, categoryMap, error: dishCategoriesError, loading: dishCategoriesLoading } = useDishCategories(restaurant);

    const { dishes = [], error: dishesError, loading: dishesLoading } = useDishes(value, categoryMap?.get(value));

    useEffect(() => {
      if (categoryList && categoryList.length > 0) setValue(categoryList[0].id)
    }, categoryList);

    const { reservation, loading: reservationLoading, error: reservationError, updateReservation } = useReservation(securityCode!);

    if(reservation?.status === "CANCELED" || reservation?.status === "FINISHED"){
        navigate(`${paths.ROOT}/reservations/${reservation.securityCode}/checkout`);
    }

    const {orderItems, error: orderItemsError, loading: orderItemLoading} = useOrderItemsBySecCode(reservation, reloadOrderItems);

    return(
        <Grid container spacing={2} justifyContent="center">
            <ReservationContext.Provider value={{reservation, updateReservation, orderItems, updateOrderItems}}>
                <RestaurantHeader restaurant={restaurant} role={"ROLE_CUSTOMER"} toggleReload={updateOrderItems}/>
                <Grid item xs={11} marginTop={2}>
                    <Tabs value={value} onChange={(event,value) => handleChange(event, value)} variant="scrollable" scrollButtons="auto" aria-label="scrollable auto tabs example">
                    {categoryList?.map((category: DishCategoryModel) => (<Tab key={category.id} value={category.id} label={category.name} />))}
                    </Tabs>
                </Grid>
                <DishDisplay toggleReload={updateOrderItems} dishes={dishes} isMenu={false}/>
            </ReservationContext.Provider>
        </Grid>
    );
}

export default FullMenuPage;