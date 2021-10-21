import axios, {AxiosResponse} from "axios";
import { IQuestionCard } from "../Interfaces/IQuestionCard";



const BASE_URL = "/api/quiz"

export const getAllCards = ():Promise<IQuestionCard[]> => {
    return axios.get(BASE_URL)
        .then(response => response.data)
}

export const createCard = (card:any):Promise<AxiosResponse> =>{
    return axios({
        method:'post',
        url:`${BASE_URL}/new`,
        data: card,
        headers:{"Content-Type": "application/json"}
    }).then(response => response.data)
}
export const validateAnswer = (card:any):Promise<AxiosResponse> =>{
    return axios({
        method:'post',
        url:`${BASE_URL}`,
        data: card,
        headers:{"Content-Type": "application/json"}
    }).then(response => response.data)
}
