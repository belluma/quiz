import React from 'react';
import ReactDOM from 'react-dom';
import Login from './Login';
import {fireEvent, render, screen} from '@testing-library/react';


let container: HTMLElement | null = null;
beforeEach(() => {
    container = document.createElement('div');
    document.body.appendChild(container);
});

afterEach(() =>{
    if(container){
    ReactDOM.unmountComponentAtNode(container);
    container.remove();
    }    container = null;
})

it('renders without crashing', () => {
    const div = document.createElement('div');
   ReactDOM.render(<Login />, div);
    });
describe('it renders all expected elements', () =>{
    render(<Login />)
    it('username textfield', () => {

    expect(screen.getByRole("textbox")).toBeInTheDocument();
})
    screen.debug();
it('password textfield', () => {
    expect(screen.getByLabelText(/password/i)).toBeInTheDocument();
})
})

it("shows username after entering in textfield", () => {

})
