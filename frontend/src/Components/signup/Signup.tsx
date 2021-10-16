import React from 'react'
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../quizcard/Quizcard";
import {overrideFontColorOnFocus} from "../../theme";

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

//interface imports

type Props = {};

function Signup(props: Props) {
    return (
        <Card sx={makeCardChangeBetweenPortraitAndLandscape(true)}>
            <CardHeader title="Sing up for free to play Codificantes" align="center"/>
            <Divider/>
            <CardContent sx={styleCardContent("QUESTION")}>
                <FormGroup>
                    <ThemeProvider theme={overrideFontColorOnFocus()}>
                        <TextField sx={{my: 1}} required label="username" type="email"/>
                        <TextField sx={{my: 1}} required label="email address" type="email"/>
                        <TextField sx={{my: 1}} required label="Password" type="password"/>
                        <TextField sx={{my: 1}} required label="confirm password" type="password"/>
                        <Button type="submit">Sign up</Button>
                    </ThemeProvider>
                </FormGroup>
            </CardContent>
        </Card>
    )
}

export default Signup;
