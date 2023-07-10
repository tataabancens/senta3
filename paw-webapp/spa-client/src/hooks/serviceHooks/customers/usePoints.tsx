import { useState, useEffect } from "react";
import { CustomerModel } from "../../../models";
import PointsModel from "../../../models/Customers/PointsModel";
import useCustomerService from "./useCustomerService";

export const usePoints = (customer: CustomerModel | undefined) => {
    const cs = useCustomerService();
    const abortController = new AbortController();
    const [points, setCustomerPoints] = useState<PointsModel | undefined>();
    const [error, setError] = useState<string>();

    useEffect(() => {
        (async () => {
            if(customer){
                const { isOk, data, error } = await cs.getPoints(customer.id, abortController)
                if (isOk) {
                    setCustomerPoints(data);
                }
                if(error) {
                    setError(error);
                }
            } 
        })();
        return () => {
            abortController.abort();
        }
    }, [customer]);
    
    return {
        points,
        error,
        loading: !points && !error,
    }
}