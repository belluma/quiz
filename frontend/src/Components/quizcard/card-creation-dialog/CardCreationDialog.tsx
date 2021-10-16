import React, {useState} from 'react'
import {getApiData} from "../../../Slicer/QuizSlice";
import {useAppDispatch} from "../../../app/hooks";
import {changeQuestionText} from "../../../Slicer/NewCardSlice";

//component imports
import Choices from "../choices/Choices";
import {createCard} from "../../../services/apiService";
import TextField from '@mui/material/TextField';
import {Button, Card, CardActions, CardContent, Divider} from "@mui/material";
import QuizcardHeader from "../quizcard-header/QuizcardHeader";


//interface imports
import {cardMode, createCardStatus} from "../../../Interfaces/IQuestionCard";

//styles
import {styleCard, styleCardContent} from "../Quizcard";

type Props = {
};

function CardCreationDialog(props: Props) {
    const dispatch = useAppDispatch();
    const [question, setQuestion] = useState<string>();
    const [choices, setChoices] = useState<string[]>([]);
    const [choiceText, setChoiceText] = useState<string>("");
    const [answerIndices, setAnswerIndices] = useState<number[]>([]);
    const [dialogStatus, setDialogStatus] = useState<createCardStatus>(createCardStatus.QUESTION);
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
        createCard({question: question, choices, answerIndices})
            .then(() => {
                dispatch(getApiData());
                resetStates();
            });
    }
    return (<Card sx={styleCard()}>
            <QuizcardHeader title={question} clickHandler={()=>setDialogStatus(createCardStatus.QUESTION)}/>
            <Divider />
            <CardContent sx={styleCardContent(dialogStatus)}>
                {dialogStatus === createCardStatus.QUESTION &&
                <div>
                    <TextField value={question} name="question" label="write your question here"
                               onChange={handleChange}/>
                    <Button onClick={advanceStatus}>Ok</Button>
                </div>}
                {dialogStatus === createCardStatus.ANSWER && choices.length < 4 &&
                <div>
                    <TextField value={choiceText} name="choiceText" label="write possible answer here"
                               onChange={handleChange}/>
                    <Button disabled={!choiceText.length} onClick={saveChoice}>add answer</Button>
                </div>}

                <Choices choices={choices} mode={cardMode.QUIZ}
                         selectAnswer={(e) => setAnswerIndices([+e.target.value])} selected={answerIndices}/>

            </CardContent>
            <CardActions>
                <Button disabled={choices.length < 2 || !answerIndices.length || !question}
                        onClick={saveCard} sx={{position: "absolute", bottom: 10}}>save
                    card</Button>
            </CardActions>
        </Card>
    )

    function resetStates() {
        dispatch(changeQuestionText(""))
        setChoices([]);
        setChoiceText("");
        setAnswerIndices([]);
        setDialogStatus(createCardStatus.QUESTION);
    }
}

export default CardCreationDialog;
