import React, {useState} from 'react'
import {Button, Card, CardContent, CardHeader, IconButton, Typography} from "@mui/material";
import TextField from '@mui/material/TextField';
import {createCard} from "../../services/apiService";
import {getApiData} from "../../Slicer/QuizSlice";
import {useAppDispatch} from "../../app/hooks";
import HelpIcon from "@mui/icons-material/Help";
import Choices from "../choices/Choices";
import {cardMode} from "../../Interfaces/IQuestionCard";

//component imports

//interface imports

type Props = {};

function CardCreationDialog(props: Props) {
    const dispatch = useAppDispatch();
    const [question, setQuestion] = useState<string>("");
    const [choices, setChoices] = useState<string[]>([]);
    const [choiceText, setChoiceText] = useState<string>("");
    const [answerIndices, setAnswerIndices] = useState<number[]>([]);
    const handleChange = ({target}: React.ChangeEvent<HTMLInputElement>) => {
        target.name === "question" && setQuestion(target.value)
        target.name === "choiceText" && setChoiceText(target.value)
    }

    const saveCoice = () => {
        setChoices([...choices, choiceText]);
        setChoiceText("");
    }
    const saveIndex = (e: React.ChangeEvent<HTMLInputElement>) => {
        setAnswerIndices([+e.target.value]);
    }
    const saveCard = () => {
        createCard({question, choices, answerIndices})
            .then(() => {
                dispatch(getApiData());
                resetStates();
            });
    }
    return (
            <CardContent>
                <div>
                    <TextField value={question} name="question" label="write your question here" onChange={handleChange}/>
                </div>
                <div>
                    <TextField value={choiceText} name="choiceText" label="write possible answer here"
                               onChange={handleChange}/>
                    <Button disabled={!choiceText.length} onClick={saveCoice}>add answer</Button>
                </div>
                <Choices choices={choices} mode={cardMode.QUIZ} selectAnswer={saveIndex}/>
                <Button disabled={choices.length < 2 || !answerIndices.length || !question.length} onClick={saveCard}>save
                    card</Button>
            </CardContent>
    )
    function resetStates(){
        setQuestion("");
        setChoices([]);
        setChoiceText("");
        setAnswerIndices([]);
    }
}

export default CardCreationDialog;
