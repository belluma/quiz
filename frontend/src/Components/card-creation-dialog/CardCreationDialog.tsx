import React, {useState} from 'react'
import TextField from '@mui/material/TextField';
import {Button,  CardContent } from "@mui/material";
import {createCard} from "../../services/apiService";
import {getApiData} from "../../Slicer/QuizSlice";
import {useAppDispatch} from "../../app/hooks";
import Choices from "../choices/Choices";
import {cardMode} from "../../Interfaces/IQuestionCard";
import {changeQuestionText} from "../../Slicer/NewCardSlice";

//component imports

//interface imports

type Props = {
    questionText: string
};

function CardCreationDialog({questionText}:Props) {
    const dispatch = useAppDispatch();
    const [choices, setChoices] = useState<string[]>([]);
    const [choiceText, setChoiceText] = useState<string>("");
    const [answerIndices, setAnswerIndices] = useState<number[]>([]);
    const handleChange = ({target}: React.ChangeEvent<HTMLInputElement>) => {
        target.name === "question" && dispatch(changeQuestionText(target.value));
        target.name === "choiceText" && setChoiceText(target.value)
    }

    const saveChoice = () => {
        setChoices([...choices, choiceText]);
        setChoiceText("");
    }
    const saveIndex = (e: React.ChangeEvent<HTMLInputElement>) => {
        setAnswerIndices([+e.target.value]);
    }
    const saveCard = () => {
        createCard({questionText, choices, answerIndices})
            .then(() => {
                dispatch(getApiData());
                resetStates();
            });
    }
    return (
            <CardContent>
                <div>
                    <TextField value={questionText} name="question" label="write your question here" onChange={handleChange}/>
                </div>
                <div>
                    <TextField value={choiceText} name="choiceText" label="write possible answer here"
                               onChange={handleChange}/>
                    <Button disabled={!choiceText.length} onClick={saveChoice}>add answer</Button>
                </div>
                <Choices choices={choices} mode={cardMode.QUIZ} selectAnswer={saveIndex}/>
                <Button disabled={choices.length < 2 || !answerIndices.length || !questionText.length} onClick={saveCard}>save
                    card</Button>
            </CardContent>
    )
    function resetStates(){
        dispatch(changeQuestionText(""))
        setChoices([]);
        setChoiceText("");
        setAnswerIndices([]);
    }
}

export default CardCreationDialog;
