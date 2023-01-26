// import { createCustomerPayloadModel } from "./CreateCustomerPayloadModel";
import { createCustomerPayloadModel } from "./CreateCustomerPayloadModel";
import {passwordPair} from "../PasswordPairModel"
import { patchCustomerPayloadModel } from "./PatchCustomerPayloadModel";


export class CustomerParams {

    private _mail: string | undefined;
    private _username: string | undefined;
    private _customerName: string | undefined;
    private _phone: string | undefined;
    private _psPair: passwordPair | undefined;

    private _userId: number | undefined;
    private _customerId: number | undefined;
    private _points: number | undefined;



    get createCustomerPayload(): createCustomerPayloadModel | null {
        if(this._mail == undefined || this._username == undefined || this._customerName == undefined || this._phone == undefined || this._psPair == undefined){
            return null
        }
        return {
            mail: this._mail,
            username: this._username,
            customerName: this._customerName,
            phone: this._phone,
            psPair: this._psPair,
        }
    }

    get patchCustomerPayload(): patchCustomerPayloadModel {
        return {
            name: this._customerName,
            phone: this._phone,
            mail: this._mail,
            userId: this._userId,
            points: this._points,
        }
    }

    get customerId(): number | undefined {
        return this._customerId;
    }

    set customerId(value: number | undefined) {
        this._customerId = value;
    }

    get mail(): string | undefined {
        return this._mail;
    }

    set mail(value: string | undefined) {
        this._mail = value;
    }

    get username(): string | undefined {
        return this._username;
    }

    set username(value: string | undefined) {
        this._username = value;
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

    get psPair(): passwordPair | undefined {
        return this._psPair;
    }

    set psPair(value: passwordPair | undefined) {
        this._psPair = value;
    }
}