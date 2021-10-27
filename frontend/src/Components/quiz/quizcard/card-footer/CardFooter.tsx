import React from 'react'
import {login} from "../../../../Slicer/AuthSlice";

//component imports
import {Button, CardActions, IconButton, Typography} from "@mui/material";
import GitHub from '@mui/icons-material/GitHub';
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
            {login && githubLogin()}
            <Typography variant="button">{footerText}</Typography>
            <Button disabled={disableButton} variant="text"
                    onClick={onButtonClick} >{buttonText} </Button>
        </CardActions>
    )
    function githubLogin(){
        return <IconButton href={"https://github.com/login/oauth/authorize/?client_id=1c197313fc93a8873939&redirect_uri=http://localhost:3000/githubLogin/"}><GitHub/></IconButton>
    }
}

export default CardFooter;
