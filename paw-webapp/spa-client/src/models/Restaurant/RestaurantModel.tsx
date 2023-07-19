export default interface RestaurantModel{
    id: number,
    name: string,
    phone: string,
    mail: string,
    dishes: string,
    reservations: string,
    dishCategories: string,
    user: string,
    totalChairs: number,
    openHour: number,
    closeHour: number,
    pointsForDiscount: number,
    discountCoefficient: number,
    addPointsCoefficient: number,
    self: string
}