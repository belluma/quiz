import {createAsyncThunk, createSlice, PayloadAction} from "@reduxjs/toolkit";
import {getAllCards} from "../services/apiService";
import {IError, IQuestionCard, IQuizState} from "../Interfaces/IQuestionCard";
import {RootState} from "../app/store";


const initialState: IQuizState = {
    allCards: [],
    answeredCards:[],
    status: 200,
    message: "",
    error: false,
    }

export const getApiData = createAsyncThunk(
    'todoList/fetchTodos'
    , async () => {
        const {data, status, statusText} = await (getAllCards());
        return {data, status, statusText}
    }
)

interface IResponseData {
    data:IQuestionCard[],
    status:number,
    statusText:string
}

const handleErrors = (state:IQuizState, action: PayloadAction<IResponseData>): boolean => {
    if(action.payload.status !== 200){
        state.status = action.payload.status;
        state.message = action.payload.statusText;
        state.error = true;
        if(action.payload.status === 204) state.allCards = state.answeredCards = [];
        return true
    }
    return false
}
export const QuizSlice = createSlice({
    name: 'todoList',
    initialState,
    reducers: {
        closeError: (state) =>{
            state.error = false;
            state.status = 200;
            state.message = "";
        },
        answerCard: (state, action:PayloadAction<IQuestionCard>) => {
            state.answeredCards = [...state.answeredCards, action.payload];
        }
    },
    extraReducers: (builder => {
        builder
            .addCase(getApiData.pending, state => {
            })
            .addCase(getApiData.fulfilled, (state, action: PayloadAction<IResponseData>) => {
                if (handleErrors(state, action)) return
                const allCards: IQuestionCard[] = action.payload.data;
                state.allCards = allCards;
                state.status = 200;
                state.message = "";
                state.error = false;
            })
    })
})

export const selectGetAllCards = (state: RootState) => state.quiz.allCards;
export const selectGetAnsweredCards = (state: RootState) => state.quiz.answeredCards;

export const selectErrorStatus = (state: RootState) => state.quiz.status;
export const selectErrorMessage = (state: RootState) => state.quiz.message;
export const selectError = (state: RootState) => state.quiz.error;
export const {closeError}= QuizSlice.actions;
export default QuizSlice.reducer;
