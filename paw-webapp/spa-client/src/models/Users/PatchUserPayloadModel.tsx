import {passwordPair} from "../PasswordPairModel"

export interface PatchUserPayloadModel {
    username: string | undefined,
    psPair: passwordPair | undefined,
}