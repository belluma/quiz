import axios, {AxiosResponse} from "axios";


const BASE_URL = "/api/quiz"

export const getAllCards = (token: string): Promise<AxiosResponse> => {
console.log(token)
    return axios({
        method: 'get',
        url: `${BASE_URL}`,
        headers: {"Authorization":getHeader(token)}
    })
        .then(response => response)
        .catch(err => err)
}

export const createCard = (card: any, ): Promise<AxiosResponse> => {
    return axios({
        method: 'post',
        url: `${BASE_URL}/new`,
        data: card,
        headers: {"Content-Type": "application/json"}
    }).then(response => response)
        .catch(err => err)

}
export const validateAnswer = (card: any, token:string): Promise<AxiosResponse> => {
    return axios({
        method: 'post',
        url: `${BASE_URL}`,
        data: card,
        headers: {"Content-Type": "application/json", "Authorization":getHeader(token)}
    }).then(response => response)
        .catch(err => err)

}

const getHeader = (token:string) =>`Bearer ${token}`
