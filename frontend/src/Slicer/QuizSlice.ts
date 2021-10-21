import {createAsyncThunk, createSlice, PayloadAction} from "@reduxjs/toolkit";
import {getAllCards, validateAnswer} from "../services/apiService";
import {IQuestionCard, IQuizState} from "../Interfaces/IQuestionCard";
import {RootState} from "../app/store";

const initialState: IQuizState = {
    allCards: [],
    answeredCards: [],
    status: 200,
    message: "",
    error: false,
    pointsCounter:0,
}

interface IResponseData {
    data: IQuestionCard[],
    status: number,
    statusText: string
}

export const getApiData = createAsyncThunk(

    'quiz/fetchQuizcards'
    , async () => {
        const {data, status, statusText} = await (getAllCards());
        return {data, status, statusText};
    }
)
export const validateQuizcard = createAsyncThunk(
    'quiz/vaildateAnswer',
    async (answer: IQuestionCard) => {
        const {data, status, statusText} = await (validateAnswer(answer));
        return {data, status, statusText};

    }
)

export const handleErrors = (state: IQuizState, action: PayloadAction<IResponseData>): boolean => {
    if (action.payload.status !== 200) {
        state.status = action.payload.status;
        state.message = action.payload.statusText;
        state.error = true;
        if (action.payload.status === 204){
            state.allCards = state.answeredCards = [];
        }
        return true
    }
    return false
}
const resetErrorState = (state:IQuizState) => {
    state.status = 200;
    state.message = "";
    state.error = false;
}
export const QuizSlice = createSlice({
    name: 'todoList',
    initialState,
    reducers: {
        closeError: (state) => {
            state.error = false;
            state.status = 200;
            state.message = "";
        },
        moveCardToAnseweredCardsStack: (state, action: PayloadAction<IQuestionCard>) => {
            state.answeredCards = [...state.answeredCards, action.payload];
        },
    },
    extraReducers: (builder => {
        builder
            .addCase(getApiData.pending, state => {
            })
            .addCase(getApiData.fulfilled, (state, action: PayloadAction<IResponseData>) => {
                if (handleErrors(state, action)) return
                state.allCards = action.payload.data
                resetErrorState(state);
            })
            .addCase(validateQuizcard.fulfilled, (state, action:PayloadAction<IResponseData>) => {
                if (handleErrors(state, action)) return
                if (action.payload.data)state.pointsCounter += 1;
                resetErrorState(state);
            })
    })
})

export const selectGetAllCards = (state: RootState) => state.quiz.allCards;
export const selectGetAnsweredCards = (state: RootState) => state.quiz.answeredCards;
export const selectErrorStatus = (state: RootState) => state.quiz.status;
export const selectErrorMessage = (state: RootState) => state.quiz.message;
export const selectError = (state: RootState) => state.quiz.error;
export const selectPoints = (state: RootState) => state.quiz.pointsCounter;

export const {closeError, moveCardToAnseweredCardsStack} = QuizSlice.actions;
export default QuizSlice.reducer;
