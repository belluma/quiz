import React from 'react';


//component imports
import {Avatar, Card, CardContent, CardHeader, IconButton} from "@mui/material";
import HelpIcon from '@mui/icons-material/Help';
import Choices from "../choices/Choices";
//interface imports
import {IQuestionCard} from "../../Interfaces/IQuestionCard";

type Props = {}

function Quizcard({question, choices, answerIndices}: IQuestionCard){
    return(
        <Card sx={{ maxWidth: 345 }}>
            <CardHeader
                action={
                    <IconButton aria-label="settings">
                        <HelpIcon />
                    </IconButton>
                }
                title={question}
            />
            <CardContent>
                <Choices choices={choices}/>
            </CardContent>
        </Card>

    )
}

export default Quizcard;
