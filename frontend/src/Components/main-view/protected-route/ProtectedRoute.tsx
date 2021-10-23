import React from 'react'
import {useAppSelector} from "../../../app/hooks";
import {selectLoggedIn} from "../../../Slicer/AuthSlice";

//component imports
import {Redirect, Route} from "react-router";
import Login from "../../security/login/Login";

//interface imports

type Props = {
    path:string,
    component?:any
};
function ProtectedRoute(props: Props){
    const loggedIn = useAppSelector(selectLoggedIn);

    return(
        loggedIn ? <Route {...props} /> : <Redirect to="/login"/>
)
}

export default ProtectedRoute;
