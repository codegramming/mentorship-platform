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
              Manager Panel
            </Link>
          </li>
          <li className='nav-item active'>
            <Link className='nav-link' to='/applications'>
              Applications
            </Link>
          </li>
          <li className='nav-item'>
            <Link className='nav-link' to='/logout' onClick={this.logout}>
              Logout
            </Link>
          </li>
        </ul>

        <ul className='navbar-nav ml-auto'></ul>
      </div>
    );

    const defaultUser = (
      <div className='collapse navbar-collapse' id='mobile-nav'>
        <ul className='navbar-nav'>
          <li className='nav-item'>
            <Link className='nav-link active' to='/'>
              Jobs
            </Link>
          </li>
          <li className='nav-item active'>
            <Link className='nav-link' to={`/${user.sub}/applications`}>
              My Applications
            </Link>
          </li>
        </ul>

        <ul className='navbar-nav ml-auto'>
          <li className='nav-item'>
            <Link className='nav-link'>
              <i className='fas fa-user-circle mr-1' />
              {user.sub}
            </Link>
          </li>
          <li className='nav-item'>
            <Link className='nav-link' to='/logout' onClick={this.logout}>
              <i class='fas fa-camera'></i>
              Logout
            </Link>
          </li>
        </ul>
      </div>
    );

    let headerLinks;

    if (validToken && user) {
      if (roles[0].includes('ADMIN')) {
        console.log(roles[0]);
        headerLinks = userIsADMIN;
      } else {
        headerLinks = defaultUser;
      }
    }

    return (
      <nav className='navbar navbar-expand-sm navbar-light bg-light mb-1'>
        <Link className='navbar-brand' to='/' style={{ marginLeft: '10%' }}>
          Mentorship
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
