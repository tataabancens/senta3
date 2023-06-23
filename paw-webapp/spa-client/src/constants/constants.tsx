//todo deploy
// BASE_URL : 'http://localhost:8080' or 'http://pawserver.it.itba.edu.ar/paw-2022a-05/api'

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
    self: ''
}

export const initCustomer: CustomerModel = {
    id: 0,
    name: '',
    phone: '',
    mail: '',
    points: 0,
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

export const steps = ['How many?', 'Date?', 'Hour?', 'Contact info', 'Done!'];

export const shoppingCartTabs = ["Shopping Cart", "Orders", "Summary"];