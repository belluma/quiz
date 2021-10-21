import React, {ChangeEvent, useState} from 'react'
import {useHistory} from "react-router";
import {overrideFontColorOnFocus} from "../../theme";
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../quizcard/Quizcard";
import {login as sendLogin} from '../../services/apiService'

//component imports
import {
    Button,
    Card,
    CardContent,
    CardHeader,
    Divider,
    FormGroup,
    TextField,
    ThemeProvider,
} from "@mui/material";
import CardFooter from "../quizcard/card-footer/CardFooter";

//interface imports

type Props = {};

function Login(props: Props) {
    const history = useHistory();
    const [username, setUsername]= useState<string>();
    const [password, setPassword] = useState<string>();

    const login = () => {
        if(username && password)
            sendLogin({username, password})
    }
    const enterUsername = (e: ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    }
const enterPassword = (e: ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    }


    return (
        <Card sx={makeCardChangeBetweenPortraitAndLandscape()}>
            <CardHeader title="Login to your account" align="center"/>
            <Divider/>
            <CardContent sx={styleCardContent("QUESTION")}>

                <FormGroup >
                    <ThemeProvider theme={overrideFontColorOnFocus()}>
                        <TextField onChange={enterUsername} sx={{my: 1}} required name="username" label="username" type="username"/>
                        <TextField onChange={enterPassword} sx={{my: 1}} required name="password"  type="password"/>
                        <Button type="submit" onClick={login}>Login</Button>
                    </ThemeProvider>
                </FormGroup>
            </CardContent>
            <CardFooter disableButton={false} footerText="The first time here? " buttonText="sign up"
                        onButtonClick={() => history.push('/signup')}/>
        </Card>
    )
}

export default Login;
