import React from 'react';
import ReactDOM from 'react-dom';
import CustomFormGroup from './CustomFormGroup';
import {render, screen, fireEvent} from "@testing-library/react";
import userEvent from "@testing-library/user-event";

let container: HTMLElement | null = null;
beforeEach(() => {
    container = document.createElement('div');
    document.body.appendChild(container);
});

afterEach(() => {
    if (container) {
        ReactDOM.unmountComponentAtNode(container);
        container.remove();
    }
    container = null;
})

const props = {
    text: "text",
    handleTextChange: jest.fn(),
    disableButton: false,
    handleButtonClick: jest.fn(),
    textFieldName: "textFieldName",
    textFieldLabel: "textFieldLabel"
}
const {text, handleTextChange, disableButton, handleButtonClick, textFieldName, textFieldLabel,} = props;

it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CustomFormGroup text={text} handleTextChange={handleTextChange} disableButton={disableButton}
                                     handleButtonClick={handleButtonClick} textFieldName={textFieldName}
                                     textFieldLabel={textFieldLabel}/>, div);
});

test("click handler gets executed on button click", () => {
    render(<CustomFormGroup text={text} handleTextChange={handleTextChange} disableButton={false}
                            handleButtonClick={handleButtonClick} textFieldName={textFieldName}
                            textFieldLabel={textFieldLabel}/>);
    const button = screen.getByRole("button");
    fireEvent.click(button);
    expect(handleButtonClick).toHaveBeenCalledTimes(1);
})

test("button gets disabled via props", () =>{
    render(<CustomFormGroup text={text} handleTextChange={handleTextChange} disableButton={true}
                            handleButtonClick={handleButtonClick} textFieldName={textFieldName}
                            textFieldLabel={textFieldLabel}/>);
    const button = screen.getByRole("button");
    fireEvent.click(button);
    expect(handleButtonClick).toHaveBeenCalledTimes(0);
})

test("textchange gets fired on text entry", () => {
    render(<CustomFormGroup text={undefined} handleTextChange={handleTextChange} disableButton={disableButton}
                            handleButtonClick={handleButtonClick} textFieldName={textFieldName}
                            textFieldLabel={textFieldLabel}/>);
    const textField:HTMLInputElement = screen.getByRole('textbox');
    screen.debug();
    console.log(textField);
    userEvent.type(textField, "abc");
    expect(textField).toHaveValue("abc");
    expect(handleTextChange).toHaveBeenCalledTimes(3);
})
