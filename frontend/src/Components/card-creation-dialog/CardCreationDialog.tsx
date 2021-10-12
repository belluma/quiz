import React, {useState} from 'react'
import {Button, Card, CardContent, Typography} from "@mui/material";
import TextField from '@mui/material/TextField';
import {createCard} from "../../services/apiService";
import {getApiData} from "../../Slicer/QuizSlice";
import {useAppDispatch} from "../../app/hooks";

//component imports

//interface imports

type Props = {};

function CardCreationDialog(props: Props) {
    const dispatch = useAppDispatch();
    const [question, setQuestion] = useState<string>("");
    const [choices, setChoices] = useState<string[]>([]);
    const [choiceText, setChoiceText] = useState<string>("");
    const [answerIndex, setAnswerIndex] = useState<number>(0);
    const [answerIndices, setAnswerIndices] = useState<number[]>([]);
    const handleChange = ({target}: React.ChangeEvent<HTMLInputElement>) => {
        target.name === "question" ? setQuestion(target.value) :
            target.name === "choiceText" ? setChoiceText(target.value) :
                setAnswerIndex(+target.value);
    }

    const saveCoice = () => {
        setChoices([...choices, choiceText]);
        setChoiceText("");
    }
    const saveIndex = () => {
        if(answerIndices.indexOf(answerIndex) >= 0) return;
        setAnswerIndices([...answerIndices, answerIndex]);
        setAnswerIndex(0);
    }
    const saveCard = () => {
        createCard({question, choices, answerIndices})
            .then(() => dispatch(getApiData()));
    }
    return (
        <Card>
            <CardContent>
                <Typography><TextField value={question} name="question" label="question" onChange={handleChange}></TextField></Typography>
                <Typography><TextField value={choiceText} name="choiceText" label="choiceText" onChange={handleChange}></TextField></Typography>
                <Button onClick={saveCoice}>add answer</Button>
                {/*<TextField*/}
                {/*    value={answerIndex}*/}
                {/*    label="answerIndex"*/}
                {/*    type="number"*/}
                {/*    InputLabelProps={{*/}
                {/*        shrink: true,*/}
                {/*    }}*/}
                {/*    inputProps={{min:0, max:choices.length -1}}*/}
                {/*    onChange={handleChange}*/}
                {/*/>*/}
                <Button onClick={saveIndex}>save index of correct answer</Button>
                <Button onClick={saveCard}>save card</Button>

            </CardContent>
        </Card>
    )
}

export default CardCreationDialog;
