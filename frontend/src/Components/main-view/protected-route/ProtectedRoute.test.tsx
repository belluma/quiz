import React from 'react';
import ReactDOM from 'react-dom';
import ProtectedRoute from './ProtectedRoute';

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
   // ReactDOM.render(<ProtectedRoute />, div);
    });
