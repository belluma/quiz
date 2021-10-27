import React from 'react'
import {login} from "../../../../Slicer/AuthSlice";

//component imports
import {Button, CardActions, IconButton, Typography} from "@mui/material";
import GitHub from '@mui/icons-material/GitHub';
import GithubLoginButton from "../../../security/github-login-button/GithubLoginButton";
//interface imports

type Props = {
    disableButton:boolean,
    onButtonClick:()=>void,
    buttonText:string,
    footerText?: string,
    login?:boolean
};

function CardFooter({disableButton, onButtonClick, buttonText, footerText}: Props){
    return(
        <CardActions sx={{position: "absolute", bottom: 1, right:15}}>
            {login && <GithubLoginButton />}
            <Typography variant="button">{footerText}</Typography>
            <Button disabled={disableButton} variant="text"
                    onClick={onButtonClick} >{buttonText} </Button>
        </CardActions>
    )
}

export default CardFooter;
