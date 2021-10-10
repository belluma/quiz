import React from 'react';


//component imports
import {Avatar, Card, CardContent, CardHeader, IconButton} from "@mui/material";
import HelpIcon from '@mui/icons-material/Help';
import Choices from "../choices/Choices";
//interface imports

type Props = {};

function Quizcard(props: Props){
    return(
        <Card sx={{ maxWidth: 345 }}>
            <CardHeader
                action={
                    <IconButton aria-label="settings">
                        <HelpIcon />
                    </IconButton>
                }
                title="Shrimp and Chorizo Paella"
                subheader="September 14, 2016"
            />
            <CardContent>
                <Choices />
            </CardContent>
        </Card>

    )
}

export default Card;
