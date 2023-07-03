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
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import {emptyAuth, paths} from "../constants/constants";
import useAuth from "../hooks/useAuth";

interface nameSitePair{
  key: string,
  value: string
}

export const NavBar: React.FC<{}> = () => {
  let navigate = useNavigate();
  const { auth, setAuth } = useAuth();
  const [rolePages, setRolePages] = useState<nameSitePair[]>([]);
  const { t } = useTranslation();

  const customerPages = new Map<string, string>([
    [t('navBar.customerPages.reservations'),"reservations"],
  ]);
  const restaurantPages = new Map<string, string>([
    [t('navBar.restaurantPages.menu'), "restaurantMenu"],
    [t('navBar.restaurantPages.kitchen'), "kitchen"],
    [t('navBar.restaurantPages.reservations'), "restaurantReservations"]
  ]);

  const logOut = () => {
    setAuth(emptyAuth);
    navigate(paths.ROOT);
  } 

  useEffect(() => {
    if(auth.roles[0] === "ROLE_CUSTOMER"){
      const pages: nameSitePair[] = Array.from(customerPages, ([key, value]) => ({key, value}));
      setRolePages(pages);
    }
    else if(auth.roles[0] === "ROLE_RESTAURANT"){
      const pages: nameSitePair[] = Array.from(restaurantPages, ([key, value]) => ({key, value}));
      setRolePages(pages);
    }
    else{
      setRolePages([]);
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
                  <Button variant="contained" key={index} onClick={() => {navigate(`${paths.ROOT}/${pair.value}`)}}>
                    <Typography>{pair.key}</Typography>
                  </Button>)}
                </Stack>
              </Grid>
              <Grid item>
                {auth.roles.length > 0?
                  <Stack direction="row" spacing={2}>
                    <Button variant="contained" onClick={() => {navigate(paths.ROOT + "/profile")}}>
                      <Typography>{auth?.user}</Typography>
                    </Button>
                    <Button onClick={logOut} variant="contained">
                      <Typography>{t('navBar.logoutButton')}</Typography>
                    </Button>
                  </Stack>
                  :
                  <Stack direction="row" spacing={2}>
                    <Button size="large" variant="contained" onClick={() => {navigate(paths.ROOT + "/login")}}>
                      <Typography>{t('navBar.loginButton')}</Typography>
                    </Button>
                    <Button size="large" onClick={() => navigate(paths.ROOT + "/signUp")} variant="contained">
                      <Typography>{t('navBar.registerButton')}</Typography>
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
