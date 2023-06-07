import { useNavigate } from "react-router-dom";
import {
    Button,
} from "@mui/material";

const UnauthorizedPage = () => {
    const navigate = useNavigate();

    const goBack = () => navigate(-1);

    return (
        <div>
            <h1>Unauthorized</h1>
            <p>You dont have access here</p>
            <Button onClick={goBack}>GoBack</Button>
        </div>
    )
}

export default UnauthorizedPage;