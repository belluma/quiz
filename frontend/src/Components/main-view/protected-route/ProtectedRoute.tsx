import React from 'react'
import {useAppSelector} from "../../../app/hooks";
import {selectLoggedIn} from "../../../Slicer/AuthSlice";

//component imports
import {Redirect, Route} from "react-router";
import Login from "../../login/Login";

//interface imports

type Props = {
    route:string,
    component?:any
};
function ProtectedRoute({route, component}: Props){
    const loggedIn = useAppSelector(selectLoggedIn);

    return(
        // loggedIn ? <Route exact={exact} path={route} component={component} /> : <Redirect  to="/login" />
    <Route  path={route} component={loggedIn ? component : Login} />
)
}

export default ProtectedRoute;
