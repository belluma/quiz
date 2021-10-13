import React, {useMemo, useState} from 'react'
import {FormControl, FormControlLabel, Radio, RadioGroup, Typography, useFormControl} from "@mui/material";

//component imports

//interface imports
import {cardMode} from "../../Interfaces/IQuestionCard";

type Props = {
    choices: string[],
    mode: cardMode,
    selectAnswer: (e: React.ChangeEvent<HTMLInputElement>) => void,
};

function Choices({choices, mode, selectAnswer}: Props){

    const choicesNoInteraction = choices.map((choice, i) => <Typography key={i}>{choice}</Typography>)
    const radios = choices.map((choice, i) => <FormControlLabel control={<Radio />} label={choice} value={i} key={i}/>)
    const multipleChoice = <RadioGroup aria-label="Multiple Choice" name="multiple-choice-answers"   onChange={selectAnswer}>{radios}</RadioGroup>
    return(
        <div>
            <FormControl component="fieldset">
        {mode === cardMode.ALL ? choicesNoInteraction : multipleChoice}
            </FormControl>
        </div>
    )
}

export default Choices;
