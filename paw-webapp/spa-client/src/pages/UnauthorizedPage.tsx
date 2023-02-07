import { useNavigate } from "react-router-dom";

const UnauthorizedPage = () => {
    const navigate = useNavigate();

    const goBack = () => navigate(-1);

    return (
        <div>
            <h1>Unauthorized</h1>
            <p>You dont have access here</p>
            <button onClick={goBack}>GoBack</button>
        </div>
    )
}

export default UnauthorizedPage;