import React, {useEffect} from 'react'
import {useLocation} from "react-router";
import { useAppDispatch } from '../../../app/hooks';
import {loginWithGithub} from "../../../Slicer/AuthSlice";

//component imports

//interface imports

type Props = {};

function GithubLogin(props: Props){
const dispatch = useAppDispatch();
    const query = useQuery();
    const code = query.get("code");
    useEffect(() => {
        if(code) {
            dispatch(loginWithGithub(code));
        }
    }    );



    return <></>

}

function useQuery() {
    return new URLSearchParams(useLocation().search);
}

export default GithubLogin;
