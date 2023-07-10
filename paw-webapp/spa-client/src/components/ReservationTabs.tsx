import { Grid, Tab, Tabs } from "@mui/material";
import { FC } from "react";
import { useTranslation } from "react-i18next";

type Props = {
    value: number;
    handleChange: (event: React.SyntheticEvent, newValue: number) => void;
};

const ReservationsTable: FC<Props> = ({value, handleChange}) =>{
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
    </>);
}

export default ReservationsTable;