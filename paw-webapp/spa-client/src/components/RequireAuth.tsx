import { useLocation, Navigate, Outlet } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import { paths } from "../constants/constants";

interface Props {
    allowedRoles: string[];
}

const RequireAuth: React.FC<Props> = ({ allowedRoles }) => {
    const { auth } = useAuth();
    const location = useLocation();

    return (
        auth?.roles?.find(role => allowedRoles?.includes(role))
            ? <Outlet />
            : auth?.user !== ""
                ? <Navigate to={"/unauthorized"} state={{ from: location}} replace />
                : <Navigate to={"/login"} state={{ from: location}} replace />
    );
}

export default RequireAuth;