import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { AuthenticationService } from "../services/auth/AuthenticationService";
import { createContext, useState, useEffect } from "react";
import { DishService } from "../services/dish/DishService";
import axios from "../api/axios";
import { RestaurantService } from "../services/RestaurantService";
import { CustomerService } from "../services/customer/CustomerService";
import { OrderItemService } from "../services/OrderItemService";
import { ReservationService } from "../services/ReservationService";
import { UserService } from "../services/UserService";
import useAuth from "../hooks/serviceHooks/authentication/useAuth";

export interface ServiceContext {
    authenticationService: AuthenticationService;
    dishService: DishService;
    restaurantService: RestaurantService;
    customerService: CustomerService;
    orderItemService: OrderItemService;
    reservationService: ReservationService;
    userService: UserService;
}

const serviceContext: ServiceContext = {
    authenticationService: new AuthenticationService,
    dishService: new DishService(axios),
    restaurantService: new RestaurantService(axios),
    customerService: new CustomerService(axios),
    orderItemService: new OrderItemService(axios),
    reservationService: new ReservationService(axios),
    userService: new UserService(axios),
}

const ServiceContext = createContext(serviceContext);

interface Props {
    children: React.ReactNode;
}

export const useServiceProvider = () => {
    const { axiosPrivate } = useAuth();
    // const axiosPrivate = useAxiosPrivate();
    const authenticationService = new AuthenticationService();
    const dishService = new DishService(axiosPrivate);
    const restaurantService = new RestaurantService(axiosPrivate);
    const customerService = new CustomerService(axiosPrivate);
    const orderItemService = new OrderItemService(axiosPrivate);
    const reservationService = new ReservationService(axiosPrivate);
    const userService = new UserService(axiosPrivate);

    return {
        customerService,
        dishService,
        restaurantService,
        orderItemService,
        reservationService,
        userService,
        authenticationService
    }
    
}

export default useServiceProvider