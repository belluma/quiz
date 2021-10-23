import React from 'react'
import {portraitView, styleCardContent} from "../../../style-helpers/card";

//component imports
import {
    Card,
    CardContent,
    Typography
} from "@mui/material";
//interface imports

type Props = {
    quizname?: string
    offset: number
};

function FaceDownCard({quizname, offset}: Props) {
    return (
        <Card sx={{...portraitView(), position:"absolute", left:offset, top: offset}}>
            <CardContent sx={{...styleCardContent(), bottom: 150, textAlign: "center"}}>
                <Typography variant="h4">{quizname}</Typography>

            </CardContent>
        </Card>
    )
}

export default FaceDownCard;
