import axios, {AxiosResponse} from "axios";

const BASE_URL = "/api/quiz"

export const getAllCards = ():Promise<AxiosResponse> => {
    return axios.get(BASE_URL)
        .then(result => result)
        .catch(err => err)
}

export const createCard = (card:any):Promise<AxiosResponse> =>{
    return axios({
        method:'post',
        url:`${BASE_URL}/new`,
        data: card,
        headers:{"Content-Type": "application/json"}
    }).then(response => response)
        .catch(err => err)
}
export const validateCard = (card:any):Promise<AxiosResponse> =>{
    return axios({
        method:'post',
        url:`${BASE_URL}`,
        data: card,
        headers:{"Content-Type": "application/json"}
    }).then(response => response)
        .catch(err => err)
}
