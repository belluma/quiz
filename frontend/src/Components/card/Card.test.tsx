import React from 'react';
import ReactDOM from 'react-dom';
import Card from './Card';

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
   ReactDOM.render(<Card />, div);
    });
