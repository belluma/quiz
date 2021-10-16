import React from 'react'
import {useHistory} from "react-router";
import {overrideFontColorOnFocus} from "../../theme";
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../quizcard/Quizcard";

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
    return (
        <Card sx={makeCardChangeBetweenPortraitAndLandscape()}>
            <CardHeader title="Login to your account" align="center"/>
            <Divider/>
            <CardContent sx={styleCardContent("QUESTION")}>
                <FormGroup>
                    <ThemeProvider theme={overrideFontColorOnFocus()}>
                        <TextField sx={{my: 1}} required label="email address" type="email"/>
                        <TextField sx={{my: 1}} required label="Password" type="password"/>
                        <Button type="submit">Login</Button>
                    </ThemeProvider>
                </FormGroup>
            </CardContent>
            <CardFooter disableButton={false} footerText="The first time here? " buttonText="sign up"
                        onButtonClick={() => history.push('/signup')}/>
        </Card>
    )
}

export default Login;
