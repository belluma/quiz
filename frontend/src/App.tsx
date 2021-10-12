import React from 'react';
import './App.css';


//components
import StartView from "./Components/start-view/StartView";
import Quiz from "./Components/quiz/Quiz";
import {Route} from "react-router";
import CardCreationDialog from './Components/card-creation-dialog/CardCreationDialog';
import AllCards from './Components/all-cards/AllCards';
import {useAppDispatch} from "./app/hooks";
import {getApiData} from "./Slicer/QuizSlice";
import Quizcard from "./Components/card/Card";
import {cardMode, IQuestionCard} from "./Interfaces/IQuestionCard";
import {Grid} from "@mui/material";


function App() {
    const dispatch = useAppDispatch();
    dispatch(getApiData());

    const emptyCard: IQuestionCard = {id: -1, question: "", choices: [], answerIndices: []}
    const newCardProps = {card: emptyCard, mode: cardMode.NEW}

    return (
        <div>
            <StartView></StartView>
            <Grid container justifyContent="center" alignItems="center">

                <Route path="/quiz" component={Quiz}/>
                <Route path="/new" render={() => <Quizcard {...newCardProps} />}/>
                <Route path="/all" component={AllCards}/>
            </Grid>
        </div>
    );
}

export default App;
