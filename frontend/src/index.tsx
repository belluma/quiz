import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { store } from './app/store';
import { Provider } from 'react-redux';
import * as serviceWorker from './serviceWorker';
import {BrowserRouter as Router} from "react-router-dom";
import { ThemeProvider} from "@mui/material";
import theme from './theme'

ReactDOM.render(
  <React.StrictMode>
      <Router>
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
