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
      endDate: new Date().toISOString().slice(0, 10),
      endTime: '23:00',
      mentorEmail: '',
      menteeEmail: '',
      success: true,
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
      this.setState({
        phases: res.data.phases,
        mentorEmail: res.data.mentor.user.email,
        menteeEmail: res.data.mentee.user.email,
      });
    });
  };

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  onChange = (e) => {
    this.setState({
      [e.target.name]: e.target.value,
      errors: {},
      success: true,
    });
  };

  onSubmit = async (e) => {
    e.preventDefault();
    phaseId++;
    if (this.state.endDate !== '' && this.state.name !== '') {
      const customEndDate = `${this.state.endDate}T${this.state.endTime}:00`;
      const customEndTime = `${this.state.endDate} ${this.state.endTime}`;
      const newPhase = {
        phaseId: phaseId,
        name: this.state.name,
        endDate: customEndDate,
        endTime: customEndTime,
      };
      this.setState({ phases: [...this.state.phases, newPhase], errors: '' });
    } else {
      const dateError = {
        clientError: 'All fields are required',
      };
      this.setState({ errors: dateError });
    }
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
      await axios
        .post(`http://localhost:8080/api/scheduleEmail`, {
          email: this.state.mentorEmail,
          subject: 'Notification',
          body: 'The phase you have received will be completed within 1 hour.',
          dateTime: phases[i].endDate,
          timeZone: 'Turkey',
        })
        .catch((error) => {
          this.setState({
            errors: error.response.data.message,
            success: false,
          });
        });

      await axios
        .post(`http://localhost:8080/api/scheduleEmail`, {
          email: this.state.menteeEmail,
          subject: 'Notification',
          body: 'The phase you have received will be completed within 1 hour.',
          dateTime: phases[i].endDate,
          timeZone: 'Turkey',
        })
        .then(async () => {
          await axios.post(
            `http://localhost:8080/api/mentorships/addPhase/${id}`,
            phases[i]
          );
        })
        .catch((error) => {
          this.setState({
            errors: error.response.data.validationErrors,
            success: false,
          });
        });
    }

    if (this.state.success) {
      this.props.history.push(`/mentorshipDetails/${id}`);
    }
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
                  <div className='alert alert-danger' role='alert'>
                    {errors.name}
                    {errors.date}
                    {errors.clientError}
                  </div>
                )}

                {this.state.submit === true && (
                  <div className='alert alert-success' role='alert'>
                    Congratulations, your application has been received. You
                    will be redirected in 3 seconds.
                  </div>
                )}

                <form onSubmit={this.onSubmit}>
                  <div className='form-group mt-3'>
                    <label className='required-field' htmlFor='email'>
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
                        <label className='required-field' htmlFor='email'>
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
                        {errors.date && (
                          <div className='invalid-feedback'>{errors.date}</div>
                        )}
                      </div>
                    </div>
                    <div className='col-md-6'>
                      {' '}
                      <div className='form-group mt-3'>
                        <label className='required-field' htmlFor='email'>
                          End Time
                        </label>
                        <select
                          name='endTime'
                          value={this.state.endTime}
                          className='custom-select my-1 mr-sm-2'
                          id='inlineFormCustomSelectPref'
                          onChange={this.onChange}
                          placeholder='sd'
                        >
                          <option>09:00</option>
                          <option>09:30</option>
                          <option>10:00</option>
                          <option>10:30</option>
                          <option>11:00</option>
                          <option>11:30</option>
                          <option>12:00</option>
                          <option>12:12</option>
                          <option>12:30</option>
                          <option>13:00</option>
                          <option>13:30</option>
                          <option>14:00</option>
                          <option>14:30</option>
                          <option>15:00</option>
                          <option>15:30</option>
                          <option>16:00</option>
                          <option>16:30</option>
                          <option>17:00</option>
                          <option>17:30</option>
                          <option>18:00</option>
                          <option>18:30</option>
                          <option>19:00</option>
                          <option>19:30</option>
                          <option>20:00</option>
                          <option>20:30</option>
                          <option>21:00</option>
                          <option>21:30</option>
                          <option>22:00</option>
                          <option>22:30</option>
                          <option>23:00</option>
                          <option>23:30</option>
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

                    <div className='job-list__wrapper mb-4'>
                      {this.state.phases.length === 0 ? (
                        <div
                          className='alert alert-info text-center'
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
                                  className='card p-0 mb-3 border-0 shadow-sm shadow--on-hover'
                                >
                                  <div className='card-body'>
                                    <span className='row justify-content-between align-items-center'>
                                      <span className='col-md-1 color--heading'>
                                        <span className='badge badge-circle background--success text-white mr-6'>
                                          {phase.phaseId}
                                        </span>{' '}
                                      </span>
                                      <span className='col-md-5 my-3 my-sm-0 color--text'>
                                        <i className='fas fa-book-reader'></i>{' '}
                                        {phase.name}
                                      </span>

                                      <span className='col-md-4 my-3 my-sm-0 color--text'>
                                        <i className='fas fa-calendar-alt'></i>{' '}
                                        {phase.endTime}
                                      </span>

                                      <h3>
                                        {' '}
                                        <span className='d-none d-md-block col-1 text-center text-danger'>
                                          <i className='fa fa-trash'></i>
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
