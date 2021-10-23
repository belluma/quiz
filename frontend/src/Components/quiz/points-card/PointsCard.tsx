import React from 'react'
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../../../style-helpers/card";

//component imports
import {Card, CardContent, Divider, Typography} from "@mui/material";
import QuizcardHeader from "../../quizcard/quizcard-header/QuizcardHeader";


//interface imports

type Props = {
    points: number,
    cardCount: number,
};

function PointsCard({points, cardCount}: Props){
    return(
        <Card sx={makeCardChangeBetweenPortraitAndLandscape()}>
            <QuizcardHeader title="username"/>
            <Divider />
            <CardContent sx={styleCardContent()}>
                <Typography variant="h5" component="h5" align="center" >You answered {points} of {cardCount} cards correct!</Typography>
            </CardContent>

        </Card>
    )
}

export default PointsCard;
