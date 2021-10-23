import React, {useEffect, useState} from 'react'
import 'bulma/css/bulma.css';


//component imports
import {
    AppBar,
    IconButton,
    Slide,
    Toolbar,
    Typography,
    useScrollTrigger
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import LogoutIcon from "@mui/icons-material/Logout";
import StartButton from "./start-button/StartButton";
import AdminButtons from "./admin-buttons/AdminButtons";
import {useAppDispatch, useAppSelector} from "../../app/hooks";
import {logout, selectLoggedIn} from "../../Slicer/AuthSlice";
import {useHistory} from "react-router";

//interface imports

type Props = {
    children?: React.ReactElement;
};

function AppHeader(props: Props) {
    const history = useHistory();
    const dispatch = useAppDispatch();
    const loggedIn = useAppSelector(selectLoggedIn)
    const [admin, setAdmin] = useState(false);
    const [scrollTrigger, setScrollTrigger] = useState<boolean>(window.innerWidth < 900);
    const handleLogout = () => {
        dispatch(logout());
        history.push('/logout')
    }
    const handleResize = () => {
        if (window.innerWidth < 900 && !scrollTrigger) setScrollTrigger(true)
        if (window.innerWidth >= 900 && scrollTrigger) setScrollTrigger(false)
    }
    useEffect(() => {
        window.addEventListener("resize", handleResize, false);
    })
    return (
        <HideOnScroll {...props}>
            <AppBar sx={{bgcolor: 'primary.light'}}>
                <Toolbar sx={{justifyContent: "space-between"}}>
                    <Typography>Codificantes</Typography>
                    <IconButton onClick={() => setAdmin(!admin)} edge="end">
                        <EditIcon/>
                    </IconButton>
                </Toolbar>
                <Toolbar sx={{mb: 1, alignItems: "stretch", justifyContent: "space-between"}}>
                    {admin ? <AdminButtons/> : <StartButton/>}
                    {loggedIn && <IconButton onClick={handleLogout} edge="end">
                        <LogoutIcon/>
                    </IconButton>}
                </Toolbar>
            </AppBar>
        </HideOnScroll>
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
