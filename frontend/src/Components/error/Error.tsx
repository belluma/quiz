import React from 'react'
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../quizcard/Quizcard";
import {useAppDispatch, useAppSelector} from '../../app/hooks';
import {closeError, selectError,  selectStatusText} from "../../Slicer/ErrorSlice";

//component imports
import {Card, CardContent, CardHeader, Dialog, Divider, ThemeProvider, Typography} from "@mui/material";
import CardFooter from "../quizcard/card-footer/CardFooter";
import {overrideBackgroundForError} from "../../theme";

//interface imports

type Props = {
};

function Error(props: Props) {
     const error = useAppSelector(selectError)
     const statusText = useAppSelector(selectStatusText)
    const dispatch = useAppDispatch();
    const clickHandler = () => {
        dispatch(closeError());
    }
    return (
        <Dialog open={error}>
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
        </Dialog>
    )
}

export default Error;
