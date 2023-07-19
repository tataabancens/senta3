import { FC, useEffect, useState } from "react";
import { DishCategoryModel } from "../models";
import { CircularProgress, Grid, Tab, Tabs } from "@mui/material";
import RestaurantHeader from "../components/RestaurantHeader";
import DishDisplay from "../components/DishDisplay";
import {useNavigate, useParams} from "react-router-dom";
import { ReservationContext } from "../context/ReservationContext";
import { useDishes } from "../hooks/serviceHooks/dishes/useDishes";
import { useRestaurant } from "../hooks/serviceHooks/restaurants/useRestaurant";
import { useDishCategories } from "../hooks/serviceHooks/dishes/useDishCategories";
import { paths, RESERVATION_INTERVAL } from "../constants/constants";
import { useReservation } from "../hooks/serviceHooks/reservations/useReservation";
import { useOrderItemsBySecCode } from "../hooks/serviceHooks/orderItems/useOrderItemsBySecCode";
import { UserRoles } from "../models/Enums/UserRoles";
import { useCustomer } from "../hooks/serviceHooks/customers/useCustomer";
import { ReservationParams } from "../models/Reservations/ReservationParams";
import useReservationService from "../hooks/serviceHooks/reservations/useReservationService";
import useServices from "../hooks/useServices";

const FullMenuPage: FC = () => {

    const [value, setValue] = useState(0);
    const [reloadOrderItems, setReload] = useState(false);
    const [discount, setDiscount] = useState(false);
    const { reservationService: rs } = useServices();

    const { securityCode } = useParams();

    let navigate = useNavigate();

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    const updateOrderItems = () => {
        setReload(!reloadOrderItems);
    }

    const toggleDiscount = () => {
        setDiscount(!discount);
        let resParams = new ReservationParams();
        resParams.securityCode = reservation!.securityCode;
        resParams.discount = true;
        rs.patchReservation(resParams);
    }


    const { restaurant } = useRestaurant(1);
    const { categoryList, categoryMap, loading: dishCategoriesLoading } = useDishCategories(restaurant);
    const { reservation, updateReservation } = useReservation(securityCode!, RESERVATION_INTERVAL);
    const { customer, points } = useCustomer(reservation?.customer);
    const { dishes, loading: dishesLoading } = useDishes(value, categoryMap?.get(value));
    const { orderItems, reloadItems, updateItem } = useOrderItemsBySecCode(reservation);

    useEffect(() => {
      if (categoryList && categoryList.length > 0) setValue(categoryList[0].id)
    }, [categoryList]);

    if(reservation?.status === "CANCELED" || reservation?.status === "FINISHED"){
        navigate(`${paths.ROOT}/reservations/${reservation!.securityCode}/checkout`);
    }


    return(
        <Grid container spacing={2} justifyContent="center">
            <ReservationContext.Provider value={{reservation, updateReservation, orderItems, reloadItems, discount, toggleDiscount, restaurant, customer, points, updateItem}}>
                <RestaurantHeader role={UserRoles.CUSTOMER} toggleReload={updateOrderItems}/>
                <Grid item xs={11} marginTop={2}>  
                {categoryList &&
                    <Tabs value={value} onChange={(event,value) => handleChange(event, value)} variant="scrollable" scrollButtons="auto" aria-label="scrollable auto tabs example">
                    {categoryList?.map((category: DishCategoryModel) => (<Tab key={category.id} value={category.id} label={category.name} />))}
                    </Tabs>}
                </Grid>
                {dishes && <DishDisplay dishes={dishes} isMenu={false}/>}
            </ReservationContext.Provider>
            {dishCategoriesLoading && dishesLoading &&
                <div style={{position: "absolute", top:"50%", right:"50%"}}><CircularProgress /></div>
            }
        </Grid>
    );
}

export default FullMenuPage;