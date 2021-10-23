import React from 'react'

import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../../../style-helpers/card";

//component imports
import {
    Card,
    CardContent,
    Typography
} from "@mui/material";
//interface imports

type Props = {
    quizname?:string
};

function FaceDownCard({quizname}: Props){
    return(
        <Card sx={makeCardChangeBetweenPortraitAndLandscape()}>
            <CardContent sx={{...styleCardContent(), bottom:150, textAlign:"center"}}>
<Typography variant="h4">{quizname}</Typography>

            </CardContent>
        </Card>
    )
}

export default FaceDownCard;
