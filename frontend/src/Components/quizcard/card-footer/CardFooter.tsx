import React from 'react'
import {Button, CardActions, Typography} from "@mui/material";

//component imports

//interface imports

type Props = {
    disableButton:boolean,
    onButtonClick:()=>void,
    buttonText:string,
    footerText?: string,
};

function CardFooter({disableButton, onButtonClick, buttonText, footerText}: Props){
    return(
        <CardActions sx={{position: "absolute", bottom: 1, right:15}}>
            <Typography variant="button">{footerText}</Typography>
            <Button disabled={disableButton} variant="text"
                    onClick={onButtonClick} name="button">{buttonText} </Button>
        </CardActions>
    )
}

export default CardFooter;
