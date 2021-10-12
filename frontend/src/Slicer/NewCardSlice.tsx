import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../app/store";

const initialState = {
    question: ""
}

export const newCardSlice = createSlice({
    name: "newCard",
    initialState,
    reducers:{
        changeText:(state, action:PayloadAction<React.ChangeEvent<HTMLInputElement>>) => {
            state.question = action.payload.target.value;
        }
    }
})

export const selectQeustionText = (state: RootState) => state.newCard.question;
export const {changeText} = newCardSlice.actions;
export default newCardSlice.reducer;
