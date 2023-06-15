import { useEffect, useState } from "react";
import { handleResponse } from "../Utils";
import { DishCategoryModel, DishModel, OrderItemModel, ReservationModel, RestaurantModel } from "../models";
import { Grid, Tab, Tabs } from "@mui/material";
import RestaurantHeader from "../components/RestaurantHeader";
import DishDisplay from "../components/DishDisplay";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import useDishService from "../hooks/serviceHooks/dishes/useDishService";
import useOrderItemService from "../hooks/serviceHooks/orderItems/useOrderItemService";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import useRestaurantService from "../hooks/serviceHooks/restaurants/useRestaurantService";
import {useNavigate, useParams} from "react-router-dom";
import { ReservationContext } from "../context/ReservationContext";
import { useDishes } from "../hooks/serviceHooks/dishes/useDishes";
import { useRestaurant } from "../hooks/serviceHooks/restaurants/useRestaurant";
import { useDishCategories } from "../hooks/serviceHooks/dishes/useDishCategories";
import { paths } from "../constants/constants";

function FullMenuPage() {

    const [value, setValue] = useState(0);
    const [reservation, setReservation] = useState<ReservationModel>();
    const [orderItems, setOrderItems] = useState<OrderItemModel[]>([]);
    const [reloadOrderItems, setReload] = useState(false);
    const [reloadReservation, setReloadReservation] = useState(false);

    const dishService = useDishService();
    const orderItemService = useOrderItemService();
    const reservationService = useReservationService();
    const restaurantService = useRestaurantService();

    const { securityCode } = useParams();

    let navigate = useNavigate();

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    const toggleReloadOrderItems = () => {
        setReload(!reloadOrderItems);
    }

    const updateReservation = () => {
        setReloadReservation(!reloadReservation);
    }

    const { restaurant, error: restaurantError, loading: restaurantLoading } = useRestaurant(1);

    const { categoryList, categoryMap, error: dishCategoriesError, loading: dishCategoriesLoading } = useDishCategories(restaurant)

    useEffect(() => {
      if (categoryList && categoryList.length > 0) setValue(categoryList[0].id)
    }, categoryList);
  
    const { dishes = [], error: dishesError, loading: dishesLoading } = useDishes(value, categoryMap?.get(value));

    // Volaran en breve xD
    useEffect(() => {
        let resParams = new ReservationParams();
        resParams.securityCode = securityCode;
        handleResponse(
            reservationService.getReservation(resParams),
            (reservation: ReservationModel) => setReservation(reservation)
        );
    }, [reloadReservation])

    if(reservation?.status === "CANCELED" || reservation?.status === "FINISHED"){
        navigate(`${paths.ROOT}/reservations/${reservation.securityCode}/checkout`);
    }

    useEffect(() => {
        let orderItems = new OrderitemParams();
        orderItems.securityCode = securityCode;
        handleResponse(
            orderItemService.getOrderItems(orderItems),
            (orderItems: OrderItemModel[]) => {
                setOrderItems(orderItems);
            }
        )
    },[reloadOrderItems]);

    return(
        <Grid container spacing={2} justifyContent="center">
            <ReservationContext.Provider value={{reservation, updateReservation}}>
                <RestaurantHeader restaurant={restaurant} orderItems={orderItems} role={"ROLE_CUSTOMER"} toggleReload={toggleReloadOrderItems}/>
                <Grid item xs={11} marginTop={2}>
                    <Tabs value={value} onChange={(event,value) => handleChange(event, value)} variant="scrollable" scrollButtons="auto" aria-label="scrollable auto tabs example">
                    {categoryList?.map((category: DishCategoryModel) => (<Tab key={category.id} value={category.id} label={category.name} />))}
                    </Tabs>
                </Grid>
                <DishDisplay dishList={dishes} role={"ROLE_CUSTOMER"} reservation={reservation} toggleReload={toggleReloadOrderItems}/>
            </ReservationContext.Provider>
        </Grid>
    );
}

export default FullMenuPage;