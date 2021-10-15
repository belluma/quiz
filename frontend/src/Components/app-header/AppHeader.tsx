import React, {useState} from 'react'
import AllCards from "../all-cards/AllCards";
import {Route, useHistory} from "react-router";
import 'bulma/css/bulma.css';


//component imports
import {
    AppBar,
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
import Quiz from "../quiz/Quiz";
import CardCreationDialog from "../quizcard/card-creation-dialog/CardCreationDialog";

//interface imports

type Props = {
    children?: React.ReactElement;
};


function AppHeader(props: Props) {
    const history = useHistory();
    const [admin, setAdmin] = useState(false);
    return (
        <React.Fragment>
            <CssBaseline/>
            <HideOnScroll {...props}>
                <AppBar color="secondary">
                    <Toolbar>
                        <Typography>Codificantes</Typography>
                        <IconButton onClick={() => setAdmin(!admin)} edge="end" sx={{color: 'primary.main'}}>
                            <EditIcon/>
                        </IconButton>
                    </Toolbar>
                    <Toolbar sx={{alignItems: "flex-start", justifyContent: "space-between"}}>
                        {!admin ?
                            <Button
                                onClick={() => history.push('/quiz')} variant="contained" size="small" color="primary">Start
                                Quiz</Button> :
                            <>
                                <Button onClick={() => history.push('/new')} variant="contained" size="small"
                                        color="primary">Create
                                    New Card</Button>
                                <Button onClick={() => history.push('/all')} variant="contained" size="small"
                                        color="primary">Show
                                    All Cards</Button>
                            </>}
                    </Toolbar>
                </AppBar>
            </HideOnScroll>
            <Toolbar/>
            <Container>
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
            <Slide appear={false} direction="down" in={!trigger}>
                {children}
            </Slide>
        );
    }


}

export default AppHeader;
