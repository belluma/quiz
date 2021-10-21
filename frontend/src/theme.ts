import {createTheme, ThemeOptions} from "@mui/material";

 const theme:ThemeOptions = createTheme({
         palette: {
             primary: {
                 main: '#fca311',
             },
             secondary: {
                 main: '#000000',
             },
             background: {
                 default: '#14213d',
                 paper: '#e5e5e5',
             },
         },
     //     primary: {
     //         main: '#546A7B',
     //     },
     //     secondary: {
     //         main: '#c6c5b9',
     //     },
     //     background: {
     //         default: '#393d3f',
     //         paper: '#62929e',
     //     },
     // },
     // palette: {
     //     primary: {
     //         main: '#ffb300',
     //
     //     },
     //     secondary: {
     //         main: '#5c6bc0',
     //     },
     //     background: {
     //         default: '#fff9c4',
     //         paper: '#f3e5f5',
     //     },
     //     success: {
     //         main: '#16e21d',
     //     },
     // },
});
export const overrideFontColorOnFocus = ():ThemeOptions => {
    return createTheme({
        palette:{
            primary:{
                main:"#000"
            }
        }
    })
}
export const overrideBackgroundForError = ():ThemeOptions => {
    return createTheme({
        palette:{
            primary:{
                main:"red"
            }
        }
    })
}


export default theme
