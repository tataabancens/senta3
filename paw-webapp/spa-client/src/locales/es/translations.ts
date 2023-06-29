export const TRANSLATIONS_ES ={
    navBar: {
        customerPages:{
            reservations: "Reservas",
            profile: "Perfil"
        },
        restaurantPages:{
            menu: "Menú",
            kitchen: "Cocina",
            reservations: "Reservas"
        },
        loginButton: "Login",
        registerButton: "Registro",
        logoutButton: "Cerrar sesión"
    },
    restaurantHeader:{
        menuHeader: "Menú",
        makeReservation: "Hacer reserva",
        haveReservation: "Tengo reserva",
        myReservation: "Mi reserva",
        createCategory: "Crear categoría"
    },
    loginPage: {
        loginTitle: "Login",
        usernameInput: "Usuario",
        formSubmit:{
            login: "login",
            loading: "Cargando"
        },
        forgotPassword: "¿Olvidaste tu constraseña?",
        registerCallToAction: "¿No tenes cuenta? registrate!"
    },
    createReservation:{
        pageTitle: "Crear reserva",
        step1:{
            stepTitle: "¿Cuantos vienen?",
            stepDescription: "¿Cuantas personas asistirán?"
        },
        step2:{
            stepTitle: "¿Que día?",
            stepDescription: "¿Que día querrían venir?"
        },
        step3:{
            stepTitle: "¿Que hora?",
            stepDescription: "¿A que hora reservarán? las reservas duran 1 hora.",
            selectLabel: "Selecciona una"
        },
        step4:{
            stepTitle: "Información de contacto",
            stepDescription: "Necesitamos tu información para completar la reserva"
        },
        step5:{
            stepTitle: "Listo!",
            stepDescription: "Hiciste una reserva en Atuel para las {{hour}}hs del {{date}} para {{qPeople}} persona/s. Tu codigo de reserva es: {{secCode}}."
        },
        next: "Siguiente",
        back: "Volver",
        makeReservation: "Reservar",
        continueWithoutSigning: "Continuar sin registrarse",
        signUp: "Registrarse",
        goToReservation: "Ir a mi reserva"
    },
    reservationData:{
        title: "Mi reserva",
        customer: "Cliente: {{customer}}",
        code: "Codigo: {{code}}",
        date: "Día: ",
        hour: "Hora: {{hour}}:00",
        table: "Mesa: {{table}}"
    },
    shoppingCart:{
        cartPanel:{
            title: "Carrito",
            recommendationTitle: "Otras personas tambien pidieron:",
            noRecommendation: "No tenemos recomendaciones para ti",
            orderItems: "Ordenar platos",
            clearItems: "Limpiar carrito"
        },
        ordersPanel:{
            title: "Pedidos",
            disclaimer: "El estatus de tus pedidos será visible al llegar al restaurante",
            orderedStatus: "Pedido",
            cookingStatus: "Cocinando",
            deliveringStatus: "Entregando"
        },
        checkPanel:{
            title: "Resumen",
            orderCheck: "Pedir la cuenta"
        },
        tableHeaders:{
            dish: "Plato",
            qty: "Cantidad",
            subtotal: "Subtotal",
            status: "Estado"
        }
    },
    restaurantMenu:{
        editCategory: "Editar categoría",
        deleteCategory: "Eliminar categoría"
    },
    dishDisplay:{
        createDish: "Crear plato"
    },
    kitchenPage:{
        orderedTitle: "Pedidos",
        cookingTitle: "Cocinando",
        deliveredTitle: "Entregando",
        kitchenHeaders:{
            dish: "Plato",
            qty: "Cantidad",
            table: "Mesa"
        },
        cookAction: "Cocinar",
        deliverAction: "Entregar",
        doneAction: "Entregado",
        emptyTable: "No hay pedidos aqui"
    },
    customerReservations:{
        points: "Puntos: {{points}}",
        activeReservationsTitle:"Reservas Activas",
        oldReservationsTitle: "Historial de reservas",
        reservationCard:{
            date: "día: ",
            hour: "hora: {{hour}}:00",
            code: "codigo: {{code}}",
            people: "personas: {{people}}"
        },
        enterButton: "Acceder"
    },
    registerPage:{
        registerTitle: "Registro",
        registerButton: "registrarse",
        loginCallToAction: "¿Ya tenes una cuenta? ingresa!",
    },
    profilePage: {
        title: "Perfil",
        accountInfo:{
            title: "Datos de cuenta:",
            restaurantName: "Nombre del restaurant: {{name}}",
            username: "Usuario: {{username}}",
            mail: "Mail: {{mail}}",
            clientName: "Nombre : {{customer}}",
            phone: "Telefono: {{phone}}",
            points: "Puntos: {{points}}"
        },
        restaurantInfo:{
            title: "Datos del restaurant:",
            chairs: "Sillas: {{chairs}}",
            openHours: "Hora de apertura: {{hour}}:00",
            closeHours: "Hora de cierre: {{hour}}:00"
        },
        editButton: "Editar"
    },
    systemError: "Hubo un error en el sistema, intenta de nuevo más tarde."
}