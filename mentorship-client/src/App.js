import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';
import jwt_decode from 'jwt-decode';
import SetToken from './security/SetToken';

import SecureRoute from './security/SecureRoutes';
import Login from './components/Authentication/Login';
import AdminPanel from './components/AdminPanel';
import Dashboard from './components/Dashboard';
import Header from './components/Layout/Header';

import { SET_CURRENT_USER } from './actions/types';
import { logout } from './actions/securityActions';

//import './App.css';
//import 'bootstrap/dist/css/bootstrap.min.css';

const jwtToken = localStorage.jwtToken;
const roles = [];
roles[0] = localStorage.roles;
if (jwtToken) {
  SetToken(jwtToken);
  const decoded_jwtToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded_jwtToken,
    roles: roles,
  });

  const currentTime = Date.now() / 1000;

  if (decoded_jwtToken.exp < currentTime) {
    store.dispatch(logout());
    window.location.href = '/';
  }
}

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className='App'>
          <Route exact path='/' component={Login} />
          <Switch>
            <Route
              exact
              path='/adminPanel'
              component={(props) => <AdminPanel {...props} />}
            />
            <Route
              exact
              path='/dashboard'
              component={(props) => <Dashboard {...props} />}
            />
          </Switch>

          {/* <Switch>
            <SecureRoute exact path='/adminPanel' component={AdminPanel} />
            <SecureRoute exact path='/dashboard' component={Dashboard} />
            <SecureRoute
              exact
              path='/application/:id'
              component={Application}
            /> 
          </Switch> */}
        </div>
      </Router>
    </Provider>
  );
}

export default App;
