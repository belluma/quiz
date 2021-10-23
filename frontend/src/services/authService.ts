import {IUser} from "../Interfaces/IUser";
import axios from "axios";


// const parseJwt = (token: string) => {
//     try {
//         return JSON.parse(atob(token.split(".")[1]));
//     } catch (e) {
//         return null;
//     }
// }
//
// const handleLogout = () => {
//     const dispatch = useAppDispatch();
//     dispatch(logout);
//     history.push('/logout');
// }
//
// export const validateToken = () => {
//     const token:string | null = localStorage.getItem("codificantesToken");
//     if(token) {
//         const decodedJwt = parseJwt(token);
//         if(decodedJwt.exp * 1000 < Date.now()) handleLogout()
//     }
// }
//
//
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

