
export default interface OrderItemModel{
    orderItemId: number,
    reservationId: number,
    dishId: number,
    unitPrice: number,
    quantity: number,
    status: string,
    tableNmbr: number,
    dishName: string,
    securityCode: string
}