import { SystemSecurityUpdate } from "@mui/icons-material";

export const TRANSLATIONS_EN = {
    navBar: {
        customerPages:{
            reservations: "Reservations",
            profile: "Profile"
        },
        restaurantPages:{
            menu: "Menu",
            kitchen: "Kitchen",
            reservations: "Reservations"
        },
        loginButton: "Login",
        registerButton: "Register",
        logoutButton: "Logout"
    },
    restaurantHeader:{
        menuHeader: "Menu",
        makeReservation: "Make reservation",
        haveReservation: "Have reservation",
        myReservation: "My reservation",
        createCategory: "Create category"
    },
    loginPage: {
        loginTitle: "Login",
        usernameInput: "Username",
        formSubmit:{
            login: "Login",
            loading: "Loading"
        },
        forgotPassword: "Forgot password?",
        registerCallToAction: "Don't have an account? Sign Up!"
    },
    createReservation:{
        pageTitle: "Create reservation",
        step1:{
            stepTitle: "How many?",
            stepDescription: "How many people will come?"
        },
        step2:{
            stepTitle: "Date?",
            stepDescription: "Which day do you want to come?"
        },
        step3:{
            stepTitle: "Hour?",
            stepDescription: "At what time do you wish to attend? sittings last 1 hour.",
            selectLabel: "Select one"
        },
        step4:{
            stepTitle: "Contact info",
            stepDescription: "We need some info to confirm your reservation"
        },
        step5:{
            stepTitle: "Done!",
            stepDescription: "You made a reservation on Atuel, for {{date}} at {{hour}}hs for {{qPeople}} people. Your reservation code is: {{secCode}}"
        },
        next: "Next",
        back: "Back",
        makeReservaton: "Place reservation",
        continueWithoutSigning: "Continue without signing up",
        signUp: "Sign up",
        goToReservation: "Continue to reservation",

    },
    reservationData:{
        title: "My reservation",
        customer: "Customer: {{customer}}",
        code: "Code: {{code}}",
        date: "Date: ",
        hour: "Hour: {{hour}}:00",
        table: "Table: {{table}}"
    },
    shoppingCart:{
        cartPanel:{
            title: "Shopping Cart",
            recommendationTitle: "Other people also ordered:",
            noRecommendation: "We can't recommend anything",
            orderItems: "Order items",
            clearItems: "Clear items"
        },
        ordersPanel:{
            title: "Orders",
            disclaimer: "Your orders status will be seen when you arrive at the restaurants",
            orderedStatus: "Ordered",
            cookingStatus: "Cooking",
            deliveringStatus: "Delivering"
        },
        checkPanel:{
            title: "Summary",
            orderCheck: "Order Check"
        },
        tableHeaders:{
            dish: "Dish",
            qty: "Qty",
            subtotal: "Subtotal",
            status: "Status"
        }
    },
    restaurantMenu:{
        editCategory: "Edit category",
        deleteCategory: "Delete category"
    },
    dishDisplay:{
        createDish: "Create dish"
    },
    kitchenPage:{
        orderedTitle: "Ordered",
        cookingTitle: "Cooking",
        deliveredTitle: "Delivered",
        kitchenHeaders:{
            dish: "Dish",
            qty: "Quantity",
            table: "Table"
        },
        cookAction: "Cook",
        deliverAction: "Deliver",
        doneAction: "Done",
        emptyTable: "There are no orders here"

    },
    customerReservations:{
        points: "Points: {{points}}",
        activeReservationsTitle: "Active Reservations",
        oldReservationsTitle: "Reservation history",
        reservationCard:{
            date: "date: ",
            hour: "hour: {{hour}}:00",
            code: "code: {{code}}",
            people: "people: {{people}}"
        },
        enterButton: "Enter"
    },
    registerPage:{
        registerTitle: "Sign up",
        registerButton: "sign up",
        loginCallToAction: "Already have an account? login!",
    },
    profilePage: {
        title: "Profile",
        accountInfo:{
            title: "Account info:",
            restaurantName: "Restaurant name: {{name}}",
            username: "Username: {{username}}",
            mail: "Mail: {{mail}}",
            phone: "Phone: {{phone}}",
            clientName: "Name: {{customer}}",
            points: "Points: {{points}}"
        },
        restaurantInfo:{
            title: "Restaurant info:",
            chairs: "Chairs: {{chairs}}",
            openHours: "Open hour: {{hour}}:00",
            closeHours: "Close hour: {{hour}}:00"
        },
        editButton: "Edit"
    },
    systemError: "There was an error in the system. Try again later."
}