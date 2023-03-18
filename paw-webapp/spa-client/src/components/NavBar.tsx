import {
  AppBar,
  Box,
  Button,
  Grid,
  Stack,
  Toolbar,
  Typography,
} from "@mui/material";
import { Container } from "@mui/system";
import React from "react";
import {Link, useNavigate} from "react-router-dom";
import {paths} from "../constants/constants";

export const NavBar: React.FC<{}> = () => {
  let navigate = useNavigate();
  return (
    <Box sx={{ flexGrow: 1, mb: 8}}>
      <AppBar position="fixed">
        <Toolbar>
          <Container maxWidth="xl">
            <Grid container justifyContent="space-between">
              <Grid item>
                <Stack direction="row" spacing={2}>
                  <Button variant="contained" onClick={() => {navigate(paths.ROOT)}}>
                    {/*<Link*/}
                    {/*  to="/"*/}
                    {/*  style={{ textDecoration: "none", color: "white" }}*/}
                    {/*>*/}
                      <Typography variant="h5" sx={{ fontStyle: "italic" }}>
                        Senta3
                      </Typography>
                    {/*</Link>*/}
                  </Button>
                  <Button variant="contained" onClick={() => {navigate(paths.ROOT + "/reservations")}}>
                    {/*<Link*/}
                    {/*  to="reservations"*/}
                    {/*  style={{ textDecoration: "none", color: "white" }}*/}
                    {/*>*/}
                      <Typography>Reservations</Typography>
                    {/*</Link>*/}
                  </Button>
                  <Button variant="contained" onClick={() => {navigate(paths.ROOT + "/restaurantReservations")}}>
                    {/*<Link*/}
                    {/*  to="reservationsRestaurant"*/}
                    {/*  style={{ textDecoration: "none", color: "white" }}*/}
                    {/*>*/}
                      <Typography>RestaurantReservations</Typography>
                    {/*</Link>*/}
                  </Button>
                </Stack>
              </Grid>
              <Grid item>
                <Stack direction="row" spacing={2}>
                  <Button variant="contained" onClick={() => {navigate(paths.ROOT + "/login")}}>
                    {/*<Link*/}
                    {/*  to="login"*/}
                    {/*  style={{ textDecoration: "none", color: "white" }}*/}
                    {/*>*/}
                      <Typography>Login</Typography>
                    {/*</Link>*/}
                  </Button>
                  <Button onClick={() => navigate(paths.ROOT + "/signUp")} variant="contained">
                    {/*<Link*/}
                    {/*  to="signUp"*/}
                    {/*  style={{ textDecoration: "none", color: "white" }}*/}
                    {/*>*/}
                      <Typography>Register</Typography>
                    {/*</Link>*/}
                  </Button>
                </Stack>
              </Grid>
            </Grid>
          </Container>
        </Toolbar>
      </AppBar>
    </Box>
  );
};
