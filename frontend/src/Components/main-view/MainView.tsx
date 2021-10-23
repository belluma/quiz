import React from 'react'


//component imports
import {Container, Grid} from "@mui/material";
import Quiz from "../quiz/Quiz";
import CardCreationDialog from "../quizcard/card-creation-dialog/CardCreationDialog";
import AllCards from "../all-cards/AllCards";
import Login from "../login/Login";
import Signup from "../signup/Signup";
import Highscore from "../highscore/Highscore";
import ProtectedRoute from "./protected-route/ProtectedRoute";
import Error from '../error/Error'
import Goodbye from "../goodbye/Goodbye";
import {Route} from "react-router";
import Deck from "../deck/Deck";

//interface imports

type Props = {};

function MainView(props: Props){
    return(
        <Container sx={{pt: 15}} maxWidth={false}>
            <Grid container justifyContent="center" alignItems="center">
                <Route path={"/logout"} component={Goodbye} />
                <Route path="/login" component={Login}/>
                <Route path={"/signup"} component={Signup}/>
                <ProtectedRoute route={"/quiz"} component={Quiz} />
                <ProtectedRoute route={"/new"} component={CardCreationDialog}/>
                <ProtectedRoute route={"/all"} component={AllCards}/>
                <ProtectedRoute route={"/highscore"} component={Highscore}/>
                <ProtectedRoute route={"/"} component={Deck} />
                <Error/>
            </Grid>
        </Container>
    )
}

export default MainView;
