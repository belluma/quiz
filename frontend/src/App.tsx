import React from 'react';
import logo from './logo.svg';
import {Counter} from './features/counter/Counter';
import './App.css';
import StartView from "./Components/start-view/StartView";
import Quiz from "./Components/quiz/Quiz";

function App() {
    return (
        <div>
            <StartView></StartView>
            <Quiz></Quiz>
        </div>
    );
}

export default App;
