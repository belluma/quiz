import axios, {AxiosResponse} from "axios";


const BASE_URL = "/api/quiz"

export const getAllCards = (token: string)=> {
    return axios({
        method: 'get',
        url: `${BASE_URL}`,
        headers: {"Authorization":getHeader(token)}
    })
        .then(response => response)
        .catch(err =>{
            return {data: "",status: err.response.status, statusText:err.response.data.message}
        } )
}

export const createCard = (card: any, token:string ) => {
    return axios({
        method: 'post',
        url: `${BASE_URL}/new`,
        data: card,
        headers: {"Content-Type": "application/json", "Authorization": getHeader(token)}
    }).then(response => response)
        .catch(err =>{
        return {data: "",status: err.response.status, statusText:err.response.data.message}})
}
export const validateAnswer = (card: any, token:string) => {
    return axios({
        method: 'post',
        url: `${BASE_URL}`,
        data: card,
        headers: {"Content-Type": "application/json", "Authorization":getHeader(token)}
    }).then(response => {
        return response
    })
        .catch(err => {
            return {data: "",status: err.response.status, statusText:err.response.data.message}
        })

}

const getHeader = (token:string) =>`Bearer ${token}`
