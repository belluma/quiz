import React, {useState} from 'react';
import {useAppDispatch} from "../../app/hooks";
import {moveCardToAnseweredCardsStack, validateQuizcard} from "../../Slicer/QuizSlice";

//component imports
import {Card, CardContent, Divider,} from "@mui/material";
import Choices from "./choices/Choices";
import QuizcardHeader from "./quizcard-header/QuizcardHeader";

//interface imports
import {cardMode, IQuestionCard} from "../../Interfaces/IQuestionCard";
import CardFooter from "./card-footer/CardFooter";


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

            <CardFooter disableButton={false} onButtonClick={submitAnswer} buttonText="submit answer"/>}

        </Card>
    )
}

export function makeCardChangeBetweenPortraitAndLandscape(signup: boolean = false) {
    return {
        height: {
            xs: 500,
            sm: signup ? 500 : 345
        }, width: {
            xs: 345, sm: 500
        }, borderRadius: 10, position: "relative"
    } as const;
}

export function styleCardContent(dialogStatus = "") {
    const styles = {
        position: "absolute",
        bottom: 50,
        width: "100%",
        bgcolor: 'primary.main',
        color: 'primary.contrastText'
    } as const;
    const {bottom, position, ...qStyles} = {...styles} as const;
    return dialogStatus === "QUESTION" ? qStyles : styles;
}

export default Quizcard;
