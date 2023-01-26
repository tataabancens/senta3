import { Container } from "@mui/system";
import { Outlet } from "react-router-dom";
import { NavBar } from "../NavBar";

function MainLayout() {
  return (
    <Container>
      <NavBar />
      <Container sx={{ display: "flex"}}>
        <Outlet />
      </Container>
    </Container>
  );
}

export default MainLayout;
