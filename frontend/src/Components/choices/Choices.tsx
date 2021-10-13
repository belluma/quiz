import React, {useMemo, useState} from 'react'

//component imports
import {FormControl, FormControlLabel, Grid, Radio, RadioGroup, Typography, useFormControl} from "@mui/material";

//interface imports
import {cardMode} from "../../Interfaces/IQuestionCard";

type Props = {
    choices: string[],
    mode: cardMode,
    selectAnswer: (e: React.ChangeEvent<HTMLInputElement>) => void,
};

function Choices({choices, mode, selectAnswer}: Props) {

    const choicesWithoutInput = choices.map((choice, i) => <Grid item xs={1} key={i}><Typography>{choice}</Typography>
    </Grid>)

    const radios = choices.map((choice, i) => <Grid item xs={1} key={i}><FormControlLabel control={<Radio/>}
                                                                                          label={choice} value={i}/>
    </Grid>)

    const multipleChoiceInGrid = <RadioGroup aria-label="Multiple Choice" name="multiple-choice-answers"
                                             onChange={selectAnswer}>
        {createGrid(radios)}</RadioGroup>
    return (

        <FormControl style={{width: "100%"}} component="fieldset">
            {mode === cardMode.QUIZ ? multipleChoiceInGrid : createGrid(choicesWithoutInput)}
        </FormControl>

    )

    function createGrid(childComponent: JSX.Element | JSX.Element[]) {
        return <Grid item container spacing={2} columns={2} justifyContent="space-around">{childComponent}</Grid>
    }
}

export default Choices;
