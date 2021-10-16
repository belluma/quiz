import React from 'react'
import {selectGetAllCards, selectGetAnsweredCards, selectPoints} from '../../Slicer/QuizSlice';
import {useAppSelector} from "../../app/hooks";

//component imports
import {CardContent, Divider, Grid} from "@mui/material";
import Quizcard, {styleCardContent} from "../quizcard/Quizcard";

//interface imports
import {cardMode, IQuestionCard} from "../../Interfaces/IQuestionCard";
import QuizcardHeader from "../quizcard/quizcard-header/QuizcardHeader";
import Choices from "../quizcard/choices/Choices";
import PointsCard from "./points-card/PointsCard";

type Props = {};


function Quiz(props: Props) {
    const allCards = useAppSelector(selectGetAllCards);
    const answeredCards = useAppSelector(selectGetAnsweredCards);
    const points = useAppSelector(selectPoints);

    const cardsToAnswer = allCards.filter(card => answeredCards.indexOf(card) === -1);
    const nextCard: IQuestionCard = cardsToAnswer[Math.floor(Math.random() * cardsToAnswer.length)]
    return (
        <Grid item>
            {nextCard ? <Quizcard card={nextCard} mode={cardMode.QUIZ}/> :
                <PointsCard points={points} cardCount={allCards.length}/>}
        </Grid>
    )
}

export default Quiz;
