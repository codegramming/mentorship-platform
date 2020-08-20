import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Header from './Layout/Header';

import axios from 'axios';
let phaseId = 0;

class CompletePhase extends Component {
  constructor() {
    super();

    this.state = {
      assessment: '',
      rating: 1,
      errors: {},
    };
  }

  componentDidMount() {
    if (
      this.props.security.roles[0] == 'ADMIN' &&
      !this.props.security.validToken
    ) {
      this.props.history.push('/adminPanel');
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  onChange = (e) => {
    this.setState({ [e.target.name]: e.target.value, errors: {} });
  };

  onSubmit = async (e) => {
    e.preventDefault();
    phaseId++;
    const completedPhase = {
      assessment: this.state.assessment,
      rating: this.state.rating,
    };
    const { id } = this.props.match.params;
    await axios
      .post(
        `http://localhost:8080/api/mentorships/completePhase/${id}`,
        completedPhase
      )
      .then(() => {
        this.props.history.goBack();
      });
  };

  render() {
    const { errors } = this.state;

    return (
      <>
        <Header />
        <div className='project'>
          <div className='container'>
            <div className='row'>
              <div className='col-md-10 m-auto'>
                <h1 className='display-4 text-center mt-4'>Complete Phase</h1>
                <hr />

                {Object.keys(errors).length !== 0 && (
                  <div class='alert alert-danger' role='alert'>
                    Please fill out all fields.
                  </div>
                )}

                {this.state.submit === true && (
                  <div class='alert alert-success' role='alert'>
                    Congratulations, your application has been received. You
                    will be redirected in 3 seconds.
                  </div>
                )}

                <form onSubmit={this.onSubmit}>
                  <div className='form-group mt-3'>
                    <label class='required-field' for='email'>
                      Assessment
                    </label>
                    <input
                      type='text'
                      className={
                        errors.name
                          ? 'form-control form-control-lg is-invalid'
                          : 'form-control form-control-lg'
                      }
                      placeholder='Assessment'
                      name='assessment'
                      value={this.state.assessment}
                      onChange={this.onChange}
                    ></input>
                    {errors.assessment && (
                      <div className='invalid-feedback'>
                        {errors.assessment}
                      </div>
                    )}
                  </div>
                  <div className='form-group mt-3'>
                    <label class='required-field' for='email'>
                      Rating
                    </label>
                    <select
                      name='rating'
                      value={this.state.rating}
                      class='custom-select my-1 mr-sm-2'
                      id='inlineFormCustomSelectPref'
                      onChange={this.onChange}
                    >
                      <option selected value='1'>
                        1
                      </option>
                      <option value='2'>2</option>
                      <option value='3'>3</option>
                      <option value='4'>4</option>
                      <option value='5'>5</option>
                    </select>
                    {errors.rating && (
                      <div className='invalid-feedback'>{errors.rating}</div>
                    )}
                  </div>
                  <input
                    type='submit'
                    className='btn btn-primary btn-block mt-4'
                  />
                </form>
              </div>
            </div>
          </div>
        </div>
      </>
    );
  }
}

CompletePhase.propTypes = {
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
  security: state.security,
});

export default connect(mapStateToProps, {})(CompletePhase);
