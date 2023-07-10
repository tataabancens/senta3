import { createContext } from "react";
import { CustomerModel, DishCategoryModel, OrderItemModel, ReservationModel, RestaurantModel } from "../models";
import PointsModel from "../models/Customers/PointsModel";


interface ReservationContextProps{
    reservation: ReservationModel | undefined;
    updateReservation: () => void;
    orderItems: OrderItemModel[] | undefined;
    reloadItems: () => void;
    removeItem: (item: OrderItemModel) => void;
    discount: boolean;
    toggleDiscount: () => void;
    customer: CustomerModel | undefined;
    points: PointsModel | undefined;
    restaurant: RestaurantModel | undefined;
}
const emptyReservationContext: ReservationContextProps = {
    reservation: undefined,
    updateReservation: () => null,
    orderItems: undefined,
    reloadItems: () => null,
    removeItem: () => null,
    discount: false,
    toggleDiscount: () => null,
    customer: undefined,
    points: undefined,
    restaurant: undefined
}
export const ReservationContext = createContext<ReservationContextProps>(emptyReservationContext);

interface CategoryContextProps{
    value: number;
    categoryList: DishCategoryModel[] | undefined;
    categoryMap: Map<number, DishCategoryModel> | undefined;
}
const defaultCategoryContext = {
    value: 0,
    categoryList: undefined,
    categoryMap: undefined,
  };

export const CategoryContext = createContext<CategoryContextProps>(defaultCategoryContext);