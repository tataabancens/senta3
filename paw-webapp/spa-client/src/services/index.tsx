import { DishService } from "./DishService";
import { UserService } from "./UserService";
import { CustomerService } from "./CustomerService";
import { RestaurantService } from "./RestaurantService";
import { ReservationService } from "./ReservationService";
import { OrderItemService } from "./OrderItemService";
import { ImageService } from "./ImageService";

const dishService = new DishService();
const userService =  new UserService();
const customerService = new CustomerService();
const restaurantService = new RestaurantService();
const reservationService = new ReservationService();
const orderItemService = new OrderItemService();
const imageService = new ImageService();

export {
    dishService,
    userService,
    restaurantService,
    customerService,
    reservationService,
    orderItemService,
    imageService
};