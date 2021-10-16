import React, {ChangeEvent} from 'react'

//component imports
import {Button,  FormGroup, ThemeProvider} from "@mui/material";
import TextField from "@mui/material/TextField";
import {overrideFontColorOnFocus} from "../../../../theme";

//interface imports

type Props = {
    text: string | undefined,
    handleTextChange: (e: ChangeEvent<HTMLInputElement>) => void,
    disableButton: boolean,
    handleButtonClick: () => void,
    textFieldName:string,
    textFieldLabel:string
}
;

function CustomFormGroup({text, handleTextChange, disableButton, handleButtonClick, textFieldName, textFieldLabel}: Props){
    return(
        <FormGroup>
            <ThemeProvider theme={overrideFontColorOnFocus()}>
                <TextField value={text} name={textFieldName} label={textFieldLabel}
                           onChange={handleTextChange} variant="filled" />
            </ThemeProvider>
            <Button disabled={disableButton} onClick={handleButtonClick} variant="contained">Ok</Button>
        </FormGroup>
    )

}

export default CustomFormGroup;
