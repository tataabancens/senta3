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
        callWaiter: "Call waiter",
        removeDiscountText: "Remove discount",
        applyDiscountText: "Apply discount",
        callingWaiter: "Calling waiter",
        createCategory: "Create category"
    },
    loginPage: {
        loginTitle: "Login",
        usernameInput: "Username",
        label:{
            username: "Username",
            password: "Password"
        },
        formSubmit:{
            login: "Login",
            loading: "Loading"
        },
        rememberMe: "Remember me",
        forgotPassword: "Forgot password?",
        registerCallToAction: "Don't have an account? Sign Up!"
    },
    validationSchema:{
        required: "Required",
        mailValidation: "Enter valid email",
        passwordMatch: "Password must match",
        passwordLength: "Minimun {{length}} caracters long",
        positiveValidation: "Must be greater than 0",
        dateValidation: "Date cannot be in the past",
        secCodeValidation: "Must be {{length}} characters long",
        invalidUsernamePassword: "Invalid username/password. Please try again.",
        dishNameMaxCharacters: "The name can't be longer than {{characters}} characters",
        dishDescriptionMaxCharacters: "The description can't be longer than {{characters}} characters"
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
            stepDescription: "We need some info to confirm your reservation",
            firstName: "First name",
            lastName: "Last name",
            mail: "Mail",
            phone: "Phone",
        },
        step5:{
            stepTitle: "Done!",
            stepDescriptionPart1: "You made a reservation on Atuel, for the",
            stepDescriptionPart2: " at {{hour}}hs for {{qPeople}} people. Your reservation code is: {{secCode}}",
            pointsDisclaimer: "If you register, you will be able to receive points for discounts!"
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
        table: "Table: {{table}}",
        noTable: "Table: You will get a table when you arrive at the restaurant"
    },
    reservationActions:{
        seat: "Seat",
        cancel: "Cancel reservation",
        makeCheck: "Make check",
        accessReservation: "Access reservation",
        endReservation: "End reservation",
    },
    reservationsPage:{
        tabs:{
            all: "All",
            open: "Open",
            seated: "Seated",
            checkOrdered: "Check Ordered",
            finished: "Finished",
            cancelled: "Cancelled"
        },
        tableHeaders:{
            code: "Code",
            customer: "Customer",
            date: "Date",
            hour: "Hour",
            table: "Table",
            people: "People",
            actions: "Actions"
        },
        sortBy: "Sort By:",
        noReservations: "There are no reservations here",
        tableRow:{
            hour: "{{hour}}:00"
        }
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
            noDishes: "You have not ordered any items yet",
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
        deleteCategory: "Delete category",
        deleteMessage:{
            title: "Delete category",
            description: "Are you sure you want to delete this category?",
            disableMessage: "It is not possible to delete a category that has dishes. Please delete the dishes first.",
            succesfulDeleteMessage: "the category was successfully deleted."
        },
        deleteDish:{
            title: "Delete dish",
            description: "Are you sure you want to delete this dish?"
        }
    },
    checkoutSummary:{
        title: "Reservation summary",
        reservationInfo: "Reservation info:",
        customer: "Customer: {{customer}}",
        date: "Date: ",
        hour: "Hour: {{hour}}:00",
        people: "People: {{people}}",
        table: "Table: {{table}}",
        itemSummary: "Item summary",
        tableHeaders:{
            dish: "Dish",
            qty: "Quantity",
            subtotal: "Subtotal",
        },
        finishButton: "Finish reservation",
        finishMessage: "Reservation was successfully finished."
    },
    dishDisplay:{
        createDish: "Create dish"
    },
    finishReservationModal:{
        title: "Reservation Finished",
        finishedMessageNoPoints: "The restaurant will soon give you the bill. Thanks for coming, come back soon!",
        finishedMessageWithPoints: "The restaurant will soon give you the bill and your points will be added. Thanks for coming, come back soon!",
        goToMenu: "Go to menu"
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
        emptyTable: "There are no orders here",
        handsTableHeaders:{
            table: "Table",
            customer: "Customer"
        },
        handsTitle: "Attention needed",
        handsActionButton: "client assisted",
        noHands: "No attention needed"

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
        label:{
            mail: "Mail",
            name: "Name",
            username: "Username",
            phone: "Phone",
            password: "Password",
            passwordRepeat: "Repeat password"
        }
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
    forms:{
        accountInfo:{
            title: "Account info",
            description: "Write the fields you want to be modified",
            restaurantName: "Restaurant Name",
            customerName: "Customer Name",
            username: "Username",
            mail: "Mail",
            phone: "Phone",
            password: "Password",
            passwordRepeat: "Repeat password"
        },
        authReservation:{
            title: "Reservation security code",
            description: "Enter the security code given in the email we sent you.",
            label: "Reservation Code",
        },
        createCategory:{
            title: "Create category",
            description: "Enter the name of the new category",
            label: "Enter new category name"
        },
        confirmDish:{
            dishPrice: "Price: ${{price}}",
            subtotal: "Subtotal: ${{subtotal}}",
            addToCart: "Add to cart"
        },
        createDish:{
            title: "Create Dish",
            createButton: "Create",
            name: "Name",
            price: "Price",
            description: "Description",
            category: "Category",
        },
        editCategory:{
            title: "Edit category",
            description: "Enter the new name of the category",
            label: "Category name"
        },
        editDish:{
            title: "Dish edition",
            description: "Edit any or all the fields you want changed",
            name: "Name",
            dishDescription: "Description",
            price: "Price",
            category: "Category",
        },
        restaurantInfo:{
            title: "Restaurant info",
            description: "Write the fields you want to be modified",
            openHour: "Open hour",
            closeHour: "Close hour",
            chairs: "Chairs"
        },
        tableNumber: {
            label: "Table number",
            error: "Table not available"
        },
        confirmButton: "Confirm",
        select: "Select one",
        cancelButton: "Cancel"
    },
    systemError: "There was an error in the system. Try again later.",
    pageDoesNotExist: "404: The route requested does not exist"
}