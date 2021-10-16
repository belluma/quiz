import React, {useState} from 'react';
import {useAppDispatch} from "../../app/hooks";
import {moveCardToAnseweredCardsStack} from "../../Slicer/QuizSlice";

//component imports
import {Button, Card, CardActions, CardContent, Divider,} from "@mui/material";
import Choices from "./choices/Choices";
import QuizcardHeader from "./quizcard-header/QuizcardHeader";

//interface imports
import {cardMode,  IQuestionCard} from "../../Interfaces/IQuestionCard";
import {validateAnswer} from '../../services/apiService';


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
    const {id, question, choices} = card;
    const submitAnswer = () => {
        const answer: IQuestionCard = {id, question, choices, answerIndices: selected}
        validateAnswer(answer)
            .then(response => {
                console.log(response)
            })
        setSelected([]);
        dispatch(moveCardToAnseweredCardsStack(card));
    };
    return (
        <Card sx={styleCard()}>
            <QuizcardHeader title={question}/>
            <Divider />
            <CardContent sx={styleCardContent()}>
                <Choices choices={choices} mode={mode} selectAnswer={onSelectAnswer} selected={selected}/>
            </CardContent>
            {mode === cardMode.QUIZ &&
            <CardActions>
                <Button onClick={submitAnswer} sx={{position: "absolute", bottom: 0}}>submit answer</Button>
            </CardActions>}
        </Card>
    )
}

export function styleCard() {
    return {
        height: {
            xs: 500,
            sm: 345
        }, width: {
            xs: 345, sm: 500
        }, borderRadius: 10, position: "relative"
    } as const;
}

export function styleCardContent(dialogStatus = "") {
    const styles = {position: "absolute", bottom: 40, width: "100%", bgcolor:'primary.main', color:'primary.contrastText'} as const;
    const {bottom, position,  ...qStyles} = {...styles} as const;
    return dialogStatus === "QUESTION" ? qStyles : styles;
}

export default Quizcard;
