import { Button, Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { FC } from "react";
import { OrderItemModel } from "../models";

type Props = {
    orderItems: OrderItemModel[];
    actionFunction : (item: OrderItemModel) => void,
    processStage: string 
};
  
const KitchenTable: FC<Props> = ({orderItems, actionFunction, processStage}) => {

    return(
        <Grid item xs={12} paddingX={2}>
        {orderItems.length === 0 &&
            <Typography align="center" variant="h6">There are not dishes here</Typography>
        }
        {orderItems.length > 0 &&
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Dish</TableCell>
                        <TableCell>Quantity</TableCell>
                        <TableCell>Table</TableCell>
                        <TableCell></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {orderItems.map(item => 
                        <TableRow>
                            <TableCell>{item.dishName}</TableCell>
                            <TableCell align="center">{item.quantity}</TableCell>
                            <TableCell align="center">{item.tableNmbr}</TableCell>
                            <TableCell align="center">
                                <Button size="small" variant="outlined" onClick={() => actionFunction(item)}>
                                    { processStage === "ORDERED" ? "Cook" : processStage === "INCOMING"? "Deliver" : "Done"}  
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
