import {createSlice, PayloadAction} from "@reduxjs/toolkit";

interface IError  {
    error: boolean,
    status:number,
    statusText:string,
}

const initialState = {
    error: false,
    status: 200,
    statusText: "",
}

export const ErrorSlice = createSlice({
    name:'error',
    initialState,
    reducers:{
        closeError: (state) => {
            state.error = false;
            state.status = 200;
            state.statusText = "";
        },
        receiveError:(state, action:PayloadAction<IError>) => {
            state.error = true;
            state.status = action.payload.status;
            state.statusText = action.payload.statusText;
        }
    }
})

export const {closeError, receiveError} = ErrorSlice.actions;
export default ErrorSlice.reducer;
