import { Container } from "@mui/system";
import { Outlet } from "react-router-dom";
import { NavBar } from "../NavBar";

function MainLayout() {
  return (
    <>
      <NavBar />
      <Outlet />
    </>
  );
}

export default MainLayout;
