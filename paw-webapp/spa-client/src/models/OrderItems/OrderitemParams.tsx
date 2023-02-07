import {CreateOrderitemPayloadModel} from "./CreateOrderitemPayloadModel"
import { PatchOrderitemPayloadModel } from "./PatchOrderitemPayloadModel";

export class OrderitemParams{
    private _securityCode: string | undefined;
    private _orderItemId: number | undefined;
    private _dishId: number | undefined;
    private _quantity: number | undefined;
    private _status: string | undefined;

    get createOrderitemPayload(): CreateOrderitemPayloadModel | null{
        if(this._dishId == undefined || this._quantity == undefined){
            return null;
        }
        return {
            "dishId": this._dishId,
            "quantity": this._quantity,
        }
    }

    get patchOrderitemPayload(): PatchOrderitemPayloadModel{
        return {
            "status": this._status,
        }
    }


    get securityCode(): string | undefined {
        return this._securityCode;
    }

    set securityCode(value: string | undefined) {
        this._securityCode = value;
    }

    get orderItemId(): number | undefined {
        return this._orderItemId;
    }

    set orderItemId(value: number | undefined) {
        this._orderItemId = value;
    }

    get dishId(): number | undefined {
        return this._dishId;
    }

    set dishId(value: number | undefined) {
        this._dishId = value;
    }

    get quantity(): number | undefined {
        return this._quantity;
    }

    set quantity(value: number | undefined) {
        this._quantity = value;
    }

    get status(): string | undefined {
        return this._status;
    }

    set status(value: string | undefined) {
        this._status = value;
    }
}