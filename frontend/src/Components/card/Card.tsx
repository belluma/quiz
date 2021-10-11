import React from 'react';


//component imports
import {Avatar, Button, Card, CardActions, CardContent, CardHeader, IconButton} from "@mui/material";
import HelpIcon from '@mui/icons-material/Help';
import Choices from "../choices/Choices";
//interface imports
import {IQuestionCard} from "../../Interfaces/IQuestionCard";

type Props = {
    card: IQuestionCard,
    mode: "show result" | "quiz" | "show all"
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
                <Choices choices={choices}/>
            </CardContent>
            {mode === "quiz" && <CardActions>
                <Button onClick={submitAnswer}>submit answer</Button>
            </CardActions>}
        </Card>

    )
}

export default Quizcard;
