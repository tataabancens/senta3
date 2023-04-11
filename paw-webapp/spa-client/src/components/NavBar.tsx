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
import React, { useEffect, useState } from "react";
import {Link, useNavigate} from "react-router-dom";
import {emptyAuth, paths} from "../constants/constants";
import useUserService from "../hooks/serviceHooks/useUserService";
import useAuth from "../hooks/useAuth";
import { UserModel } from "../models";
import { handleResponse } from "../Utils";

interface nameSitePair{
  key: string,
  value: string
}

export const NavBar: React.FC<{}> = () => {
  let navigate = useNavigate();
  const { auth, setAuth } = useAuth();
  const [user, setUser] = useState<UserModel | undefined>();
  const [rolePages, setRolePages] = useState<nameSitePair[]>([]);
  const userService = useUserService();

  const customerPages = new Map<string, string>([
    ["Reservations","reservations"],
    ["Profile","profile"],
  ]);
  const restaurantPages = new Map<string, string>([
    ["Kitchen", "kitchen"],
    ["Reservations", "restaurantReservations"]
  ]);

  const logOut = () => {
    setAuth(emptyAuth);
    navigate(paths.ROOT);
  } 

  useEffect(() => {
    if(auth.roles[0] === "ROLE_CUSTOMER"){
      const pages: nameSitePair[] = Array.from(customerPages, ([key, value]) => ({key, value}));
      handleResponse(userService.getUserById(auth.id), user => setUser(user));
      setRolePages(pages);
    }
    else if(auth.roles[0] === "ROLE_RESTAURANT"){
      const pages: nameSitePair[] = Array.from(restaurantPages, ([key, value]) => ({key, value}));
      handleResponse(userService.getUserById(auth.id), user => setUser(user));
      setRolePages(pages);
    }
    else{
      setRolePages([]);
      console.log("reRender");
    }

  },[auth]);

  return (
    <Box sx={{ flexGrow: 1, mb: 8}}>
      <AppBar position="fixed">
        <Toolbar>
          <Container maxWidth="xl">
            <Grid container justifyContent="space-between">
              <Grid item>
                <Stack direction="row" spacing={2}>
                  <Button variant="contained" onClick={() => {navigate(paths.ROOT)}}>
                      <Typography variant="h5" sx={{ fontStyle: "italic" }}>
                        Senta3
                      </Typography>
                  </Button>
                  {rolePages.map((pair: nameSitePair, index) => 
                  <Button variant="contained" key={index} onClick={() => {navigate(paths.ROOT + pair.value)}}>
                    <Typography>{pair.key}</Typography>
                  </Button>)}
                </Stack>
              </Grid>
              <Grid item>
                {auth.roles.length > 0?
                  <Stack direction="row" spacing={2}>
                    <Button variant="contained" onClick={() => {navigate(paths.ROOT + "profile")}}>
                      <Typography>{user?.username}</Typography>
                    </Button>
                    <Button onClick={logOut} variant="contained">
                      <Typography>Logout</Typography>
                    </Button>
                  </Stack>
                  :
                  <Stack direction="row" spacing={2}>
                    <Button variant="contained" onClick={() => {navigate(paths.ROOT + "login")}}>
                      <Typography>Login</Typography>
                    </Button>
                    <Button onClick={() => navigate(paths.ROOT + "signUp")} variant="contained">
                      <Typography>Register</Typography>
                    </Button>
                  </Stack>                 
                }
              </Grid>
            </Grid>
          </Container>
        </Toolbar>
      </AppBar>
    </Box>
  );
};
