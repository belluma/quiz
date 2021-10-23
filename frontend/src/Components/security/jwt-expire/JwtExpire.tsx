import React from 'react'
import {withRouter} from 'react-router-dom'
import {History, LocationState} from "history";
import {useAppDispatch} from '../../../app/hooks';
import {logout} from "../../../Slicer/AuthSlice";

//component imports

//interface imports

type Props = {
    history: History<LocationState>
};

const parseJwt = (token: string) => {
    try {
        return JSON.parse(atob(token.split(".")[1]));
    } catch (e) {
        return null;
    }
}

function JwtExpire({history}: Props) {
    const handleLogout = () => {
        dispatch(logout);
        history.push('/logout')
    }
    const dispatch = useAppDispatch();
    history.listen(( location, action) => {
        console.log(action, location);
        const token = localStorage.getItem("codificantesToken");
        if (token) {
            const decodedJwt = parseJwt(token);
            console.log(decodedJwt.exp * 1000 < Date.now())
            if (!decodedJwt || decodedJwt.exp * 1000 < Date.now()) {
                handleLogout()
            }
        }
    })
    return (
        <div></div>
    )
}

export default withRouter(JwtExpire);
