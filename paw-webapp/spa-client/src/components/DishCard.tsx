import {Paper, Typography, Box} from "@mui/material";

interface Dish{
    id: number,
    name: string,
    self: string,
    restaurant: string,
    image: string,
    category: string,
    description: string,
    price: number
}


function DishCard({
    id,
    name,
    self,
    restaurant,
    image,
    category,
    description,
    price
}: Dish){
    return (
        <Paper elevation = {3} sx={{width: {xs: 1, md: 320}}}>
            <Box sx={{ m: 3}}>
                <Typography variant="h3">{name}</Typography>
                <Typography sx={{mt: 2}}>{description}</Typography>
                <Typography sx={{mt: 2}}>{price}</Typography>
            </Box>
        </Paper>
    );
}

export default DishCard;