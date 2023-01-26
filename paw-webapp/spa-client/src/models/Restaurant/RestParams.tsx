import {RestPayloadModel} from "./RestPayloadModel";

export class RestParams{
    private _restaurantName: string | undefined;
    private _phone: string | undefined;
    private _mail: string | undefined;
    private _totalChairs: number | undefined;
    private _openHour: number | undefined;
    private _closeHour: number | undefined;
    private _restaurantId: number | undefined;

    get patchRestaurantPayload(): RestPayloadModel{
        return {
            restaurantName: this._restaurantName,
            phone: this._phone,
            mail: this._mail,
            totalChairs: this._totalChairs,
            openHour: this._openHour,
            closeHour: this._closeHour,
        }
    }


    get restaurantId(): number | undefined {
        return this._restaurantId;
    }

    set restaurantId(value: number | undefined) {
        this._restaurantId = value;
    }

    get restaurantName(): string | undefined {
        return this._restaurantName;
    }

    set restaurantName(value: string | undefined) {
        this._restaurantName = value;
    }

    get phone(): string | undefined {
        return this._phone;
    }

    set phone(value: string | undefined) {
        this._phone = value;
    }

    get mail(): string | undefined {
        return this._mail;
    }

    set mail(value: string | undefined) {
        this._mail = value;
    }

    get totalChairs(): number | undefined {
        return this._totalChairs;
    }

    set totalChairs(value: number | undefined) {
        this._totalChairs = value;
    }

    get openHour(): number | undefined {
        return this._openHour;
    }

    set openHour(value: number | undefined) {
        this._openHour = value;
    }

    get closeHour(): number | undefined {
        return this._closeHour;
    }

    set closeHour(value: number | undefined) {
        this._closeHour = value;
    }
}