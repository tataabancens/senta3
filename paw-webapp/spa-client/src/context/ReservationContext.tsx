import { createContext } from "react";
import { DishCategoryModel, OrderItemModel, ReservationModel } from "../models";


interface ReservationContextProps{
    reservation: ReservationModel | undefined;
    updateReservation: () => void;
    orderItems: OrderItemModel[] | undefined;
    reloadItems: () => void;
    removeItem: (item: OrderItemModel) => void;
}
const emptyReservationContext: ReservationContextProps = {
    reservation: undefined,
    updateReservation: () => null,
    orderItems: undefined,
    reloadItems: () => null,
    removeItem: () => null
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