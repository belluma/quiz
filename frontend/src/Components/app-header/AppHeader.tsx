import React from 'react'


//component imports
import {Grid} from "@mui/material";
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import {useHistory} from "react-router";
//interface imports

type Props = {};

function StartView(props: Props){
    const history = useHistory();
    return(
      <Grid container justifyContent="center" alignItems="center">
          <Grid item>
              <Box>
              <Button onClick={() => history.push('/quiz')} variant="outlined">Start Quiz</Button>
              <Button onClick={() => history.push('/new')} variant="outlined">Create New Card</Button>
              <Button onClick={() => history.push('/all')} variant="outlined">Show All Cards</Button>
              </Box>
          </Grid>
      </Grid>

    )
}

export default StartView;
