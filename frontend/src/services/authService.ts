import {IUser} from "../Interfaces/IUser";
import axios from "axios";


const parseJwt = (token: string) => {
    try {
        return JSON.parse(atob(token.split(".")[1]));
    } catch (e) {
        return null;
    }
}

export const validateToken = (token:string):boolean => {
        const decodedJwt = parseJwt(token);
        return decodedJwt.exp * 1000  > Date.now()
}

export const sendLoginData = (credentials: IUser) => {
    return axios({
        method: 'post',
        url: `/auth/login`,
        data: credentials,
        headers: {}
    }).then(response => {
        return response
    })
        .catch(err => {
            return {data: "",status: err.response.status, statusText:err.response.data.message}
        })
}

