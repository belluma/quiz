import React, {useImperativeHandle, useState} from 'react'
import {getApiData} from "../../../Slicer/QuizSlice";
import {useAppDispatch} from "../../../app/hooks";
import {changeQuestionText} from "../../../Slicer/NewCardSlice";

//component imports
import Choices from "../../choices/Choices";
import {createCard} from "../../../services/apiService";
import TextField from '@mui/material/TextField';
import {Button, CardActions, CardContent} from "@mui/material";


//interface imports
import {cardMode, createCardStatus} from "../../../Interfaces/IQuestionCard";

type Props = {
    questionText: string
};

function CardCreationDialog({questionText}:Props) {
    const dispatch = useAppDispatch();
    const [choices, setChoices] = useState<string[]>([]);
    const [choiceText, setChoiceText] = useState<string>("");
    const [answerIndices, setAnswerIndices] = useState<number[]>([]);
    const [dialogStatus, setDialogStatus] = useState<createCardStatus>(createCardStatus.QUESTION);
    const handleChange = ({target}: React.ChangeEvent<HTMLInputElement>) => {
        target.name === "question" && dispatch(changeQuestionText(target.value));
        target.name === "choiceText" && setChoiceText(target.value)
    }


const advanceStatus = () => {
    setDialogStatus(dialogStatus === createCardStatus.QUESTION ? createCardStatus.ANSWER : createCardStatus.SELECT);
}
const revertStatus = () => {
    setDialogStatus(dialogStatus === createCardStatus.SELECT ? createCardStatus.ANSWER : createCardStatus.QUESTION);
}

    const saveChoice = () => {
        setChoices([...choices, choiceText]);
        setChoiceText("");
    }

    const saveCard = () => {
        createCard({question: questionText, choices, answerIndices})
            .then(() => {
                dispatch(getApiData());
                resetStates();
            });
    }
    return (<>
            <CardContent sx={{position: "absolute", bottom: "25px", width:0.99}}>
                {dialogStatus === createCardStatus.QUESTION &&
                <div>
                    <TextField value={questionText} name="question" label="write your question here" onChange={handleChange}/>
                    <Button onClick={advanceStatus}>Ok</Button>
                </div>}
                {dialogStatus === createCardStatus.ANSWER && choices.length < 4 &&
                <div>
                    <TextField value={choiceText} name="choiceText" label="write possible answer here"
                               onChange={handleChange}/>
                    <Button disabled={!choiceText.length} onClick={saveChoice}>add answer</Button>
                </div>}

                <Choices choices={choices} mode={cardMode.QUIZ} selectAnswer={(e) => setAnswerIndices([+e.target.value])} selected={answerIndices} />

            </CardContent>
    <CardActions>
        <Button disabled={choices.length < 2 || !answerIndices.length || !questionText.length} onClick={saveCard}  sx={{position: "absolute", bottom: 10}}>save
            card</Button>
    </CardActions>
        </>
    )
    function resetStates(){
        dispatch(changeQuestionText(""))
        setChoices([]);
        setChoiceText("");
        setAnswerIndices([]);
        setDialogStatus(createCardStatus.QUESTION);
    }
}

export default CardCreationDialog;
