import React from 'react';
import './App.css';


//components
import AppHeader from "./Components/app-header/AppHeader";
import {useAppDispatch, useAppSelector} from "./app/hooks";
import {getApiData} from "./Slicer/QuizSlice";
import {CssBaseline, Toolbar} from "@mui/material";
import MainView from "./Components/main-view/MainView";
import {selectToken} from "./Slicer/AuthSlice";

function App() {
    console.log(localStorage.getItem("codificantesToken"))
    const token = useAppSelector(selectToken);
    const dispatch = useAppDispatch();
    token.length && dispatch(getApiData());
    return (
        <React.Fragment>
            <CssBaseline/>
            <AppHeader />
            <Toolbar/>
            <MainView />

        </React.Fragment>
    );
}

export default App;
