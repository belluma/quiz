import React from 'react'
import {FormControlLabel, Radio, RadioGroup} from "@mui/material";

//component imports

//interface imports
import {cardMode} from "../../Interfaces/IQuestionCard";

type Props = {
    choices: string[],
    mode: cardMode,
};

function Choices({choices, mode}: Props){
    const value = 0;
    const onChange = () => {
    };
    const choicesNoInteraction = choices.map((choice, i) => <h1 key={i}>{choice}</h1>)
    const radios = choices.map((choice, i) => <FormControlLabel control={<Radio />} label={choice} value={choice} />)
    const multipleChoice = <RadioGroup aria-label="Multiple Choice" name="multiple-choice-answers" value={value} onChange={onChange}>{radios}</RadioGroup>
    return(
        <div>
        {mode === cardMode.ALL ? choicesNoInteraction : multipleChoice}
        </div>
    )
}

export default Choices;
