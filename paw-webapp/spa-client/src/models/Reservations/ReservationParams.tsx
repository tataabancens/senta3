import {patchReservationPayloadModel} from "./PatchReservationPayloadModel";
import {createReservationPayloadModel} from "./CreateReservationPayloadModel";

export class ReservationParams {
    private _restaurantId: number | undefined;
    private _customerId: number | undefined;

    private _hour: number | undefined;
    private _qPeople: number | undefined;

    private _securityCode: string | undefined;
    private _date: string | undefined;

    private _table: number | undefined;
    private _hand: boolean | undefined;
    private _discount: boolean | undefined;
    private _status: string | undefined;

    private _orderBy: string | undefined;
    private _direction: string | undefined;
    private _filterStatus: string | undefined;
    private _page: number | undefined;

    private _orderItemStatus: string | undefined;

    
    get patchReservationPayload(): patchReservationPayloadModel | null{
        return {
            "hour": this._hour,
            "qPeople": this._qPeople,
            "reservationStatus": this._status,
            "discount": this._discount,
            "reservationDate": this._date,
            "table": this._table,
            "hand": this._hand,
        };
    }

    get createReservationPayload(): createReservationPayloadModel | null{
        if(this._hour === undefined || this._qPeople === undefined || this._restaurantId === undefined || this._customerId === undefined || this._date === undefined){
            return null;
        }
        return {
            "hour": this._hour,
            "qPeople": this._qPeople,
            "restaurantId": this._restaurantId,
            "customerId": this._customerId,
            "reservationDate": this._date
        };
    }

    get getReservationsQuery(): string{
        let query = "?"
        if(this._restaurantId !== undefined){
            query += `restaurantId=${this._restaurantId}`
        }
        if(this._customerId !== undefined){
            query += `&customerId=${this._customerId}`
        }
        if(this._orderBy !== undefined){
            query += `&orderBy=${this._orderBy}`
        }
        if(this._direction !== undefined){
            query += `&direction=${this._direction}`
        }
        if(this._filterStatus !== undefined){
            query += `&filterStatus=${this._filterStatus}`
        }
        if(this._status !== undefined){
            query += `&status=${this._status}`
        }
        if(this._page !== undefined){
            query += `&page=${this._page}`
        }
        return query;
    }

    get orderItems(): string | undefined {
        return this._orderItemStatus;
    }

    set orderItemStatus(status: string | undefined){
        this._orderItemStatus = status;
    }

    get customerId(): number | undefined {
        return this._customerId;
    }

    set customerId(value: number | undefined) {
        this._customerId = value;
    }

    get hour(): number | undefined {
        return this._hour;
    }

    set hour(value: number | undefined) {
        this._hour = value;
    }

    get qPeople(): number | undefined {
        return this._qPeople;
    }

    set qPeople(value: number | undefined) {
        this._qPeople = value;
    }

    get securityCode(): string | undefined {
        return this._securityCode;
    }

    set securityCode(value: string | undefined) {
        this._securityCode = value;
    }

    get date(): string | undefined {
        return this._date;
    }

    set date(value: string | undefined) {
        this._date = value;
    }

    get table(): number | undefined {
        return this._table;
    }

    set table(value: number | undefined) {
        this._table = value;
    }

    get hand(): boolean | undefined {
        return this._hand;
    }

    set hand(value: boolean | undefined) {
        this._hand = value;
    }

    get discount(): boolean | undefined {
        return this._discount;
    }

    set discount(value: boolean | undefined) {
        this._discount = value;
    }

    get status(): string | undefined {
        return this._status;
    }

    set status(value: string | undefined) {
        this._status = value;
    }

    get orderBy(): string | undefined {
        return this._orderBy;
    }

    set orderBy(value: string | undefined) {
        this._orderBy = value;
    }

    get direction(): string | undefined {
        return this._direction;
    }

    set direction(value: string | undefined) {
        this._direction = value;
    }

    get filterStatus(): string | undefined {
        return this._filterStatus;
    }

    set filterStatus(value: string | undefined) {
        this._filterStatus = value;
    }

    get page(): number | undefined {
        return this._page;
    }

    set page(value: number | undefined) {
        this._page = value;
    }

    get restaurantId(): number {
        return this._restaurantId as number;
    }

    set restaurantId(value: number) {
        this._restaurantId = value;
    }
}
