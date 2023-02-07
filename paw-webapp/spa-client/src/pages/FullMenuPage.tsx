import { useEffect, useState } from "react";
import { handleResponse } from "../handleResponse";
import { DishCategoryModel, DishModel, OrderItemModel, ReservationModel, RestaurantModel } from "../models";
import { Grid } from "@mui/material";
import CategoryTabs from "../components/CategoryTabs";
import RestaurantHeader from "../components/RestaurantHeader";
import DishDisplay from "../components/DishDisplay";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import { OrderitemParams } from "../models/OrderItems/OrderitemParams";
import useDishService from "../hooks/serviceHooks/useDishService";
import useOrderItemService from "../hooks/serviceHooks/useOrderItemService";
import useReservationService from "../hooks/serviceHooks/useReservationService";
import useRestaurantService from "../hooks/serviceHooks/useRestaurantService";
import { useParams } from "react-router-dom";

function FullMenuPage() {

    const [value, setValue] = useState(0);
    const [restaurant, setRestaurant] = useState<RestaurantModel>();
    const [dishList, setDishes] = useState<DishModel[]>([]);
    const [customerReservation, setReservation] = useState<ReservationModel>();
    const [ orderItems, setOrderItems ] = useState<OrderItemModel[]>([]);
    const [categoryList, setCategories] = useState<DishCategoryModel[]>([]);

    const dishService = useDishService();
    const orderItemService = useOrderItemService();
    const reservationService = useReservationService();
    const restaurantService = useRestaurantService();

    const { securityCode } = useParams();

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

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
            }
        );
    }, []);

    useEffect(() => {
        handleResponse(
            dishService.getDishes(categoryList[value]?.name),
            (dishes: DishModel[]) => {
            dishes.length > 0 ? setDishes(dishes) : setDishes([]);
        }
        );
    }, [value]);

    useEffect(() => {
        let reservation = new ReservationParams();
        reservation.securityCode = securityCode;
        handleResponse(
            reservationService.getReservation(reservation),
            (reservation: ReservationModel) => setReservation(reservation)
        );
    },[])

    useEffect(() => {
        let orderItems = new OrderitemParams();
        orderItems.securityCode = securityCode;
        handleResponse(
            orderItemService.getOrderItems(orderItems),
            (orderItems: OrderItemModel[]) => {
                setOrderItems(orderItems);
            }
        )
    },[])

    return(
        <Grid container spacing={2} justifyContent="center">
            <RestaurantHeader restaurant={restaurant} reservation={customerReservation} orderItems={orderItems} role={"ROLE_CUSTOMER"}/>
            <CategoryTabs value={value} handleChange={handleChange} categoryList={categoryList}  />
            <DishDisplay dishList={dishList} role={"ROLE_CUSTOMER"}/>
        </Grid>
    );
}

export default FullMenuPage;