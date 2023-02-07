import {
  Box,
  Button,
  Checkbox,
  FormControlLabel,
  Grid,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import { useEffect, useRef, useState} from "react";
import { Link, Navigate, useLocation, useNavigate } from "react-router-dom";
import Image from "../commons/restaurantPicture.jpg";
import axios, { AxiosResponse } from "axios";
import { paths } from "../constants/constants";
import { fromByteArray, toByteArray } from 'base64-js';
import useAuth from "../hooks/useAuth";



const LoginPage = () => {

  const { auth, setAuth } = useAuth();

  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/"

  const userRef = useRef<HTMLInputElement>();
  const errRef = useRef();

  const [user, setUser] = useState('');
  const [pwd, setPwd] = useState('');
  const [errMsg, setErrMsg] = useState('');

  useEffect(() => {
    userRef.current?.focus();
  }, []);

  useEffect(() => {
    setErrMsg('');
  }, [user, pwd]);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const path = `${paths.LOCAL_BASE_URL}/users/auth`;

    const encoder = new TextEncoder();
    const base64Credentials = fromByteArray(encoder.encode(`${user}:${pwd}`));

    try {
      const response = await axios.get(path,
        {
          headers: {
            'Authorization': `Basic ${base64Credentials}`
          }
        }
      );

      const authorization: string | undefined = response?.headers.authorization;
      const role = response?.data?.role;
      const roles: string[]= [role];
      const id = response?.data?.id;
      
      setAuth({user, roles, authorization, id});
      console.log(auth);
      setUser('');
      setPwd('');
      navigate(from, { replace : true });

    } catch (err: any) {
        if(!err?.response) {
          setErrMsg("No server response");
        } else if (err.response?.status === 400) {
          setErrMsg("Missing username or password");
        } else if (err.response?.status === 401) {
          setErrMsg("Unauthorized");
        } else {
          setErrMsg("Login failed");
        }
    }


  };

  return (
    <Grid container component="main" sx={{ height: "100vh" }}>
      <Grid
        item
        xs={false}
        sm={6}
        md={9}
        sx={{
          backgroundImage: `url(${Image})`,
          backgroundRepeat: "no-repeat",
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      />
      <Grid item xs={12} sm={6} md={3} component={Paper} elevation={6} square>
        <Box
          sx={{
            my: 8,
            mx: 4,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Typography component="h1" variant="h5">
            Login
          </Typography>
          <Box
            component="form"
            noValidate
            onSubmit={handleSubmit}
            sx={{ mt: 1 }}
          >
            <TextField
              inputRef={userRef}
              margin="normal"
              required
              fullWidth
              id="username"
              label="Username"
              name="username"
              autoComplete="username"
              onChange={(e) => setUser(e.target.value)}
              value={user}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              onChange={(e) => setPwd(e.target.value)}
              value={pwd}
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="Remember me"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign In
            </Button>
            <Grid
              container
              sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                mt: 1,
              }}
              spacing={2}
            >
              <Grid item xs>
                <Link
                  to="/*"
                  style={{ textDecoration: "none", color: "black" }}
                >
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link
                  to="/signup"
                  style={{ textDecoration: "none", color: "black" }}
                >
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Grid>
    </Grid>
  );
}

export default LoginPage;
