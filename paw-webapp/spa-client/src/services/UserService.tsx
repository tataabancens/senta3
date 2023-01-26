import axios, { AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import { UserModel } from "../models";
import { UserParams } from "../models/Users/UserParams";


export class UserService{
    private readonly basePath = paths.LOCAL_BASE_URL + paths.USERS

    public getUsers(): Promise<AxiosResponse<UserModel[]>>{
        return axios.get<UserModel[]>(this.basePath);
    }

    public getUserById(userId: number): Promise<AxiosResponse<UserModel>>{
        return axios.get<UserModel>(this.basePath + '/' + userId);
    }

    public deleteUser(userId: number){
        return axios.delete(this.basePath + '/' + userId);
    }

    public createUser(params: UserParams): Promise<AxiosResponse<UserModel>>{
        console.log(params.createUserPayload);
        return axios.post(this.basePath, params.createUserPayload);
    }

    public editUser(params: UserParams): Promise<AxiosResponse>{
        return axios.patch(this.basePath + `/${params.userId}`, params.editUserPayload);
    }
}