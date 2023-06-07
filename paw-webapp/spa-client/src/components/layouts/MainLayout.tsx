import { Container } from "@mui/system";
import { FC } from "react";
import { Outlet } from "react-router-dom";
import { NavBar } from "../NavBar";

type Props = {
};

const MainLayout: FC<Props> = (): JSX.Element => {
  return (
    <>
      <NavBar />
      <Outlet />
    </>
  );
}

export default MainLayout;
