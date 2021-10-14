import React from 'react'


//component imports
import {
    AppBar,
    Container,
    CssBaseline,
    Grid,
    Slide,
    Toolbar,
    Typography,
    useScrollTrigger
} from "@mui/material";
import {styled} from '@mui/material/styles'
import Button from '@mui/material/Button';
import {Route, useHistory} from "react-router";
import Quiz from "../quiz/Quiz";
import CardCreationDialog from "../quizcard/card-creation-dialog/CardCreationDialog";
import AllCards from "../all-cards/AllCards";
//interface imports

type Props = {
    children?: React.ReactElement;
};
const StyledToolbar = styled(Toolbar)(({ theme }) => ({
    alignItems: 'flex-start',
    paddingTop: theme.spacing(1),
    paddingBottom: theme.spacing(2),
    '@media all': {
        minHeight: 128,
    },
}));
function AppHeader(props: Props) {
    const history = useHistory();
    return (
        <React.Fragment>
            <CssBaseline/>
            <HideOnScroll {...props}>
                <AppBar>
                    <StyledToolbar>
                        <Typography>Quiz with no name</Typography>
                            <Button onClick={() => history.push('/quiz')} variant="contained" size="small" color="secondary">Start Quiz</Button>
                            <Button onClick={() => history.push('/new')} variant="contained" size="small" color="secondary">Create New Card</Button>
                            <Button onClick={() => history.push('/all')} variant="contained" size="small" color="secondary">Show All Cards</Button>
                    </StyledToolbar>
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
