import React from 'react'
import {useAppSelector} from "../../app/hooks";
import {selectGetAllCards} from "../../Slicer/QuizSlice";
import Quizcard from "../card/Card";

//component imports

//interface imports
import {cardMode} from "../../Interfaces/IQuestionCard";

type Props = {};

function AllCards(props: Props){
    const allCards = useAppSelector(selectGetAllCards);
    const cards = allCards.map(card => <Quizcard card={card} mode={cardMode.ALL} key={card.id}/>)
    return(
       <div>{cards}</div>
    )
}

export default AllCards;
