import React from 'react'
import {useAppSelector} from "../../app/hooks";
import {selectGetAllCards, selectGetAnsweredCards} from "../../Slicer/QuizSlice";

//component imports
import FaceDownCard from "./face-down-card/FaceDownCard";
import { Grid} from "@mui/material";

//interface imports

type Props = {
    quizname?:string
};

function Deck({quizname}: Props){
    const remainingCards = useAppSelector(selectGetAllCards).length - useAppSelector(selectGetAnsweredCards).length;
const cardsToBeRendered = remainingCards < 10 ? remainingCards : 10
    const deck = [...Array(cardsToBeRendered)].map((_, i) => <FaceDownCard quizname={quizname} offset={i * 5} key={i}/>)

    return(
        <Grid container spacing={2} sx={{justifyContent: {md: "space-between", xs: "space-around"}}}>

            <Grid sx={{position:"relative"}} item>{deck}</Grid>
        </Grid>
    )
}

export default Deck;
