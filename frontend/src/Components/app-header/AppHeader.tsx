import React, {useEffect, useState} from 'react'
import AllCards from "../all-cards/AllCards";
import {Route, useHistory} from "react-router";
import 'bulma/css/bulma.css';


//component imports
import {
    AppBar, ButtonGroup,
    Container,
    CssBaseline,
    Grid,
    IconButton,
    Slide,
    Toolbar,
    Typography,
    useScrollTrigger
} from "@mui/material";
import Button from '@mui/material/Button';
import EditIcon from "@mui/icons-material/Edit";
import PlayArrowIcon from "@mui/icons-material/PlayArrow";
import Quiz from "../quiz/Quiz";
import CardCreationDialog from "../quizcard/card-creation-dialog/CardCreationDialog";

//interface imports

type Props = {
    children?: React.ReactElement;
};


function AppHeader(props: Props) {
    const history = useHistory();
    const [admin, setAdmin] = useState(false);
    const [scrollTrigger, setScrollTrigger] = useState<boolean>(window.innerWidth < 900);
    const handleResize = () => {
        if (window.innerWidth < 900 && !scrollTrigger) setScrollTrigger(true)
        if (window.innerWidth >= 900 && scrollTrigger) setScrollTrigger(false)
    }
    useEffect(() => {
        window.addEventListener("resize", handleResize, false);
    })


    return (
        <React.Fragment>
            <CssBaseline/>
            <HideOnScroll {...props}>
                <AppBar sx={{bgcolor:'primary.light'}}>
                    <Toolbar sx={{justifyContent:"space-between"}}>
                        <Typography>Codificantes</Typography>
                        <IconButton onClick={() => setAdmin(!admin)} edge="end"
                                    >
                            <EditIcon/>
                        </IconButton>
                    </Toolbar>
                    <Toolbar sx={{mb:1, alignItems: "stretch", justifyContent: "space-between"}}>
                        {!admin ?
                            <ButtonGroup  >
                                <Button endIcon={<PlayArrowIcon/>}
                                        onClick={() => history.push('/quiz')} variant="contained" size="large"
                                      sx={{bgcolor:"primary.light"}}  >Start
                                    Quiz</Button>
                            </ButtonGroup> :
                            <ButtonGroup>
                                <Button onClick={() => history.push('/new')} variant="contained" size="medium"
                                        sx={{bgcolor:"primary.light"}}>Create
                                    New Card</Button>
                                <Button onClick={() => history.push('/all')} variant="contained" size="medium"
                                        sx={{bgcolor:"primary.light"}}>Show
                                    All Cards</Button>
                            </ButtonGroup>}
                    </Toolbar>
                </AppBar>
            </HideOnScroll>
            <Toolbar/>
            <Container sx={{pt: 15}} maxWidth={false}>
                <Grid container justifyContent="center" alignItems="center">
                    <Route path="/quiz" component={Quiz}/>
                    <Route path="/new" component={CardCreationDialog}/>
                    <Route path="/all" component={AllCards}/>
                </Grid>
            </Container>
        </React.Fragment>
    )
    function HideOnScroll(props: Props) {
        const {children} = props;
        const trigger = useScrollTrigger({});
        return (
            <Slide appear={false} direction="down" in={!scrollTrigger ? true : !trigger}>
                {children}
            </Slide>
        );
    }
}

export default AppHeader;
