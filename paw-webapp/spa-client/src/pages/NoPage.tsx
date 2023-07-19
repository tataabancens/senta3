import { Grid, Paper, Typography } from "@mui/material";
import { FC } from "react";
import { themePalette } from "../config/theme.config";
import { useTranslation } from "react-i18next";


const NoPage: FC = () => {
    const { t } = useTranslation();
    return(
            <Grid container component={Paper} sx={{width: 500, height: 250, position: "absolute", top: "40%", right:"40%", backgroundColor: themePalette.PURPLE}} alignItems="center">
                <Grid item xs={12}>
                    <Typography variant="h5" color="white" align="center">{t('pageDoesNotExist')}</Typography>
                </Grid>
            </Grid>
    );
}

export default NoPage;