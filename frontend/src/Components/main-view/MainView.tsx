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
//interface imports

type Props = {};

function MainView(props: Props){
    return(
        <Container sx={{pt: 15}} maxWidth={false}>
            <Grid container justifyContent="center" alignItems="center">
                <Route path="/quiz" component={Quiz}/>
                <Route path="/new" component={CardCreationDialog}/>
                <Route path="/all" component={AllCards}/>
                <Route path="/login" component={Login}/>
                <Route path="/signup" component={Signup}/>
                <Route path="/highscore" component={Highscore}/>

            </Grid>
        </Container>
    )
}

export default MainView;
