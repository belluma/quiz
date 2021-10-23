import React from 'react'

//component imports
import {DataGrid, GridRowsProp, GridColDef} from "@mui/x-data-grid";

//interface imports
import {IHighscore} from "../../../Interfaces/IUser";

type Props = {
    highscores: IHighscore[]
};


const columns: GridColDef[] = [
    {field: 'col1', headerName: 'Username', width: 300},
    {field: 'col2', headerName: 'Points', width: 200},
    {field: 'col3', headerName: 'Date', width: 200},
];

function Highscore({highscores}: Props) {
    const rows: GridRowsProp = !highscores ? [] : highscores.map(highscore => {
        const {id, username, date, score} = highscore
        return {id: id, col1: username, col2: score, col3: date}
    });
    return (
        <div style={{height: 500, maxHeight: "100%", width: 700, maxWidth: "100%"}}>
            <DataGrid rows={rows} columns={columns}/>
        </div>
    )
}

export default Highscore;
