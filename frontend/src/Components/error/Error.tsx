import React from 'react'
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../quizcard/Quizcard";
import {useAppDispatch} from '../../app/hooks';

//component imports
import {Card, CardContent, CardHeader, Divider, ThemeProvider, Typography} from "@mui/material";
import CardFooter from "../quizcard/card-footer/CardFooter";
import {overrideBackgroundForError} from "../../theme";

//interface imports

type Props = {
    status:number,
    statusText:string,
};

function Error({status, statusText}: Props) {
    const dispatch = useAppDispatch();
    const clickHandler = () => {
    }
    return (
        <ThemeProvider theme={overrideBackgroundForError()}>
            <Card sx={makeCardChangeBetweenPortraitAndLandscape()}>
                <CardHeader title="Error!!1!" align="center"/>
                <Divider/>
                <CardContent sx={styleCardContent()}>
                    <Typography variant="h5">{statusText}</Typography>
                </CardContent>
                <CardFooter disableButton={false} footerText="Something went wrong " buttonText="OK"
                            onButtonClick={clickHandler}/>
            </Card>
        </ThemeProvider>
    )
}

export default Error;
