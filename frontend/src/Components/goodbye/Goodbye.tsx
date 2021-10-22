import React from 'react'
import {
    Card,
    CardContent,
    CardHeader,
    Divider,
    Typography
} from "@mui/material";
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../quizcard/Quizcard";

//component imports

//interface imports

type Props = {};

function Goodbye(props: Props) {
    return (
        <Card sx={makeCardChangeBetweenPortraitAndLandscape()}>
            <CardHeader title="Sing up for free to play Codificantes" align="center"/>
            <Divider/>
            <CardContent sx={{...styleCardContent(), bottom: 120, textAlign: 'center'}}>
                <Typography variant="h2">Goodbye!</Typography>
            </CardContent>
        </Card>
    )
}

export default Goodbye;
