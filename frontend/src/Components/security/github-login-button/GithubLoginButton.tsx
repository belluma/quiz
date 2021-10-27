import React, {useEffect, useState} from 'react'
import {getGithubClientId} from "../../../services/authService";

//component imports

import GitHub from "@mui/icons-material/GitHub";
import {IconButton} from "@mui/material";

//interface imports

type Props = {};

function GithubLoginButton(props: Props) {
    const [clientId, setClientId] = useState<string>();
    useEffect(() => {
            getGithubClientId().then(response => {
                setClientId(response.data)
            })
        }
    );

    return (
        <IconButton disabled={!clientId}
            href={`https://github.com/login/oauth/authorize/?client_id=${clientId}&redirect_uri=http://localhost:3000/githubLogin/`}><GitHub/></IconButton>
    )
}

export default GithubLoginButton;
