import React from 'react';
import './App.css';


//components
import AppHeader from "./Components/app-header/AppHeader";
import {useAppDispatch} from "./app/hooks";
import {getApiData} from "./Slicer/QuizSlice";

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
