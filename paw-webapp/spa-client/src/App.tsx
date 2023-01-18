import React from 'react';
import './App.css';
import NavBar from './components/NavBar';
import DishCard from './components/DishCard';
import {Box} from '@mui/material';


const dishList = [
  {dishName: "plato",
   dishPrice: 200,
   dishDescription: "lorem ipsum"},
  {dishName: "plato2",
   dishPrice: 300,
   dishDescription: "lorem ipsum"},
  {dishName: "plato3",
  dishPrice: 215,
  dishDescription: "lorem ipsum"}];


function App() {
  return (
    <div className="App">
        <NavBar />
        <Box sx={{ 
          display: 'flex',
          flexDirection: {xs: "column", md: "row"},
          justifyContent: "space-between",
          gap: 4}}
        >
          {dishList.map((dish) => DishCard(dish))}
        </Box>
    </div>
  );
}

export default App;
