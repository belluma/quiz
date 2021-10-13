import React, {useState} from 'react';


//component imports
import {Button, Card, CardActions, CardContent, CardHeader, Grid, IconButton} from "@mui/material";
import HelpIcon from '@mui/icons-material/Help';
import Choices from "../choices/Choices";
import CardCreationDialog from "../card-creation-dialog/CardCreationDialog";

//interface imports
import {cardMode, IQuestionCard} from "../../Interfaces/IQuestionCard";
import {useAppDispatch, useAppSelector} from "../../app/hooks";
import {answerCard} from "../../Slicer/QuizSlice";
import {selectQuestionText} from "../../Slicer/NewCardSlice";

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
    const questionText: string = useAppSelector(selectQuestionText);
    const submitAnswer = () => {
        console.log(checkAnswer() ? "correct" : "wrong");
        dispatch(answerCard(card))
    };
    return (
        <Card sx={{width: 500, height: 345 ,borderRadius:10, position:"relative"}}>
                    <CardHeader
                        component='h1'
                        sx={{bgcolor: 'primary.main'}}
                        avatar={<HelpIcon/>}
                        title={mode === cardMode.NEW ? `${questionText}?` : `${question}?`}
                        titleTypographyProps={{fontSize: 26}}
                    />
                    {mode === cardMode.NEW ? <CardCreationDialog questionText={questionText}/> :
                        <CardContent sx={{position:"absolute", bottom:"25px"}}>
                            <Choices choices={choices} mode={mode} selectAnswer={onSelectAnswer}/>
                        </CardContent>}
                    {mode === cardMode.QUIZ && <CardActions>
                        <Button onClick={submitAnswer} sx={{position:"absolute", bottom:0}}>submit answer</Button>
                    </CardActions>}
        </Card>
    )

    function checkAnswer() {
        return card.answerIndices.length === selected.length &&
            card.answerIndices.every(index => selected.indexOf(index) !== -1)
    }
}

export default Quizcard;
