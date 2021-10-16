import {createTheme, ThemeOptions} from "@mui/material";

 const theme:ThemeOptions = createTheme({
     palette: {
         primary: {
             main: '#ffb300',

         },
         secondary: {
             main: '#5c6bc0',
         },
         background: {
             default: '#fff9c4',
             paper: '#f3e5f5',
         },
         success: {
             main: '#16e21d',
         },
     },
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


export default theme
