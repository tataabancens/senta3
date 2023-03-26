import { createContext, useState, useEffect } from "react";
import { emptyAuth } from "../constants/constants";

export interface Authentication {
    user: string;
    id: number;
    authorization: string | undefined;
    roles: string[];
}

export interface AuthContextValue {
    auth: Authentication;
    setAuth: (newAuth: any) => void;
}

const authContext: AuthContextValue = {
    auth: emptyAuth,
    setAuth: () => null,
}

const AuthContext = createContext(authContext);

interface Props {
    children: React.ReactNode;
}

export const AuthProvider: React.FC<Props> = ({ children }) => {
    const [auth, setAuth] = useState(getAuthInfo());

    useEffect(() => {
        saveAuthInfo(auth);
    }, [auth])

    return (
        <AuthContext.Provider value={{ auth, setAuth }}>
            {children}
        </AuthContext.Provider>
    )
}

// Guardar información en localStorage
const saveAuthInfo = (authInfo: Authentication) => {
    localStorage.setItem('authInfo', JSON.stringify(authInfo));
};

// Obtener información de localStorage
const getAuthInfo = (): Authentication => {
    const authInfo = localStorage.getItem('authInfo');
    if (authInfo !== "undefined") {
        return authInfo ? JSON.parse(authInfo) : emptyAuth;
    }
    return emptyAuth;
};

export default AuthContext;