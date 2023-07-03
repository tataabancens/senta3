import { AxiosInstance, AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import { UserModel } from "../models";
import { UserParams } from "../models/Users/UserParams";


export class UserService {
    private axios: AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    private readonly ACCEPT = { "Accept": "application/vnd.sentate.user.v1+json" };
    private readonly CONTENT_TYPE = { "Content-type": "application/vnd.sentate.user.v1+json" };

    public getUsers(): Promise<AxiosResponse<UserModel[]>> {
        return this.axios.get<UserModel[]>(paths.USERS, { headers: this.ACCEPT });
    }

    public getUserById(userId: number): Promise<AxiosResponse<UserModel>> {
        return this.axios.get<UserModel>(paths.USERS + '/' + userId, { headers: this.ACCEPT });
    }

    public deleteUser(userId: number) {
        return this.axios.delete(paths.USERS + '/' + userId, { headers: this.ACCEPT });
    }

    public createUser(params: UserParams): Promise<AxiosResponse> {
        return this.axios.post(paths.USERS, params.createUserPayload, { headers: this.CONTENT_TYPE });
    }

    public editUser(params: UserParams): Promise<AxiosResponse<UserModel>> {
        return this.axios.patch(paths.USERS + `/${params.userId}`, params.editUserPayload, { headers: this.CONTENT_TYPE });
    }
}