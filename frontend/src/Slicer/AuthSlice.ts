import {createAsyncThunk, createSlice, PayloadAction} from "@reduxjs/toolkit";
import axios from "axios";
import {IUser} from "../Interfaces/IUser";
import {RootState} from "../app/store";
import {receiveError} from "./ErrorSlice";
import {getApiData} from "./QuizSlice";
const initialState = {
    loggedIn: false,
    token:""
}

const sendLoginData = (credentials: IUser) => {
    return axios({
        method: 'post',
        url: `/auth/login`,
        data: credentials,
        headers: {"Content-Type": "application/json"}
    }).then(response => {
        return response
    })
        .catch(err => {
            return {data: "",status: err.response.status, statusText:err.response.data.message}
        })
}

export const login = createAsyncThunk(
    'login',
    async (credentials:IUser, thunkAPI) =>  {
        const data = await sendLoginData(credentials)
       if(data.status !== 200) {
           thunkAPI.dispatch(receiveError(data))
       }
       thunkAPI.dispatch(getApiData())
        return data
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
                if (action.payload.status !== 200){
                    return;
                }
                state.loggedIn = true;
                state.token = action.payload.data
                localStorage.setItem('codificantesToken', action.payload.data);
            })
    }})

export const selectLoggedIn = (state: RootState) => state.login.loggedIn;
export const selectToken = (state: RootState) => state.login.token;
export default LoginSlice.reducer;
