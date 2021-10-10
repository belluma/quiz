import React from 'react'


//component imports
import {Grid} from "@mui/material";
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
//interface imports

type Props = {};

function StartView(props: Props){
    return(
      <Grid container justifyContent="center" alignItems="center">
          <Grid item>
              <Box>
              <Button variant="outlined">Start Quiz</Button>
              <Button variant="outlined">Create New Card</Button>
              <Button variant="outlined">Show All Cards</Button>
              </Box>
          </Grid>
      </Grid>

    )
}

export default StartView;
