import React from 'react'
import FaceDownCard from "./face-down-card/FaceDownCard";

//component imports

//interface imports

type Props = {
    cards?:number
    quizname?:string
};

function Stack({cards, quizname}: Props){


    return(
       <FaceDownCard quizname={quizname}/>
    )
}

export default Stack;
