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
        removeDiscountText: "Remover descuento",
        applyDiscountText: "Aplicar descuento",
        callWaiter: "Llamar mozo",
        callingWaiter: "Llamando mozo",
        createCategory: "Crear categoría"
    },
    loginPage: {
        loginTitle: "Login",
        usernameInput: "Usuario",
        label:{
            username: "Usuario",
            password: "Contraseña"
        },
        formSubmit:{
            login: "login",
            loading: "Cargando"
        },
        rememberMe: "Recuerdame",
        forgotPassword: "¿Olvidaste tu constraseña?",
        registerCallToAction: "¿No tenes cuenta? registrate!"
    },
    validationSchema:{
        required: "Requerido",
        mailValidation: "Ingresar un mail valido",
        passwordMatch: "Las constraseñas deben coincidir",
        passwordLength: "La contraseña debe tener al menos {{length}} caracteres",
        positiveValidation: "Debe ser mayor que 0",
        dateValidation: "La fecha no puede ser en el pasado",
        secCodeValidation: "Debe ser de {{length}} caracteres",
        invalidUsernamePassword: "Usuario/contraseña invalidos. Por favor, intente nuevamente. ",
        dishNameMaxCharacters: "El nombre no puede tener más de {{characters}} caracteres",
        dishDescriptionMaxCharacters: "La descripción no puede tener más de {{characters}} caracteres"
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
            stepDescription: "Necesitamos tu información para completar la reserva",
            firstName: "Nombre",
            lastName: "Apellido",
            mail: "Mail",
            phone: "Telefono",
        },
        step5:{
            stepTitle: "Listo!",
            stepDescriptionPart1: "Hiciste una reserva en Atuel para el día ",
            stepDescriptionPart2: " a las {{hour}}hs para {{qPeople}} persona/s. Tu codigo de reserva es: {{secCode}}.",
            pointsDisclaimer: "Si te registras vas a poder obtener puntos para descuentos!"
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
        table: "Mesa: {{table}}",
        noTable: "Mesa: Una mesa te sera asignada al llegar al restaurante"
    },
    reservationActions:{
        seat: "Sentar",
        cancel: "Cancelar reserva",
        makeCheck: "Hacer la cuenta",
        accessReservation: "Acceder reserva",
        endReservation: "Terminar reserva",
    },
    reservationsPage:{
        tabs:{
            all: "Todas",
            open: "Abiertas",
            seated: "Sentados",
            checkOrdered: "Piden Cuenta",
            finished: "Finalizadas",
            cancelled: "Canceladas"
        },
        tableHeaders:{
            code: "Codigo",
            customer: "Cliente",
            date: "Día",
            hour: "Hora",
            table: "Mesa",
            people: "Personas",
            actions: "Acciones"
        },
        sortBy: "Ordenar por:",
        noReservations: "No hay reservas aquí",
        tableRow:{
            hour: "{{hour}}:00"
        }
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
            noDishes: "Aun no has ordernado ningún platillo",
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
        deleteCategory: "Eliminar categoría",
        deleteMessage:{
            title: "Borrar categoría",
            description: "¿Esta seguro que desea eliminar esta categoría?",
            disableMessage: "No es posible borrar una categoría que tiene platos. Por favor elimina los platos primero.",
            succesfulDeleteMessage: "La categoría fue borrada con exito."
        },
        deleteDish:{
            title: "Borrar plato",
            description: "¿Esta seguro que desea eliminar este plato?"
        }
    },
    checkoutSummary:{
        title: "Resumen de la reserva",
        reservationInfo: "Información de reserva:",
        customer: "Cliente: {{customer}}",
        date: "Día: ",
        hour: "Hora: {{hour}}:00",
        people: "Personas: {{people}}",
        table: "Mesa: {{table}}",
        itemSummary: "Resumen de pedidos",
        tableHeaders:{
            dish: "Plato",
            qty: "Cantidad",
            subtotal: "Subtotal",
        },
        finishButton: "Finalizar reserva",
        finishMessage: "La reserva fue finalizada exitosamente."
    },
    dishDisplay:{
        createDish: "Crear plato"
    },
    finishReservationModal:{
        title: "Reserva finalizada",
        finishedMessageNoPoints: "El restaurant pronto vendra a cobrarte. Gracias por venir, vuelva pronto!",
        finishedMessageWithPoints: "El restaurant pronto vendra a cobrarte y tus puntos serán agregados. Gracias por venir, vuelva pronto!",
        goToMenu: "Ir al menú"
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
        emptyTable: "No hay pedidos aqui",
        handsTableHeaders:{
            table: "Mesa",
            customer: "Cliente"
        },
        handsTitle: "Necesitan atención",
        handsActionButton: "cliente asistido",
        noHands: "Nadie necesita atención"
    },
    customerReservations:{
        points: "Puntos: {{points}}",
        activeReservationsTitle:"Reservas Activas",
        oldReservationsTitle: "Historial de reservas",
        pendingReservationsTitle: "Finalización pendiente",
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
        label:{
            mail: "Mail",
            name: "Nombre",
            username: "Usuario",
            phone: "Telefono",
            password: "Contraseña",
            passwordRepeat: "Repetir contraseña"
        }
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
    forms:{
        accountInfo:{
            title: "Datos de cuenta",
            description: "Escribi en los campos que quieras cambiar",
            restaurantName: "Nombre del restaurant",
            customerName: "Nombre del cliente",
            username: "Usuario",
            mail: "Mail",
            phone: "Telefono",
            password: "Contraseña",
            passwordRepeat: "Repetir contraseña"
        },
        authReservation:{
            title: "Codigo de reserva",
            description: "Ingresa el codigo de seguridad de la reserva. Lo podes encontrar en el mail que te envíamos.",
            label: "Codigo de reserva",
        },
        createCategory:{
            title: "Crear categoría",
            description: "Ingresa el nombre de la nueva categoría",
            label: "Nombre de la nueva categoría"
        },
        confirmDish:{
            dishPrice: "Precio: ${{price}}",
            subtotal: "Subtotal: ${{subtotal}}",
            addToCart: "Agregar al carrito"
        },
        createDish:{
            title: "Crear plato",
            createButton: "Crear",
            name: "Nombre",
            price: "Precio",
            description: "Descripción",
            category: "Categorpía",
        },
        editCategory:{
            title: "Editar categoría",
            description: "Ingresa un nuevo nombre para la categoría",
            label: "Nombre de la categoría"
        },
        editDish:{
            title: "Editar plato",
            description: "Editar uno o todos los campos que se quiera modificar",
            name: "Nombre",
            dishDescription: "Descripción",
            price: "Precio",
            category: "Categoría",
        },
        restaurantInfo:{
            title: "Datos del restaurant",
            description: "Edita los campos que quieras modificar",
            openHour: "Hora de apertura",
            closeHour: "Hora de cierre",
            chairs: "Sillas"
        },
        tableNumber: {
            label: "Numero de mesa",
            error: "Mesa no disponible"
        },
        confirmButton: "Confirmar",
        select: "Selecciona uno",
        cancelButton: "Cancelar"
    },
    systemError: "Hubo un error en el sistema, intenta de nuevo más tarde.",
    pageDoesNotExist: "404: La ruta solicitada no existe"
}