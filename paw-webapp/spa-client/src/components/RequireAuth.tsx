import { useLocation, Navigate, Outlet } from "react-router-dom";
import useAuth from "../hooks/serviceHooks/authentication/useAuth";
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
                ? <Navigate to={paths.ROOT + "/unauthorized"} state={{ from: location }} replace />
                : <Navigate to={paths.ROOT + "/login"} state={{ from: location }} replace />
    );
}

export default RequireAuth;