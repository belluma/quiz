import React from 'react'


//component imports
import {Container, Grid} from "@mui/material";
import Quiz from "../quiz/Quiz";
import CardCreationDialog from "../quiz/quizcard/card-creation-dialog/CardCreationDialog";
import AllCards from "../quiz/all-cards/AllCards";
import Login from "../security/login/Login";
import Signup from "../security/signup/Signup";
import Highscore from "../quiz/highscore/Highscore";
import ProtectedRoute from "./protected-route/ProtectedRoute";
import Error from '../error/Error'
import Goodbye from "../security/goodbye/Goodbye";
import {Route, Switch} from "react-router";
import Deck from "../quiz/deck/Deck";

//interface imports

type Props = {};

function MainView(props: Props) {
    return (
        <Container sx={{pt: 15}} maxWidth={false}>
            <Grid container justifyContent="center" alignItems="center">
                <Switch>
                    <Route path={"/logout"} component={Goodbye}/>
                    <Route path={"/login"} component={Login}/>
                    <Route path={"/signup"} component={Signup}/>
                    <ProtectedRoute path={"/new"} component={CardCreationDialog}/>
                    <ProtectedRoute path={"/quiz"} component={Quiz}/>
                    <ProtectedRoute path={"/all"} component={AllCards}/>
                    <ProtectedRoute path={"/highscore"} component={Highscore}/>
                    <ProtectedRoute path={"/"} component={Deck}/>
                </Switch>
                <Error/>
            </Grid>
        </Container>
    )
}

export default MainView;
