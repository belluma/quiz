import React from 'react'
import {useAppSelector} from "../../app/hooks";
import {selectGetAllCards} from "../../Slicer/QuizSlice";

//component imports
import Quizcard from "../quizcard/Quizcard";

//interface imports
import {cardMode} from "../../Interfaces/IQuestionCard";
import {Grid} from "@mui/material";

type Props = {};

function AllCards(props: Props){
    const allCards = useAppSelector(selectGetAllCards);
    const cards = allCards.map(card => <Grid item key={card.id}><Quizcard card={card} mode={cardMode.ALL}/></Grid>)
    return(
       <Grid container spacing={2} justifyContent="space-between">
           {cards}
       </Grid>

    )
}

export default AllCards;
