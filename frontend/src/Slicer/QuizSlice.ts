import {createAsyncThunk, createSlice, PayloadAction} from "@reduxjs/toolkit";
import {getAllCards, validateAnswer} from "../services/apiService";
import {IQuestionCard, IQuizState} from "../Interfaces/IQuestionCard";
import {RootState} from "../app/store";
import {getErrorMessage} from "./ErrorSlice";
import {validateToken} from "../services/authService";
import {logout} from "./AuthSlice";
import history from '../services/history'

const initialState: IQuizState = {
    allCards: [],
    answeredCards: [],
    status: 200,
    message: "",
    error: false,
    pointsCounter: 0,
}

interface IResponseData {
    data: IQuestionCard[],
    status: number,
    statusText: string
}

export const parseError = (err: any) => {
    return {...err, data: []}
}
export const getApiData = createAsyncThunk(
    'quiz/fetchQuizcards'
    , async (_, thunkAPI) => {
        // @ts-ignore
        const token = thunkAPI.getState().login.token;
        if (tokenInvalid(token, thunkAPI)) return;
        const {data, status, statusText} = await getAllCards(token);
        if (errorMessage(status, statusText, thunkAPI)){
            return parseError({data, status, statusText});
        }
        return {data, status, statusText};
    }
)

export const validateQuizcard = createAsyncThunk(
    'quiz/vaildateAnswer',
    async (answer: IQuestionCard, thunkAPI) => {
        // @ts-ignore
        const token = thunkAPI.getState().login.token;
        if (tokenInvalid(token, thunkAPI)) return;
        const {data, status, statusText} = await validateAnswer(answer, token);
        if (errorMessage(status, statusText, thunkAPI)){
            return parseError({data, status, statusText});
        }
        return {data, status, statusText};
    }
)

function tokenInvalid(token: string, thunkApi: any) {
    if (!validateToken(token)) {
        thunkApi.dispatch(logout());
        errorMessage(401, "Session expired!", thunkApi)
        history.push('/login)');
        return true
    }
}

function errorMessage(status: number, statusText: string, thunkApi: any) {
    if (status !== 200) {
        thunkApi.dispatch(getErrorMessage({status, statusText}))
        return true
    }

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
                if(!action.payload) return;
                state.allCards = action.payload.data
            })
            .addCase(validateQuizcard.fulfilled, (state, action: PayloadAction<any>) => {
                if(!action.payload) return;
                if (action.payload.data) state.pointsCounter += 1;
            })
    })
})

export const selectGetAllCards = (state: RootState) => state.quiz.allCards;
export const selectGetAnsweredCards = (state: RootState) => state.quiz.answeredCards;
export const selectErrorStatus = (state: RootState) => state.quiz.status;
export const selectPoints = (state: RootState) => state.quiz.pointsCounter;

export const {closeError, moveCardToAnseweredCardsStack} = QuizSlice.actions;
export default QuizSlice.reducer;
