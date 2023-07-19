import { useState, useEffect } from "react";
import { CustomerModel } from "../../../models";
import PointsModel from "../../../models/Customers/PointsModel";
import useCustomerService from "./useCustomerService";
import useServices from "../../useServices";

export const useCustomer = (customerPath: string | undefined) => {
    const { customerService: cs } = useServices();
    const abortController = new AbortController();
    const [customer, setCustomer] = useState<CustomerModel | undefined>();
    const [points, setPoints] = useState<PointsModel | undefined>();
    const [customerError, setCustomerError] = useState<string>();
    const [pointsError, setPointsError] = useState<string>();

    const extractIdFromPath = () => {
        const parts = customerPath!.split('/');
        const customerId = parts[parts.length - 1];
        return parseInt(customerId, 10);
    }

    useEffect(() => {
        (async () => {
            if(customerPath){
                const { isOk, data, error } = await cs.getCustomerByIdNewVersion(extractIdFromPath());
                if (isOk) {
                    console.log(data)
                    setCustomer(data);
                } else {
                    setCustomerError(error);
                }
            }
        })();
        return () => {
            abortController.abort();
        }
    }, [customerPath]);

    useEffect(() => {
        (async () => {
            if(customer){
                const { isOk, data, error } = await cs.getPoints(customer.id, abortController);
            if (isOk) {
                data ? setPoints(data) : setPoints(undefined);
            } else {
                setPointsError(error);
            }
            }
        })();
        return () => {
            abortController.abort();
        }
    },[customer])
    
    return {
        customer: customer,
        customerError,
        customerLoading: !customer && !customerError,
        points: points,
        pointsError,
        pointsLoading:!points &&!pointsError
    }
}