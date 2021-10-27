import {IUser} from "../Interfaces/IUser";
import axios, {AxiosError, AxiosResponse} from "axios";


const parseJwt = (token: string) => {
    try {
        return JSON.parse(atob(token.split(".")[1]));
    } catch (e) {
        return null;
    }
}

export const validateToken = (token: string): boolean => {
    const decodedJwt = parseJwt(token);
    if(!decodedJwt) return false;
    return decodedJwt.exp * 1000 > Date.now()
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
            return {data: "", status: err.response.status, statusText: err.response.data.message}
        })
}

export const sendLoginDataToGithub = (code: string) => {
    console.error(code)
    return axios({
        method: 'get',
        url: `/auth/github/${code}`,

    })
        .then(response => response)
        .catch(err => parseError(err))
}

function parseError(err:any) {
    return {data: "", status: err.response.status, statusText: err.response.data.message}
}
