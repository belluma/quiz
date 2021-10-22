import axios, {AxiosResponse} from "axios";
import { IQuestionCard} from "../Interfaces/IQuestionCard";
import {IError} from "../Slicer/ErrorSlice";
import {useAppSelector} from "../app/hooks";



const BASE_URL = "/api/quiz"
const AUTH_HEADER = `Bearer: ${localStorage.getItem('codificantesToken')}`

export const getAllCards = ():Promise<AxiosResponse> => {
    return axios.get(BASE_URL)
        .then(response => response)
        .catch(err => err)
}

export const createCard = (card:any):Promise<IQuestionCard[] | IError> =>{
    return axios({
        method:'post',
        url:`${BASE_URL}/new`,
        data: card,
        headers:{"Content-Type": "application/json"}
    }).then(response => response)
            .catch(handleError)

}
export const validateAnswer = (card:any):Promise<IQuestionCard[] | IError> =>{
    return axios({
        method:'post',
        url:`${BASE_URL}`,
        data: card,
        headers:{"Content-Type": "application/json"}
    }).then(response => response)
            .catch(handleError)

}

export const handleError = (err: any) =>  {
    return {data: [], status: err.response.status, statusText:err.response.data.message}
}
