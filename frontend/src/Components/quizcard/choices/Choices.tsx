import React from 'react'

//component imports
import {
    createTheme,
    FormControl,
    FormControlLabel,
    Grid,
    Radio,
    RadioGroup,
    ThemeOptions, ThemeProvider,
    Typography
} from "@mui/material";

//interface imports
import {cardMode} from "../../../Interfaces/IQuestionCard";

type Props = {
    choices: string[],
    mode: cardMode,
    selectAnswer: (e: React.ChangeEvent<HTMLInputElement>) => void,
    selected:number[]
};

function Choices({choices, mode, selectAnswer, selected}: Props) {

    const choicesWithoutInput = choices.map((choice, i) => <Grid item xs={2} sm={1}
                                                                 key={i}><Typography>{choice}</Typography>
    </Grid>)

    const radios = choices.map((choice, i) => (
        <Grid item xs={2} sm={1} key={i}>
            <ThemeProvider theme={overrideColorOnChecked()}>
            <FormControlLabel control={<Radio/>} label={choice} value={i} checked={selected[0] === i}/>
            </ThemeProvider></Grid>));

    const multipleChoiceInGrid =
        <RadioGroup aria-label="Multiple Choice" name="multiple-choice-answers" onChange={selectAnswer}  >
        {createGrid(radios)}</RadioGroup>

    return (
        <FormControl sx={{width: "100%"}} component="fieldset">
            {mode === cardMode.QUIZ ? multipleChoiceInGrid : createGrid(choicesWithoutInput)}
        </FormControl>
    )

    function createGrid(childComponent: JSX.Element | JSX.Element[]) {
        return <Grid item container spacing={2} columns={{xs: 2}} justifyContent="space-between">{childComponent}</Grid>
    }
    function overrideColorOnChecked():ThemeOptions{
        return createTheme({
            palette:{
                primary:{
                    main:"rgb(0,0,0,0.87)"
                }
            }
        })
    }

}

export default Choices;
