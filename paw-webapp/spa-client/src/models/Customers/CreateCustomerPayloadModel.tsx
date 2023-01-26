import {passwordPair} from "../PasswordPairModel"

export interface createCustomerPayloadModel {
    mail: string,
    username: string,
    customerName: string,
    phone: string,
    psPair: passwordPair
}