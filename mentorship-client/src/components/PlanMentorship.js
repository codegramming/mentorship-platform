import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Header from './Layout/Header';

import axios from 'axios';
let phaseId = 0;

class PlanMentorship extends Component {
  constructor() {
    super();

    this.state = {
      phases: [],
      newPhase: {},
      name: '',
      endDate: '',
      endTime: '09:00',
      success: false,
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
    const { id } = this.props.match.params;
    this.getPhases(id);
  }

  getPhases = async (id) => {
    axios.get(`http://localhost:8080/api/mentorships/${id}`).then((res) => {
      this.setState({ phases: res.data.phases });
    });
  };

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
    const newPhase = {
      phaseId: phaseId,
      name: this.state.name,
      endDate: this.state.endDate,
      endTime: this.state.endTime,
    };
    this.setState({ phases: [...this.state.phases, newPhase] });
  };

  handleDelete = (item) => {
    const filteredItems = this.state.phases.filter(
      (phase) => phase.phaseId !== item.phaseId
    );
    phaseId--;
    this.setState({
      phases: filteredItems,
    });
  };

  savePhases = async (e) => {
    e.preventDefault();
    const { id } = this.props.match.params;
    const phases = this.state.phases;
    for (let i = 0; i < phases.length; i++) {
      await axios.post(
        `http://localhost:8080/api/mentorships/addPhase/${id}`,
        phases[i]
      );
    }

    this.props.history.push(`/mentorshipDetails/${id}`);
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
                <h1 className='display-4 text-center mt-4'>
                  Add Phase to Mentorship
                </h1>
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
                      Phase Name
                    </label>
                    <input
                      type='text'
                      className={
                        errors.name
                          ? 'form-control form-control-lg is-invalid'
                          : 'form-control form-control-lg'
                      }
                      placeholder='Phase name'
                      name='name'
                      value={this.state.name}
                      onChange={this.onChange}
                    ></input>
                    {errors.name && (
                      <div className='invalid-feedback'>{errors.name}</div>
                    )}
                  </div>
                  <div className='row'>
                    <div className='col-md-6'>
                      {' '}
                      <div className='form-group mt-3'>
                        <label class='required-field' for='email'>
                          End Date
                        </label>
                        <input
                          type='date'
                          className={
                            errors.endDate
                              ? 'form-control form-control-lg is-invalid'
                              : 'form-control form-control-lg'
                          }
                          name='endDate'
                          value={this.state.endDate}
                          onChange={this.onChange}
                        />
                        {errors.endDate && (
                          <div className='invalid-feedback'>
                            {errors.endDate}
                          </div>
                        )}
                      </div>
                    </div>
                    <div className='col-md-6'>
                      {' '}
                      <div className='form-group mt-3'>
                        <label class='required-field' for='email'>
                          End Time
                        </label>
                        <select
                          name='endTime'
                          value={this.state.endTime}
                          class='custom-select my-1 mr-sm-2'
                          id='inlineFormCustomSelectPref'
                          onChange={this.onChange}
                        >
                          <option selected>09:00</option>
                          <option>10:00</option>
                          <option>11:00</option>
                          <option>12:00</option>
                          <option>13:00</option>
                          <option>14:00</option>
                          <option>15:00</option>
                          <option>16:00</option>
                          <option>17:00</option>
                        </select>
                      </div>
                    </div>
                  </div>
                  <input
                    type='submit'
                    className='btn btn-primary btn-block mt-4'
                  />
                </form>
                <div
                  className='row'
                  style={{ marginTop: '3rem', marginBottom: '5rem' }}
                >
                  <div className='col-md-12 m-auto'>
                    <h1 className='display-4 text-center mt-4'>Phases</h1>
                    <hr />

                    <div class='job-list__wrapper mb-4'>
                      {this.state.phases.length === 0 ? (
                        <div
                          class='alert alert-info text-center'
                          role='alert'
                          style={{ marginTop: 30 }}
                        >
                          <h3>There is no phase</h3>
                        </div>
                      ) : (
                        this.state.phases.map((phase, index) => {
                          return (
                            <>
                              <div key={index}>
                                <Link
                                  onClick={() => this.handleDelete(phase)}
                                  class='card p-0 mb-3 border-0 shadow-sm shadow--on-hover'
                                >
                                  <div class='card-body'>
                                    <span class='row justify-content-between align-items-center'>
                                      <span class='col-md-2 color--heading'>
                                        <span class='badge badge-circle background--success text-white mr-6'>
                                          {phase.phaseId}
                                        </span>{' '}
                                      </span>
                                      <span class='col-md-5 my-3 my-sm-0 color--text'>
                                        <i class='fas fa-book-reader'></i>{' '}
                                        {phase.name}
                                      </span>

                                      <span class='col-md-4 my-3 my-sm-0 color--text'>
                                        <i class='fas fa-calendar-alt'></i>{' '}
                                        {phase.endDate + ' ' + phase.endTime}
                                      </span>

                                      <h3>
                                        {' '}
                                        <span class='d-none d-md-block col-1 text-center text-danger'>
                                          <i class='fa fa-trash'></i>
                                        </span>
                                      </h3>
                                    </span>
                                  </div>
                                </Link>
                              </div>
                            </>
                          );
                        })
                      )}
                    </div>
                  </div>
                  {this.state.phases.length !== 0 && (
                    <div className='col text-center'>
                      {' '}
                      <button
                        className='btn btn-success text-center'
                        style={{ width: '100%' }}
                        onClick={this.savePhases}
                      >
                        SAVE ALL PHASES
                      </button>
                    </div>
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
    );
  }
}

PlanMentorship.propTypes = {
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
  security: state.security,
});

export default connect(mapStateToProps, {})(PlanMentorship);
