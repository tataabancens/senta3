import { AxiosInstance, AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import { UserModel } from "../models";
import { UserParams } from "../models/Users/UserParams";


export class UserService {
    private axios:AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    public getUsers(): Promise<AxiosResponse<UserModel[]>>{
        return this.axios.get<UserModel[]>(paths.USERS);
    }

    public getUserById(userId: number): Promise<AxiosResponse<UserModel>>{
        return this.axios.get<UserModel>(paths.USERS + '/' + userId);
    }

    public deleteUser(userId: number){
        return this.axios.delete(paths.USERS + '/' + userId);
    }

    public createUser(params: UserParams): Promise<AxiosResponse<UserModel>>{
        return this.axios.post(paths.USERS, params.createUserPayload);
    }

    public editUser(params: UserParams): Promise<AxiosResponse>{
        return this.axios.patch(paths.USERS + `/${params.userId}`, params.editUserPayload);
    }
}