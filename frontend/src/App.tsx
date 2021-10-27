import React from 'react';
import {useAppDispatch, useAppSelector} from "./app/hooks";
import {loginFromStorage, selectToken} from "./Slicer/AuthSlice";
import {getApiData} from "./Slicer/QuizSlice";
import './App.css';

//components
import AppHeader from "./Components/app-header/AppHeader";
import {CssBaseline, Toolbar} from "@mui/material";
import MainView from "./Components/main-view/MainView";

function App() {
    const dispatch = useAppDispatch();
    dispatch(loginFromStorage());
    const token = useAppSelector(selectToken);
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
