import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { login } from '../../actions/securityActions';
import createHistory from 'history/createBrowserHistory';
const history = createHistory();

class Login2 extends Component {
  constructor() {
    super();

    this.state = {
      username: '',
      password: '',
      errors: {},
    };
  }

  componentDidMount() {
    if (this.props.security.validToken) {
      console.log(this.props.security);
      if (this.props.security.roles[0].includes('ADMIN')) {
        this.props.history.push('/adminPanel');
      } else {
        this.props.history.push('/dashboard');
      }
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.security.validToken) {
      history.go(0);
      this.props.history.push('/dashboard');
    }

    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  onChange = (e) => {
    this.setState({
      [e.target.name]: e.target.value,
      errors: {},
    });
  };

  onSubmit = (e) => {
    e.preventDefault();
    const loginRequest = {
      username: this.state.username,
      password: this.state.password,
    };

    this.props.login(loginRequest);
  };

  render() {
    const { errors } = this.state;
    return (
      <div class='container-fluid'>
        <div class='row min-vh-100 align-items-stretch'>
          <div class='col-lg-7 d-none d-lg-flex justify-content-center align-items-center gradient-brand-color'>
            <div class='w-66'>
              <h2 class='color--white mb-5'>
                We helps your business grow without compromise.
              </h2>
              <img
                class='ml-4 mb-n4'
                src='https://weboth.us/preview/fluxo-v1.0.0/html/images/image-login-chart.png'
                srcset='images/image-login-chart@2x.png 2x'
                alt='Fluxo Login Page'
              />
            </div>
          </div>

          <div class='col-lg-5 d-flex align-items-center py-5 background--light'>
            <div class='w-75 mx-auto overflow-hidden px-md-5 px-lg-3'>
              <header class='mb-4'>
                <a class='cover-page-brand' href='index.html'>
                  <img
                    src='https://weboth.us/preview/fluxo-v1.0.0/html/images/logo.svg'
                    alt='Fluxo'
                  />
                </a>
              </header>

              <main class='main'>
                <h3 class='mb-2'>Login to your account</h3>

                <form class='login-form' onSubmit={this.onSubmit}>
                  <div class='row'>
                    <div class='col-sm-12 mb-2'>
                      <div class='form-group'>
                        {errors.message && (
                          <div class='alert alert-danger' role='alert'>
                            The username or password is incorrect please try
                            again.
                          </div>
                        )}
                        <label for='email'>Username</label>
                        <input
                          type='text'
                          className={
                            errors.username
                              ? 'form-control form-control-lg is-invalid'
                              : 'form-control form-control-lg'
                          }
                          id='text'
                          placeholder='Username'
                          name='username'
                          value={this.state.username}
                          onChange={this.onChange}
                        />
                        {errors.username && (
                          <div className='invalid-feedback'>
                            {errors.username}
                          </div>
                        )}
                      </div>
                    </div>

                    <div class='col-sm-12 mb-2'>
                      <div class='form-group'>
                        <label for='email'>Password</label>
                        <input
                          type='password'
                          className={
                            errors.password
                              ? 'form-control form-control-lg is-invalid'
                              : 'form-control form-control-lg'
                          }
                          id='password'
                          placeholder='Password'
                          name='password'
                          value={this.state.password}
                          onChange={this.onChange}
                        />
                        {errors.password && (
                          <div className='invalid-feedback'>
                            {errors.password}
                          </div>
                        )}
                      </div>
                    </div>

                    <div class='col-sm-12 mb-2'>
                      <button
                        type='submit'
                        name='submit'
                        class='btn btn-primary w-100'
                      >
                        Log In
                      </button>
                    </div>

                    <div class='col-sm-12 mb-2 text-center position-relative pt-2 pb-3 px-0'>
                      <hr
                        class='mw-100 position-absolute z-index-100'
                        style={{ width: 'calc(100%-30px)', marginLeft: '15px' }}
                      />
                      <small class='px-3 background--light position-relative z-index-105'>
                        Or log in with:
                      </small>
                    </div>

                    <div className='col-sm-3'></div>
                    <div class='col-sm-6 mb-2'>
                      <a
                        href='#0'
                        class='btn background--white border w-100 px-2'
                      >
                        <figure class='d-inline-block mr-1 mb-0'>
                          <svg
                            viewBox='0 0 48 48'
                            width='16'
                            height='16'
                            class='google-icon display-block'
                            data-reactid='93'
                          >
                            <defs data-reactid='94'>
                              <clipPath id='clip-path' data-reactid='95'>
                                <path
                                  fill='none'
                                  d='M44.5,20H24v8.5H35.8C34.7,33.9,30.1,37,24,37a13,13,0,0,1,0-26,12.72,12.72,0,0,1,8.1,2.9l6.4-6.4A22,22,0,1,0,24,46c11,0,21-8,21-22A18.25,18.25,0,0,0,44.5,20Z'
                                  data-reactid='96'
                                ></path>
                              </clipPath>
                            </defs>
                            <g clip-path='url(#clip-path)' data-reactid='97'>
                              <path
                                style={{ fill: '#fbbc05' }}
                                d='M0,37V11L17,24Z'
                                data-reactid='98'
                              ></path>
                            </g>
                            <g clip-path='url(#clip-path)' data-reactid='99'>
                              <path
                                style={{ fill: '#ea4335' }}
                                d='M0,11,17,24l7-6.1L48,14V0H0Z'
                                data-reactid='100'
                              ></path>
                            </g>
                            <g clip-path='url(#clip-path)' data-reactid='101'>
                              <path
                                style={{ fill: '#34a853' }}
                                d='M0,37,30,14l7.9,1L48,0V48H0Z'
                                data-reactid='102'
                              ></path>
                            </g>
                            <g clip-path='url(#clip-path)' data-reactid='103'>
                              <path
                                style={{ fill: '#4285f4' }}
                                d='M48,48,17,24l-4-3L48,11Z'
                                data-reactid='104'
                              ></path>
                            </g>
                          </svg>
                        </figure>
                        Google
                      </a>
                    </div>
                  </div>
                </form>
              </main>

              <footer class='mt-6'>
                <small>©2020 - Tayfur Unal. All rights reserved.</small>
              </footer>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Login2.propTypes = {
  login: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
  errors: state.errors,
});

export default connect(mapStateToProps, { login })(Login2);
