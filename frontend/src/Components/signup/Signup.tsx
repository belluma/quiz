import React from 'react'
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../../style-helpers/card";
import {overrideFontColorOnFocus} from "../../theme";
import {selectLoggedIn} from "../../Slicer/AuthSlice";
import {useAppSelector} from "../../app/hooks";

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
import {Redirect} from "react-router";

//interface imports

type Props = {};

function Signup(props: Props) {
    const loggedIn = useAppSelector(selectLoggedIn);
    return (
        loggedIn ? <Redirect to="/quiz"/> :
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
