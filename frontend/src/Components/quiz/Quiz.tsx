import React from 'react'
import {selectGetAllCards, selectGetAnsweredCards} from '../../Slicer/QuizSlice';
import {useAppSelector} from "../../app/hooks";

//component imports
import {Grid} from "@mui/material";
import Quizcard from "../card/Card";
import {IQuestionCard} from "../../Interfaces/IQuestionCard";

//interface imports

type Props = {};


function Quiz(props: Props){
    const allCards = useAppSelector(selectGetAllCards);
    const answeredCards = useAppSelector(selectGetAnsweredCards);
    const cardsToAnswer = allCards.filter(card => answeredCards.indexOf(card) === -1);
    const nextCard: IQuestionCard = cardsToAnswer[Math.floor(Math.random() * cardsToAnswer.length)]
    return(
       <Grid container justifyContent="center" alignItems="center">
           <Grid item>
               {nextCard &&<Quizcard card={nextCard} mode={"quiz"} />}
           </Grid>
       </Grid>
    )
}

export default Quiz;
