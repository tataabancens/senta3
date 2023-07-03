import { Grid, Stack, Switch, Tab, Tabs, Typography } from "@mui/material";
import { FC } from "react";
import { useTranslation } from "react-i18next";

type Props = {
    value: number;
    handleChange: (event: React.SyntheticEvent, newValue: number) => void;
    sortDirection: boolean;
    handleSwitchChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
};

const ReservationsTable: FC<Props> = ({value, handleChange, sortDirection, handleSwitchChange}) =>{
    const { t } = useTranslation();
    return (
    <>
        <Grid item xs={10}>
          <Tabs
            value={value}
            onChange={handleChange}
            variant="scrollable"
            scrollButtons="auto"
            aria-label="scrollable auto tabs example"
          >
          <Tab value={9} label={t('reservationsPage.tabs.all')} />
          <Tab value={0} label={t('reservationsPage.tabs.open')} />
          <Tab value={1} label={t('reservationsPage.tabs.seated')} />
          <Tab value={2} label={t('reservationsPage.tabs.checkOrdered')}/>
          <Tab value={3} label={t('reservationsPage.tabs.finished')} />
          <Tab value={4} label={t('reservationsPage.tabs.cancelled')} />
          </Tabs>
        </Grid>
        <Grid item container xs={2} justifyContent="flex-start">
          <Stack direction="row" alignItems="center" spacing={3}>
            <Typography variant="subtitle1">{t('reservationsPage.sortBy')}</Typography>
            <Stack direction="row" spacing={2} alignItems="center">
              <Typography>Asc</Typography>
              <Switch checked={sortDirection} onChange={handleSwitchChange} color="primary" />
              <Typography>Desc</Typography>
            </Stack>
          </Stack>
        </Grid>
    </>);
}

export default ReservationsTable;