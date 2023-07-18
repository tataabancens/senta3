//todo deploy
// BASE_URL : 'http://localhost:8080' or 'http://pawserver.it.itba.edu.ar/paw-2022a-05/api'

import { useTranslation } from "react-i18next";
import { CustomerModel, RestaurantModel } from "../models";

// ROOT : '/' or /paw-2022a-05/
export const paths = {
    ROOT : '/paw-2022a-05',
    BASE_URL : 'http://localhost:8080/api',
    RESTAURANTS: '/restaurants',
    USERS: '/users',
    DISH_CATEGORIES: '/dishCategories',
    DISHES: '/dishes',
    CUSTOMERS: '/customers',
    IMAGES: '/resources/images',
    ORDERITEMS: '/orderItems',
    RESERVATIONS: '/reservations',
}

export const userRoles = {
    CUSTOMER: 1,
    KITCHEN: 2,
    WAITER: 3,
    RESTAURANT: 4
}

export const emptyAuth = {
    user: "",
    authorization: "",
    roles: [],
    id: -1,
    content: undefined,
    contentURL: ""
}

export const initRestaurant: RestaurantModel = {
    id: 1,
    name: '',
    phone: '',
    mail: '',
    dishes: '',
    reservations: '',
    dishCategories: '',
    user: '',
    totalChairs: 0,
    openHour: 0,
    closeHour: 0,
    self: '',
    pointsForDiscount: 0,
    discountCoefficient: 1,
    pointsPerItem: 0
}

export const initCustomer: CustomerModel = {
    id: 0,
    name: '',
    phone: '',
    mail: '',
    points: '',
    user: '',
    reservations: '',
    self: '',
}

export const linkStyle = {
    width: "100%",
    height: 100,
    margin: "1%",
    transition: "0.8s",
    backgroundColor: "white",
    boxShadow: "0 1.4rem 8rem rgba(0,0,0,.35)",
    borderRadius: ".8rem",
    display: "flex"
}
export const timeArray = 
["0:00",
 "1:00",
 "2:00",
 "3:00",
 "4:00",
 "5:00",
 "6:00",
 "7:00",
 "8:00",
 "9:00",
 "10:00",
 "11:00",
 "12:00",
 "13:00",
 "14:00",
 "15:00",
 "16:00",
 "17:00",
 "18:00",
 "19:00",
 "20:00",
 "21:00",
 "22:00",
 "23:00"
]
export const ORDERITEMS_INTERVAL = 30000 //in milliseconds

export const RESERVATION_INTERVAL = 100000 //in milliseconds
