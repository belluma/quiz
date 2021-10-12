import React, {useState} from 'react';


//component imports
import {Button, Card, CardActions, CardContent, CardHeader, IconButton} from "@mui/material";
import HelpIcon from '@mui/icons-material/Help';
import Choices from "../choices/Choices";
import CardCreationDialog from "../card-creation-dialog/CardCreationDialog";

//interface imports
import {cardMode, IQuestionCard} from "../../Interfaces/IQuestionCard";
import {useAppDispatch} from "../../app/hooks";
import {answerCard} from "../../Slicer/QuizSlice";

type Props = {
    card: IQuestionCard,
    mode: cardMode
}

function Quizcard({card, mode}: Props) {
    const dispatch = useAppDispatch();
    const [selected, setSelected] = useState<number[]>([]);
    const onSelectAnswer = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSelected([+e.target.value])
    };
    const {question, choices, answerIndices} = card;
    const submitAnswer = () => {
        console.log(checkAnswer() ? "correct" : "wrong");
        dispatch(answerCard(card))
    };
    return (
        <Card sx={{maxWidth: 345}}>
            <CardHeader
                action={
                    <IconButton aria-label="settings">
                        <HelpIcon/>
                    </IconButton>
                }
                title={`${question}?`}
            />
            {mode === cardMode.NEW ? <CardCreationDialog/> :
                <CardContent>
                    <Choices choices={choices} mode={mode} selectAnswer={onSelectAnswer}/>
                </CardContent>}
            {mode === cardMode.QUIZ && <CardActions>
                <Button onClick={submitAnswer}>submit answer</Button>
            </CardActions>}
        </Card>
    )

    function checkAnswer() {
        return card.answerIndices.length === selected.length &&
            card.answerIndices.every(index => selected.indexOf(index) !== -1)
    }
}

export default Quizcard;
