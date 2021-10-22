import React from 'react'

import {useHistory} from "react-router";

//component imports
import Button from "@mui/material/Button";
import {ButtonGroup} from "@mui/material";
//interface imports

type Props = {};

function AdminButtons(props: Props){
    const history = useHistory();

    return(
        <ButtonGroup>
            <Button onClick={() => history.push('/new')} variant="contained" size="medium"
                    sx={{bgcolor: "primary.light"}}>Create
                New Card</Button>
            <Button onClick={() => history.push('/all')} variant="contained" size="medium"
                    sx={{bgcolor: "primary.light"}}>Show
                All Cards</Button>
        </ButtonGroup>
    )
}

export default AdminButtons;
