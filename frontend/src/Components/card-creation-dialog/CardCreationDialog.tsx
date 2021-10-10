import React, {useState} from 'react'
import {Button, Card, CardContent} from "@mui/material";
import TextField from '@mui/material/TextField';

//component imports

//interface imports

type Props = {};

function CardCreationDialog(props: Props) {
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
    return (
        <Card>
            <CardContent>
                <TextField value={question} name="question" label="question" onChange={handleChange}></TextField>
                <TextField value={choiceText} name="choiceText" label="choiceText" onChange={handleChange}></TextField>
                <Button onClick={saveCoice}>add answer</Button>
                <TextField
                    value={answerIndex}
                    label="answerIndex"
                    type="number"
                    InputLabelProps={{
                        shrink: true,
                    }}
                    inputProps={{min:0, max:choices.length -1}}
                    onChange={handleChange}
                    // min={0} max={choices.length - 1}
                /><Button onClick={saveIndex}>add answer</Button>

            </CardContent>
        </Card>
    )
}

export default CardCreationDialog;
