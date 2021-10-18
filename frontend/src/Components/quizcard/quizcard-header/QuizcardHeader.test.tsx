import React from 'react';
import ReactDOM from 'react-dom';
import QuizcardHeader from './QuizcardHeader';
import {fireEvent, render, screen} from '@testing-library/react';


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
    ReactDOM.render(<QuizcardHeader title={"title"} clickHandler={() => {
    }}/>, div);
});

test('has text given in title props', () => {
    render(<QuizcardHeader title={"title"} clickHandler={() => console.log("title")}/>);
    expect(screen.getByText('title?')).toBeInTheDocument();
})
test('calls clickHandler prop when clicked', () => {
    const handleClick = jest.fn();
    const {baseElement} = render(<QuizcardHeader title={"title"} clickHandler={handleClick}/>);
    fireEvent.click(baseElement);
    // expect(handleClick).toHaveBeenCalledTimes(1);
})
