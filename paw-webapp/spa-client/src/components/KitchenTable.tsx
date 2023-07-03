import { Button, Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { OrderItemModel } from "../models";

type Props = {
    orderItems: OrderItemModel[];
    actionFunction : (item: OrderItemModel) => void,
    processStage: string 
};
  
const KitchenTable: FC<Props> = ({orderItems, actionFunction, processStage}) => {

    const { t } = useTranslation();

    return(
        <Grid item xs={12} paddingX={2}>
        {orderItems.length === 0 &&
            <Typography align="center" variant="h6">{t('kitchenPage.emptyTable')}</Typography>
        }
        {orderItems.length > 0 &&
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>{t('kitchenPage.kitchenHeaders.dish')}</TableCell>
                        <TableCell>{t('kitchenPage.kitchenHeaders.qty')}</TableCell>
                        <TableCell>{t('kitchenPage.kitchenHeaders.table')}</TableCell>
                        <TableCell></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {orderItems.map((item,i) => 
                        <TableRow key={i}>
                            <TableCell>{item.dishName}</TableCell>
                            <TableCell align="center">{item.quantity}</TableCell>
                            <TableCell align="center">{item.tableNmbr}</TableCell>
                            <TableCell align="center">
                                <Button size="small" variant="outlined" onClick={() => actionFunction(item)}>
                                    { processStage === "ORDERED" ? t('kitchenPage.cookAction') : processStage === "INCOMING"? t('kitchenPage.deliverAction') : t('kitchenPage.doneAction')}  
                                </Button>
                            </TableCell>
                        </TableRow>    
                    )}
                </TableBody>
            </Table>
        }
        </Grid> 
    );
}

export default KitchenTable;
