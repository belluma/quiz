import React from 'react';
import './App.css';


//components
import StartView from "./Components/start-view/StartView";
import Quiz from "./Components/quiz/Quiz";
import {Route} from "react-router";
import CardCreationDialog from './Components/card-creation-dialog/CardCreationDialog';
import AllCards from './Components/all-cards/AllCards';
import {useAppDispatch} from "./app/hooks";
import {getApiData} from "./Components/quiz/QuizSlice";


function App() {
    const dispatch = useAppDispatch();
    dispatch(getApiData());

    return (
        <div>
            <StartView></StartView>
            <Route path="/quiz" component={Quiz} />
            <Route path="/new" component={CardCreationDialog} />
            <Route path="/all" component={AllCards} />
            {/*<Quiz></Quiz>*/}
        </div>
    );
}

export default App;
