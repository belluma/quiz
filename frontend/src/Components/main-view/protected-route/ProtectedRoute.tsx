import React from 'react'
import Login from "../../login/Login";
import {useAppSelector} from "../../../app/hooks";
import {selectLoggedIn} from "../../../Slicer/AuthSlice";
import {Route} from "react-router";

//component imports

//interface imports

type Props = {
    route:string,
    component:any
};

function ProtectedRoute({route, component}: Props){
    const loggedIn = useAppSelector(selectLoggedIn);

    return(
       <Route path={route} component={loggedIn ? component : Login} />
    )
}

export default ProtectedRoute;
