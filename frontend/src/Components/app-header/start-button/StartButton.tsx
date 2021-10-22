import React from 'react'
import {useHistory} from "react-router";

//component imports
import {ButtonGroup} from "@mui/material";
import Button from "@mui/material/Button";
import PlayArrowIcon from "@mui/icons-material/PlayArrow";

//interface imports

type Props = {};

function StartButton(props: Props){
    const history = useHistory();
    return(
        <ButtonGroup>
            <Button endIcon={<PlayArrowIcon/>}
                    onClick={() => history.push('/quiz')} variant="contained" size="large"
                    sx={{bgcolor: "primary.light"}}>Start
                Quiz</Button>
        </ButtonGroup>
    )
}

export default StartButton;
