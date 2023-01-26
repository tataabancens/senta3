import {passwordPair} from "../PasswordPairModel"

export interface CreateUserPayloadModel {
    username: string,
    psPair: passwordPair,
    role: string,
}