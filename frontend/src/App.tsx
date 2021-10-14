import React from 'react';
import './App.css';


//components
import AppHeader from "./Components/app-header/AppHeader";
import Quiz from "./Components/quiz/Quiz";
import {Route} from "react-router";
import CardCreationDialog from './Components/quizcard/card-creation-dialog/CardCreationDialog';
import AllCards from './Components/all-cards/AllCards';
import {useAppDispatch} from "./app/hooks";
import {getApiData} from "./Slicer/QuizSlice";
import Quizcard from "./Components/quizcard/Quizcard";
import {cardMode, IQuestionCard} from "./Interfaces/IQuestionCard";
import {Grid} from "@mui/material";


function App() {
    const dispatch = useAppDispatch();
    dispatch(getApiData());
    return (
        <div>
            <AppHeader></AppHeader>

        </div>
    );
}

export default App;
