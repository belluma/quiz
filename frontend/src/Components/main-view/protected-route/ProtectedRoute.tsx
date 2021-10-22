import React from 'react'
import {useAppSelector} from "../../../app/hooks";
import {selectLoggedIn} from "../../../Slicer/AuthSlice";

//component imports
import {Redirect, Route} from "react-router";

//interface imports

type Props = {
    route:string,
    component?:any
};

function ProtectedRoute({route, component}: Props){
    const loggedIn = useAppSelector(selectLoggedIn);

    return(
        loggedIn ? <Route path={route} component={component} /> : <Redirect  to="/login" />
    )
}

export default ProtectedRoute;
