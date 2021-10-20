import React from 'react';
import ReactDOM from 'react-dom';
import CardFooter from './CardFooter';
import {fireEvent, render, screen} from "@testing-library/react";

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

it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CardFooter disableButton={false} onButtonClick={() => {
    }} buttonText={"button"} footerText={"footer"}/>, div);
});

describe("text props get forwarded", () => {
    render(<CardFooter disableButton={false} onButtonClick={() => {
    }} buttonText={"button"} footerText={"footer"}/>)
    test("to button", () => {
        expect(screen.getByText("button")).toBeInTheDocument();
    })
    test("to typography component", () => {
        expect(screen.getByText("footer")).toBeInTheDocument();
    })
})

test("onButtonClick function gets executed on buttton click", () => {
    const onButtonClick = jest.fn();
    render(<CardFooter disableButton={false} onButtonClick={onButtonClick} buttonText={"button"}
                       footerText={"footer"}/>)
    const buttons = screen.getAllByRole("button", {name:"button"})
    buttons.forEach((button) => fireEvent.click(button))
    expect(onButtonClick).toHaveBeenCalledTimes(1);
})

test("button gets disabled via props", () => {
    const onButtonClick = jest.fn();
    render(<CardFooter disableButton={true} onButtonClick={onButtonClick} buttonText={"button"}
                       footerText={"footer"}/>)
    const buttons = screen.getAllByRole("button", {name:"button"})
    buttons.forEach((button) => fireEvent.click(button))
    expect(onButtonClick).toHaveBeenCalledTimes(0);
})
