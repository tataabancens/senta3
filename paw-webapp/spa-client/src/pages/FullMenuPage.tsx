import { useEffect, useState } from "react";
import { handleResponse } from "../Utils";
import { DishCategoryModel, DishModel, OrderItemModel, ReservationModel, RestaurantModel } from "../models";
import { Grid, Tab, Tabs } from "@mui/material";
import RestaurantHeader from "../components/RestaurantHeader";
import DishDisplay from "../components/DishDisplay";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import useDishService from "../hooks/serviceHooks/useDishService";
import useOrderItemService from "../hooks/serviceHooks/useOrderItemService";
import useReservationService from "../hooks/serviceHooks/useReservationService";
import useRestaurantService from "../hooks/serviceHooks/useRestaurantService";
import {useNavigate, useParams} from "react-router-dom";

function FullMenuPage() {

    const [value, setValue] = useState(0);
    const [restaurant, setRestaurant] = useState<RestaurantModel>();
    const [dishList, setDishes] = useState<DishModel[]>([]);
    const [customerReservation, setReservation] = useState<ReservationModel>();
    const [orderItems, setOrderItems] = useState<OrderItemModel[]>([]);
    const [categoryList, setCategories] = useState<DishCategoryModel[]>([]);
    const [categoryMap, setMap] = useState<Map<number,string>>();
    const [reloadOrderItems, setReload] = useState(false);

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

    useEffect(() => {
        handleResponse(
            restaurantService.getRestaurant(1),
            (restaurantData: RestaurantModel) => {
            setRestaurant(restaurantData);
            }
        );

        handleResponse(
            dishService.getDishCategories(),
            (categories: DishCategoryModel[]) => {
            setCategories(categories);
            setValue(categories[0].id);
            let myMap = new Map<number, string>();
            categories.forEach(category => myMap.set(category.id, category.name));
            setMap(myMap);

            handleResponse(
                dishService.getDishes(categories[0].name),
                (dishes: DishModel[]) => {
                    dishes.length > 0 ? setDishes(dishes) : setDishes([]);
                }
            );
        });

        let resParams = new ReservationParams();
        resParams.securityCode = securityCode;
        handleResponse(
            reservationService.getReservation(resParams),
            (reservation: ReservationModel) => setReservation(reservation)
        );
    }, []);
    if(customerReservation?.status == "CANCELED" || customerReservation?.status == "FINISHED"){
        navigate(`/reservations/${customerReservation.securityCode}/checkout`);
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

    useEffect(() => {
        handleResponse(
            dishService.getDishes(categoryMap?.get(value)),
            (dishes: DishModel[]) => {
              dishes.length > 0 ? setDishes(dishes) : setDishes([]);
            }
        );
    }, [value]);



    return(
        <Grid container spacing={2} justifyContent="center">
            <RestaurantHeader restaurant={restaurant} reservation={customerReservation} orderItems={orderItems} role={"ROLE_CUSTOMER"} toggleReload={toggleReloadOrderItems}/>
            <Grid item xs={11} marginTop={2}>
              <Tabs value={value} onChange={(event,value) => handleChange(event, value)} variant="scrollable" scrollButtons="auto" aria-label="scrollable auto tabs example">
                {categoryList?.map((category: DishCategoryModel) => (<Tab key={category.id} value={category.id} label={category.name} />))}
              </Tabs>
            </Grid>
            <DishDisplay dishList={dishList} role={"ROLE_CUSTOMER"} reservation={customerReservation} toggleReload={toggleReloadOrderItems}/>
        </Grid>
    );
}

export default FullMenuPage;