import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import MenuPage from "./pages/MenuPage";
import NoPage from "./pages/NoPage";
import SignUpPage from "./pages/SignUpPage";
import MainLayout from "./components/layouts/MainLayout";
import CustomerReservations from "./pages/CustomerReservations";

function App(){
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<MenuPage />} />
          <Route path="login" element={<LoginPage />} />
          <Route path="signUp" element={<SignUpPage />} />
          <Route path="reservations" element={<CustomerReservations />} />
          <Route path="*" element={<NoPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default App;
