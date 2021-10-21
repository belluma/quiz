import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../app/store";

const initialState = {
    question: ""
}

export const newCardSlice = createSlice({
    name: "newCard",
    initialState,
    reducers:{
        changeQuestionText:(state, action:PayloadAction<string>) => {
            state.question = action.payload;
        }
    }
})

export const selectQuestionText = (state: RootState) => state.newCard.question;
export const {changeQuestionText} = newCardSlice.actions;
export default newCardSlice.reducer;
