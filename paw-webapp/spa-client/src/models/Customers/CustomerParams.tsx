import { createCustomerPayloadModel } from "./CreateCustomerPayloadModel";
import { patchCustomerPayloadModel } from "./PatchCustomerPayloadModel";


export class CustomerParams {

    private _mail: string | undefined;
    private _customerName: string | undefined;
    private _phone: string | undefined;

    private _userId: number | undefined;
    private _customerId: number | undefined;
    private _points: number | undefined;



    get createCustomerPayload(): createCustomerPayloadModel | null {
        if(this._mail === undefined || this._customerName === undefined || this._phone === undefined){
            return null
        }
        if(this._userId === undefined){
            return {
                "mail": this._mail,
                "customerName": this._customerName,
                "phone": this._phone,
                "userId": null,
            }
        } else {
            return {
                "mail": this._mail,
                "customerName": this._customerName,
                "phone": this._phone,
                "userId": this._userId,
            }
        }

    }

    get patchCustomerPayload(): patchCustomerPayloadModel {
        return {
            "name": this._customerName,
            "phone": this._phone,
            "mail": this._mail,
            "userId": this._userId,
            "points": this._points,
        }
    }

    get customerId(): number | undefined {
        return this._customerId;
    }

    set customerId(value: number | undefined) {
        this._customerId = value;
    }

    get userId(): number | undefined {
        return this._userId;
    }

    set userId(value: number | undefined) {
        this._userId = value;
    }

    get mail(): string | undefined {
        return this._mail;
    }

    set mail(value: string | undefined) {
        this._mail = value;
    }

    get customerName(): string | undefined {
        return this._customerName;
    }

    set customerName(value: string | undefined) {
        this._customerName = value;
    }

    get phone(): string | undefined {
        return this._phone;
    }

    set phone(value: string | undefined) {
        this._phone = value;
    }

    get points(): number | undefined {
        return this._points;
    }

    set points(value: number | undefined) {
        this._points = value;
    }
}