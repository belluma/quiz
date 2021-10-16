import React from 'react'

//component imports
import {CardHeader} from "@mui/material";
import HelpIcon from "@mui/icons-material/Help";

//interface imports

type Props = {
    title: string | undefined,
    clickHandler?: () => void
};

function QuizcardHeader({title, clickHandler}: Props){
    return(
        <CardHeader
            onClick={clickHandler}
            component='h1'

            sx={{bgcolor: 'background.paper', }}
            avatar={<HelpIcon/>}
            title={title ? `${title}?` : title}
            titleTypographyProps={{fontSize: 26}}
        />
    )
}

export default QuizcardHeader;
