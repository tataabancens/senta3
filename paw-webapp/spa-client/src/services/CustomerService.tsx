import axios, { AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import { CustomerModel } from "../models";
import { CustomerParams } from "../models/Customers/CustomerParams";


export class CustomerService{
    private readonly basePath = paths.LOCAL_BASE_URL + paths.CUSTOMERS;

  public getCustomerById(id: number): Promise<AxiosResponse<CustomerModel>> {
    return axios.get<CustomerModel>(this.basePath + "/" + id);
  }

    public getCustomers(page: number):Promise<AxiosResponse<Array<CustomerModel>>>{
        return axios.get<Array<CustomerModel>>(this.basePath + '?page='+ page);
    }

    public createCustomer(params: CustomerParams): Promise<AxiosResponse<CustomerModel>>{
        return axios.post(this.basePath, params.createCustomerPayload);
    }

    public editCustomer(params: CustomerParams): Promise<AxiosResponse<CustomerModel>>{
        return axios.patch(this.basePath + `/${params.customerId}`, params.patchCustomerPayload)
    }

    public deleteCustomer(id: number){
        return axios.delete(this.basePath + '/' +id);
    }
}
