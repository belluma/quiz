import {createAsyncThunk, createSlice, PayloadAction} from "@reduxjs/toolkit";
import {IUser} from "../Interfaces/IUser";
import {RootState} from "../app/store";
import {receiveError} from "./ErrorSlice";
import {sendLoginData} from "../services/authService";

const initialState = {
    loggedIn: false,
    token:""
}


export const login = createAsyncThunk(
    'login',
    async (credentials:IUser, thunkAPI) =>  {
        const {data, status, statusText} = await sendLoginData(credentials)
       if(status !== 200) {
           thunkAPI.dispatch(receiveError({status,statusText}))
       }
        return {data, status, statusText}
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
    reducers:{
        logout:(state) => {
            localStorage.removeItem("codificantesToken");
            state.loggedIn = false;
        }
    },
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

export const {logout} = LoginSlice.actions;

export const selectLoggedIn = (state: RootState) => state.login.loggedIn;
export const selectToken = (state: RootState) => state.login.token;
export default LoginSlice.reducer;
