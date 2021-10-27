import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { store } from './app/store';
import { Provider } from 'react-redux';
import * as serviceWorker from './serviceWorker';
import {Router as Router} from "react-router-dom";
import { ThemeProvider} from "@mui/material";
import theme from './theme'
import history from './services/history'

ReactDOM.render(
  <React.StrictMode>
      <Router history={history}>
          <ThemeProvider theme={theme}>
    <Provider store={store}>
      <App />
    </Provider>
          </ThemeProvider>
      </Router>
  </React.StrictMode>,
  document.getElementById('root')
);

serviceWorker.unregister();
