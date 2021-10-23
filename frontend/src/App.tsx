import React from 'react';
import './App.css';


//components
import AppHeader from "./Components/app-header/AppHeader";
import {useAppDispatch, useAppSelector} from "./app/hooks";
import {getApiData} from "./Slicer/QuizSlice";
import {CssBaseline, Toolbar} from "@mui/material";
import MainView from "./Components/main-view/MainView";
import {loginFromStorage, selectToken} from "./Slicer/AuthSlice";

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
