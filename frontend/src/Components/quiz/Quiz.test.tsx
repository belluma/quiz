import React from 'react';
import ReactDOM from 'react-dom';
import { store } from '../../app/store';
import Quiz from './Quiz';
import {Provider} from "react-redux";

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
    ReactDOM.render(<Provider store={store}><Quiz /></Provider>, div);
    });
