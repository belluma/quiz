import React, {useState} from 'react'
import {getApiData} from "../../../Slicer/QuizSlice";
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {changeQuestionText} from "../../../Slicer/NewCardSlice";

//component imports
import Choices from "../choices/Choices";
import {createCard} from "../../../services/apiService";
import {
    Card,
    CardContent,
    Divider,
} from "@mui/material";
import QuizcardHeader from "../quizcard-header/QuizcardHeader";
import CardFooter from "../card-footer/CardFooter";
import CustomFormGroup from "./custom-form-group/CustomFormGroup";


//interface imports
import {cardMode, createCardStatus} from "../../../Interfaces/IQuestionCard";

//styles
import {makeCardChangeBetweenPortraitAndLandscape, styleCardContent} from "../../../style-helpers/card";
import { selectToken } from '../../../Slicer/AuthSlice';

type Props = {};

function CardCreationDialog(props: Props) {
    const dispatch = useAppDispatch();
    const [question, setQuestion] = useState<string>("");
    const [choices, setChoices] = useState<string[]>([]);
    const [choiceText, setChoiceText] = useState<string>("");
    const [answerIndices, setAnswerIndices] = useState<number[]>([]);
    const [dialogStatus, setDialogStatus] = useState<createCardStatus>(createCardStatus.QUESTION);
    const token = useAppSelector(selectToken);
    const handleChange = ({target}: React.ChangeEvent<HTMLInputElement>) => {
        target.name === "question" && setQuestion(target.value);
        target.name === "choiceText" && setChoiceText(target.value)
    }
    const advanceStatus = () => {
        setDialogStatus(dialogStatus === createCardStatus.QUESTION ? createCardStatus.ANSWER : createCardStatus.SELECT);
    }
    // const revertStatus = () => {
    //     setDialogStatus(dialogStatus === createCardStatus.SELECT ? createCardStatus.ANSWER : createCardStatus.QUESTION);
    // }
    const saveChoice = () => {
        setChoices([...choices, choiceText]);
        setChoiceText("");
    }
    const saveCard = () => {
        createCard({question: question, choices, answerIndices}, token)
            .then(() => {
                dispatch(getApiData());
                resetStates();
            });
    }
    return (<Card sx={makeCardChangeBetweenPortraitAndLandscape()}>
            <QuizcardHeader title={question} clickHandler={() => setDialogStatus(createCardStatus.QUESTION)}/>
            <Divider/>
            <CardContent sx={styleCardContent(dialogStatus)}>
                {dialogStatus === createCardStatus.QUESTION &&
                <CustomFormGroup text={question} handleTextChange={handleChange} disableButton={!question.length}
                                 handleButtonClick={advanceStatus} textFieldName="question"
                                 textFieldLabel="write your question here"/>}
                {dialogStatus === createCardStatus.ANSWER && choices.length < 4 &&
                <CustomFormGroup text={choiceText} handleTextChange={handleChange} disableButton={!choiceText.length}
                                 handleButtonClick={saveChoice} textFieldName="choiceText"
                                 textFieldLabel="write possible answer here"/>
                }
                <Choices choices={choices} mode={cardMode.QUIZ}
                         selectAnswer={(e) => setAnswerIndices([+e.target.value])} selected={answerIndices}/>

            </CardContent>
            <CardFooter disableButton={choices.length < 2 || !answerIndices.length || !question} onButtonClick={saveCard} buttonText="save card" />

        </Card>
    )

    function resetStates() {
        dispatch(changeQuestionText(""))
        setQuestion("");
        setChoices([]);
        setChoiceText("");
        setAnswerIndices([]);
        setDialogStatus(createCardStatus.QUESTION);
    }
}

export default CardCreationDialog;
