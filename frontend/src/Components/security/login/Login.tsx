import React, {ChangeEvent, useState} from 'react'
import {Redirect, useHistory} from "react-router";
import {overrideFontColorOnFocus} from "../../../theme";
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../../../style-helpers/card";
import {login as sendLogin, selectLoggedIn} from '../../../Slicer/AuthSlice'

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
import CardFooter from "../../quiz/quizcard/card-footer/CardFooter";
import {IUser} from "../../../Interfaces/IUser";
import {useAppDispatch, useAppSelector} from "../../../app/hooks";

//interface imports

type Props = {};

function Login(props: Props) {
    const loggedIn = useAppSelector(selectLoggedIn);
    const dispatch = useAppDispatch();
    const history = useHistory();
    const [credentials, setCredentials] = useState<IUser> ({});
    const login = () => {
        credentials && dispatch(sendLogin(credentials));
    }
    const onInput = (e: ChangeEvent<HTMLInputElement>) => {
        setCredentials({...credentials, [e.target.name]:e.target.value })
    }
    const disableLogin = (!credentials.password || !credentials.username);

    return (
        loggedIn ? <Redirect to="/quiz"/> :
        <Card sx={makeCardChangeBetweenPortraitAndLandscape()}>
            <CardHeader title="Login to your account" align="center"/>
            <Divider/>
            <CardContent sx={styleCardContent("QUESTION")}>

                <FormGroup >
                    <ThemeProvider theme={overrideFontColorOnFocus()}>
                        <TextField  onChange={onInput} sx={{my: 1}} required name="username" label="username" type="username"/>
                        <TextField onChange={onInput} sx={{my: 1}} required name="password"  type="password"/>
                        <Button disabled={disableLogin} type="submit" onClick={login}>Login</Button>
                    </ThemeProvider>
                </FormGroup>
            </CardContent>
            <CardFooter disableButton={false} footerText="The first time here? " buttonText="sign up"
                        onButtonClick={() => history.push('/signup')}/>
        </Card>
    )
}

export default Login;
