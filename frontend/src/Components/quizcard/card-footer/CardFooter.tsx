import React from 'react'
import {Button, CardActions} from "@mui/material";

//component imports

//interface imports

type Props = {
    disableButton:boolean,
    onButtonClick:()=>void,
    buttonText:string,
};

function CardFooter({disableButton, onButtonClick, buttonText}: Props){
    return(
        <CardActions sx={{position: "absolute", bottom: 1, left:15}}>
            <Button disabled={disableButton} variant="text"
                    onClick={onButtonClick}>{buttonText}</Button>
        </CardActions>
    )
}

export default CardFooter;
