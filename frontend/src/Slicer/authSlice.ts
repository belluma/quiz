import {createAsyncThunk, createSlice, PayloadAction} from "@reduxjs/toolkit";
import axios from "axios";
import {IUser} from "../Interfaces/IUser";
import {RootState} from "../app/store";


const initialState = {
    loggedIn: false
}

const sendLoginData = (credentials: IUser) => {
    return axios({
        method: 'post',
        url: `/auth/login`,
        data: credentials,
        headers: {"Content-Type": "application/json"}
    }).then(response => response)
        .catch(err => err)
}


export const login = createAsyncThunk(
    'quiz/login',
    async (credentials:IUser) => {
        const {data, status, statusText} = await (sendLoginData(credentials));
        return {data,status, statusText}
    }
)
interface IResponseData {
    data: string;
    status: number,
    statusText:string
}

export const LoginSlice = createSlice({
    name:'login',
    initialState,
    reducers:{},
    extraReducers:builder => {
        builder
            .addCase(login.pending, state => {
            })
            .addCase(login.fulfilled, (state, action: PayloadAction<IResponseData>) => {
                state.loggedIn = true;
                localStorage.setItem('currentUser', action.payload.data);
            })
    }})

export const selectLoggedIn = (state: RootState) => state.login.loggedIn = true;
export default LoginSlice.reducer;
