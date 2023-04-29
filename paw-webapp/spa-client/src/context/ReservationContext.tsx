import { createContext } from "react";
import { ReservationModel } from "../models";

export const ReservationContext = createContext<ReservationModel | undefined>(undefined);