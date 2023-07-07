import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import MenuPage from "./pages/MenuPage";
import NoPage from "./pages/NoPage";
import SignUpPage from "./pages/SignUpPage";
import MainLayout from "./components/layouts/MainLayout";
import CustomerReservations from "./pages/CustomerReservations";
import Reservations from "./pages/Reservations";
import CreateReservation from "./pages/CreateReservation/CreateReservationPage";
import ProfilePage from "./pages/ProfilePage";
import RequireAuth from "./components/RequireAuth";
import UnauthorizedPage from "./pages/UnauthorizedPage";
import FullMenuPage from "./pages/FullMenuPage";
import CheckOutPage from "./pages/CheckOutPage";
import RestaurantMenu from "./pages/RestaurantMenu";
import Kitchen from "./pages/Kitchen";
import { RestaurantMenuProvider } from "./context/RestaurantMenuContext";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/*todo deploy*/}
        {/* <Route path="/paw-2022a-05/ or /" element={<MainLayout />}> */}
        <Route path="/paw-2022a-05/" element={<MainLayout />}>
          {/* These are public routes */}
          <Route index element={<MenuPage />} />
          <Route path="login" element={<LoginPage />} />
          <Route path="signUp" element={<SignUpPage />} />
          <Route path="createReservation" element={< CreateReservation />} />
          <Route path="unauthorized" element={<UnauthorizedPage />} />
          <Route path="reservations/:securityCode" element={<FullMenuPage />} />
          <Route path="reservations/:securityCode/checkOut" element={<CheckOutPage />} />

          {/* Customer only routes*/}
          <Route element={<RequireAuth allowedRoles={["ROLE_CUSTOMER"]} />}>
            <Route path="reservations" element={<CustomerReservations />} />
          </Route>

          {/* Restaurant only Routes*/}
          <Route element={<RequireAuth allowedRoles={["ROLE_RESTAURANT"]} />}>
            <Route path="restaurantReservations" element={<Reservations />} />
            <Route path="restaurantMenu" element={
              <RestaurantMenuProvider>
                <RestaurantMenu />
              </RestaurantMenuProvider>} />
          </Route>
          
          {/* Kitchen, waiter and restaurant*/}
          <Route element={<RequireAuth allowedRoles={["ROLE_RESTAURANT", "ROLE_WAITER", "ROLE_KITCHEN"]} />}>
            <Route path="kitchen" element={<Kitchen />}/>
          </Route>
            
          {/* Restaurant and customer */}
          <Route element={<RequireAuth allowedRoles={["ROLE_RESTAURANT", "ROLE_CUSTOMER"]} />}>
            <Route path="profile" element={<ProfilePage />} />
          </Route>

          {/*Catch all */}
          <Route path="*" element={<NoPage />} />

        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default App;
