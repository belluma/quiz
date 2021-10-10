import React from 'react';


//component imports
import {Avatar, Card, CardContent, CardHeader, IconButton} from "@mui/material";
import HelpIcon from '@mui/icons-material/Help';
import Choices from "../choices/Choices";
//interface imports
import {IQuestionCard} from "../../Interfaces/IQuestionCard";

type Props = {}

function Quizcard({question, answers, correctAnswers}: IQuestionCard){
    return(
        <Card sx={{ maxWidth: 345 }}>
            <CardHeader
                action={
                    <IconButton aria-label="settings">
                        <HelpIcon />
                    </IconButton>
                }
                title="Shrimp and Chorizo Paella"
                subheader="September 14, 2016"
            />
            <CardContent>
                <Choices />
            </CardContent>
        </Card>

    )
}

export default Card;
