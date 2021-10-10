import React from 'react'
import {useAppSelector} from "../../app/hooks";
import {selectGetAllCards} from "../../Slicer/QuizSlice";
import Quizcard from "../card/Card";

//component imports

//interface imports

type Props = {};

function AllCards(props: Props){
    const allCards = useAppSelector(selectGetAllCards);
    const cards = allCards.map(card => <Quizcard question={card.question} choices={card.choices} answerIndices={card.answerIndices} />)
    return(
       <div>{cards}</div>
    )
}

export default AllCards;
