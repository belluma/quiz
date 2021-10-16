import React from 'react'

//component imports
import {Card, CardContent, CardHeader, Divider, FormGroup, TextField, ThemeProvider, Toolbar} from "@mui/material";
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../quizcard/Quizcard";
import {overrideFontColorOnFocus} from "../../theme";
import CardFooter from "../quizcard/card-footer/CardFooter";
import {useHistory} from "react-router";

//interface imports

type Props = {};

function Login(props: Props){
    const history = useHistory();
    return(
       <Card sx={makeCardChangeBetweenPortraitAndLandscape()}>
           <CardHeader title="Login to your account" align="center"/>
           <Divider />
           <CardContent sx={styleCardContent()}>
               <FormGroup>
                   <ThemeProvider theme={overrideFontColorOnFocus()}>
                   <TextField required label="email address" type="email" />
                   <Toolbar />
                   <TextField required label="Password" type="password" />
               </ThemeProvider>
               </FormGroup>
           </CardContent>
<CardFooter disableButton={false}  footerText="The first time here? " buttonText="sign up" onButtonClick={() => history.push('/signup') }/>
       </Card>
    )
}

export default Login;
