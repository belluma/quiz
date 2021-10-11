import React from 'react';


//component imports
import {Avatar, Button, Card, CardActions, CardContent, CardHeader, IconButton} from "@mui/material";
import HelpIcon from '@mui/icons-material/Help';
import Choices from "../choices/Choices";
//interface imports
import {cardMode, IQuestionCard} from "../../Interfaces/IQuestionCard";

type Props = {
    card: IQuestionCard,
    mode: cardMode
}

function Quizcard({card, mode}: Props){
    const {question, choices, answerIndices} = card;
    const submitAnswer = () => {

    };
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
                <Choices choices={choices} mode={mode}/>
            </CardContent>
            {mode === cardMode.QUIZ && <CardActions>
                <Button onClick={submitAnswer}>submit answer</Button>
            </CardActions>}
        </Card>

    )
}

export default Quizcard;
