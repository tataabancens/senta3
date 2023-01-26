import {passwordPair} from "../PasswordPairModel"
import { CreateUserPayloadModel } from "./CreateUserPayloadModel";
import { PatchUserPayloadModel } from "./PatchUserPayloadModel";


export class UserParams {
    private _username: string | undefined;
    private _psPair: passwordPair | undefined;
    private _role: string | undefined;
    private _userId: number | undefined;

    get createUserPayload(): CreateUserPayloadModel | null{
        if(this._username == undefined || this._psPair == undefined || this._role == undefined){
            return null;
        }
        return {
            username: this._username,
            psPair: this._psPair,
            role: this._role,
        }
    }

    get editUserPayload(): PatchUserPayloadModel | null{
        return {
            username: this._username,
            psPair: this._psPair,
        }
    }

    get userId(): number | undefined {
        return this._userId;
    }

    set userId(value: number | undefined) {
        this._userId = value;
    }

    get username(): string | undefined {
        return this._username;
    }

    set username(value: string | undefined) {
        this._username = value;
    }

    get psPair(): passwordPair | undefined {
        return this._psPair;
    }

    set psPair(value: passwordPair | undefined) {
        this._psPair = value;
    }

    get role(): string | undefined {
        return this._role;
    }

    set role(value: string | undefined) {
        this._role = value;
    }
}