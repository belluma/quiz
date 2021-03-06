import React from 'react';
import ReactDOM from 'react-dom';
import MainView from './MainView';
import {BrowserRouter as Router} from "react-router-dom";
import {store} from "../../app/store";
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
    ReactDOM.render(<Provider store={store}><Router><MainView/></Router></Provider>, div);
        });
