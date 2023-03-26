//todo deploy
// LOCAL_BASE_URL : 'http://localhost:8080' or 'http://pawserver.it.itba.edu.ar/paw-2022a-05'
// ROOT : '/' or /paw-2022a-05/
export const paths = {
    ROOT : '/paw-2022a-05',
    BASE_URL : 'http://localhost:8080/api',
    LOCAL_BASE_URL : 'http://localhost:8080',
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
}