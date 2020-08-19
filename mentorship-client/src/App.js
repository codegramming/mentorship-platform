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
import MentorDetails from './components/MentorDetails';
import Application from './components/Application';
import Header from './components/Layout/Header';
import OAuth2RedirectHandler from './security/OAuth2RedirectHandler';
import { SET_CURRENT_USER } from './actions/types';
import { logout } from './actions/securityActions';
import SearchMentor from './components/SearchMentor';
import SelectMentor from './components/SelectMentor';
import MentorshipDetails from './components/MentorshipDetails';
import PlanMentorship from './components/PlanMentorship';
import CompletePhase from './components/CompletePhase';

const accessToken = localStorage.accessToken;
const roles = [];
roles[0] = localStorage.roles;
if (accessToken) {
  SetToken(accessToken);
  const decoded_accessToken = jwt_decode(accessToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded_accessToken,
    roles: roles,
  });

  const currentTime = Date.now() / 1000;

  if (decoded_accessToken.exp < currentTime) {
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
            <Route
              exact
              path='/mentorDetails/:id'
              component={(props) => <MentorDetails {...props} />}
            />
            <Route
              exact
              path='/selectMentor/:id'
              component={(props) => <SelectMentor {...props} />}
            />
            <Route
              exact
              path='/mentorshipDetails/:id'
              component={(props) => <MentorshipDetails {...props} />}
            />
            <Route
              exact
              path='/planMentorship/:id'
              component={(props) => <PlanMentorship {...props} />}
            />
            <Route
              exact
              path='/completePhase/:id'
              component={(props) => <CompletePhase {...props} />}
            />
            <Route
              exact
              path='/apply'
              component={(props) => <Application {...props} />}
            />
            <Route
              exact
              path='/search'
              component={(props) => <SearchMentor {...props} />}
            />
            <Route
              exact
              path='/oauth2/redirect'
              component={(props) => <OAuth2RedirectHandler {...props} />}
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
