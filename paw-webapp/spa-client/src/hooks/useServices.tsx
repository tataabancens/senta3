import { useContext, useState } from "react";
import ServiceContext from "../context/ServiceProvider";

const useServices = () => {
    return useContext(ServiceContext);
}

export default useServices;