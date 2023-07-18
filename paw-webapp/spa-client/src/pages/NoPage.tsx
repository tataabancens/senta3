import { Grid, Paper, Typography } from "@mui/material";
import { FC } from "react";
import { themePalette } from "../config/theme.config";


const NoPage: FC = () => {
    return(
            <Grid container component={Paper} sx={{width: 500, height: 250, position: "absolute", top: "40%", right:"40%", backgroundColor: themePalette.PURPLE}} alignItems="center">
                <Grid item xs={12}>
                    <Typography variant="h5" color="white" align="center">404: The route requested does not exist</Typography>
                </Grid>
            </Grid>
    );
}

export default NoPage;