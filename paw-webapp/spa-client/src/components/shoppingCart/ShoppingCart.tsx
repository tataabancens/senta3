import { Box, Dialog, DialogContent, DialogTitle, Grid, IconButton, Tab, Tabs, Typography } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import { FC, useState } from "react";
import { OrderItemModel } from "../../models";
import { shoppingCartTabs } from "../../constants/constants";
import OrdersPanel from "./OrdersPanel";
import ShoppingCartPanel from "./ShoppingCartPanel";

type Props = {
    orderItems: OrderItemModel[];
    isOpen: boolean;
    toggleCart: () => void;
    toggleReload?: () => void;
};


const ShoppingCart: FC<Props> =({orderItems, isOpen, toggleCart, toggleReload}) => {

    const [value, setValue] = useState(0);
    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    return(
        <Dialog open={isOpen} maxWidth="xl" onClose={toggleCart} PaperProps={{sx: { height: 700}}}>
          <Grid container component={Box} sx={{width: 1400}} padding={1}>
            <Grid item xs={12} sx={{display:"flex", justifyContent:"flex-end"}}>
                <IconButton onClick={toggleCart}>
                    <CloseIcon  color="primary"/>
                </IconButton>
            </Grid>
            <Grid item component={DialogTitle} xs={12} sx={ {display:"flex", justifyContent:"center", marginBottom: 2}}><Typography variant="h5">{shoppingCartTabs[value]}</Typography></Grid>
            <Grid item container component={DialogContent}>
                <Grid item xs={12} component={Box} sx={{ borderBottom: 1, borderColor: 'divider' }}>
                    <Tabs value={value} onChange={handleChange} >
                        {shoppingCartTabs.map((tab: string, i: number) => <Tab label={tab} value={i} key={i} />)}
                    </Tabs>
                </Grid>
                <Grid item xs={12}>
                <ShoppingCartPanel value={value} index={0} orderItems={orderItems} toggleReload={toggleReload}/>
                <OrdersPanel value={value} index={1} orderItems={orderItems} />
                </Grid>            
            </Grid>
         </Grid>
        </Dialog>
    );
}

export default ShoppingCart;