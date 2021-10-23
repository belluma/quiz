import React, {useState} from 'react';
import {useAppDispatch} from "../../../app/hooks";
import {moveCardToAnseweredCardsStack, validateQuizcard} from "../../../Slicer/QuizSlice";

//component imports
import {Card, CardContent, Divider,} from "@mui/material";
import Choices from "./choices/Choices";
import QuizcardHeader from "./quizcard-header/QuizcardHeader";

//interface imports
import {cardMode, IQuestionCard} from "../../../Interfaces/IQuestionCard";
import CardFooter from "./card-footer/CardFooter";
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../../../style-helpers/card";


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
        dispatch(validateQuizcard(answer));
        setSelected([]);
        dispatch(moveCardToAnseweredCardsStack(card));
    };
    return (
        <Card sx={makeCardChangeBetweenPortraitAndLandscape()}>
            <QuizcardHeader title={question}/>
            <Divider/>
            <CardContent sx={styleCardContent()}>
                <Choices choices={choices} mode={mode} selectAnswer={onSelectAnswer} selected={selected}/>
            </CardContent>
            {mode === cardMode.QUIZ &&

            <CardFooter disableButton={!selected.length} onButtonClick={submitAnswer} buttonText="submit answer"/>}

        </Card>
    );
}



export default Quizcard;
