import React, {useState} from 'react'
import {useLocation} from "react-router";
import { useAppDispatch } from '../../../app/hooks';
import {sendLoginDataToGithub} from "../../../services/authService";
import {loginWithGithub} from "../../../Slicer/AuthSlice";

//component imports

//interface imports

type Props = {};

function GithubLogin(props: Props){
const dispatch = useAppDispatch();
    const query = useQuery();
    const code = query.get("code");
    console.log(code)
    if(code) {
        sendLoginDataToGithub(code);
        // dispatch(loginWithGithub(code));
    }
    return <></>

}

function useQuery() {
    return new URLSearchParams(useLocation().search);
}

export default GithubLogin;
