export default interface ReservationModel{
    securityCode: string,
    hour: number,
    date: string,
    peopleAmount: number,
    tableNumber: number,
    hand: boolean,
    orderItems: string,
    restaurant: string,
    customerName: string,
    recommendedDish?: string,
    customer: string,
    usedDiscount: boolean,
    status: string
}