import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { logout } from '../../actions/securityActions';

class Header extends Component {
  logout = () => {
    this.props.logout();
    window.location.href = '/';
  };

  render() {
    const { validToken, user, roles } = this.props.security;

    const userIsADMIN = (
      <div className='collapse navbar-collapse' id='mobile-nav'>
        <ul className='navbar-nav ml-auto'>
          <li className='nav-item active'>
            <Link className='nav-link' to='/adminPanel'>
              <i className='fas fa-home'></i> Review Application
            </Link>
          </li>
          <li className='nav-item active'>
            <Link className='nav-link' to='/editTopic'>
              <i className='fas fa-pen'></i> Edit Topic
            </Link>
          </li>
          <li className='nav-item'>
            <Link className='nav-link' to='/logout' onClick={this.logout}>
              <i className='fas fa-sign-out-alt'></i>Logout
            </Link>
          </li>
        </ul>

        <ul className='navbar-nav ml-auto'></ul>
      </div>
    );

    const defaultUser = (
      <div className='collapse navbar-collapse' id='mobile-nav'>
        <ul className='navbar-nav ml-auto'>
          <li className='nav-item active'>
            <Link className='nav-link' to='/dashboard'>
              <i className='fas fa-home'></i> Dashboard
            </Link>
          </li>
          <li className='nav-item active'>
            <Link className='nav-link' to='/search'>
              <i className='fas fa-search'></i> Search Mentor
            </Link>
          </li>
          <li className='nav-item active'>
            <Link className='nav-link' to='/apply'>
              <i className='fas fa-user-edit'></i> Become a Mentor
            </Link>
          </li>
          <li className='nav-item'>
            <Link className='nav-link' to='/logout' onClick={this.logout}>
              <i className='fas fa-sign-out-alt'></i>Logout
            </Link>
          </li>
        </ul>

        <ul className='navbar-nav ml-auto'></ul>
      </div>
    );

    let headerLinks;

    if (validToken && user) {
      if (roles[0].includes('ADMIN')) {
        headerLinks = userIsADMIN;
      } else {
        headerLinks = defaultUser;
      }
    }

    return (
      <nav className='navbar navbar-expand-sm navbar-light bg-light mb-1'>
        <Link className='navbar-brand' to='/' style={{ marginLeft: '10%' }}>
          <img
            width='200px'
            height='60px'
            src='https://svgur.com/i/Nar.svg'
            alt=''
          />
        </Link>

        <button
          className='navbar-toggler'
          type='button'
          data-toggle='collapse'
          data-target='#mobile-nav'
        >
          <span className='navbar-toggler-icon' />
        </button>
        {headerLinks}
      </nav>
    );
  }
}

Header.propTypes = {
  logout: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps, { logout })(Header);
