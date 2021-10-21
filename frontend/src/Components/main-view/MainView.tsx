import React from 'react'
import {Route} from "react-router";


//component imports
import {Container, Grid} from "@mui/material";
import Quiz from "../quiz/Quiz";
import CardCreationDialog from "../quizcard/card-creation-dialog/CardCreationDialog";
import AllCards from "../all-cards/AllCards";
import Login from "../login/Login";
import Signup from "../signup/Signup";
import Highscore from "../highscore/Highscore";
import {useAppSelector} from "../../app/hooks";
import {selectLoggedIn} from "../../Slicer/authSlice";
import ProtectedRoute from "../protected-route/ProtectedRoute";
//interface imports

type Props = {};

function MainView(props: Props){
    return(
        <Container sx={{pt: 15}} maxWidth={false}>
            <Grid container justifyContent="center" alignItems="center">
                <ProtectedRoute route={"/quiz"} component={Quiz} />
                <ProtectedRoute route={"/new"} component={CardCreationDialog}/>
                <ProtectedRoute route={"/all"} component={AllCards}/>
                <Route path="/login" component={Login}/>
                <Route path={"/signup"} component={Signup}/>
                <ProtectedRoute route={"/highscore"} component={Highscore}/>
            </Grid>
        </Container>
    )
}

export default MainView;
