import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Header from './Layout/Header';
import axios from 'axios';

class SelectMentor extends Component {
  constructor() {
    super();

    this.state = {
      currentUser: '',
      mentorDisplayName: '',
      menteeDisplayName: '',
      mentorUsername: '',
      menteeUsername: '',
      status: '',
      startDate: '',
      numberOfPhases: 0,
      hasPhase: false,
      mentorThoughts: '',
      menteeThoughts: '',
      currentPhase: 0,
      isMentor: false,
      isMentee: false,
      phases: [],
      errors: {},
    };
  }

  getMentorship = async (id) => {
    let application = await axios.get(
      `http://localhost:8080/api/mentorships/${id}`
    );
    this.setState({
      mentorDisplayName: application.data.mentor.user.displayName,
      mentorUsername: application.data.mentor.user.username,
      menteeDisplayName: application.data.mentee.user.displayName,
      menteeUsername: application.data.mentee.user.username,
      status: application.data.status,
      startDate: application.data.startDate,
      numberOfPhases: application.data.numberOfPhases,
      hasPhase: application.data.hasPhase,
      mentorThoughts: application.data.mentorThoughts,
      menteeThoughts: application.data.menteeThoughts,
      currentPhase: application.data.currentPhase,
      phases: application.data.phases,
    });
  };

  planTheProcess = async () => {
    const { id } = this.props.match.params;
    const mentorshipDto = {
      mentorId: id,
    };
    await axios.post(`http://localhost:8080/api/mentorships`, mentorshipDto);
    this.props.history.push('/');
  };

  startMentorship = async () => {
    const { id } = this.props.match.params;
    await axios
      .put(`http://localhost:8080/api/mentorships/${id}`)
      .then((res) => {
        this.setState({
          currentPhase: res.data.currentPhase,
          phases: res.data.phases,
          status: res.data.status,
        });
      });
  };

  getCurrentUser = async () => {
    await axios
      .get('http://localhost:8080/api/auth/user/me')
      .then((res) => {
        this.setState({ currentUser: res.data.username });
      })
      .then(() => {
        if (this.state.mentorUsername === this.state.currentUser) {
          this.setState({ isMentor: true });
        }
        if (this.state.menteeUsername === this.state.currentUser) {
          this.setState({ isMentee: true });
        }
      });
  };

  getPendingPhaseButton = (phase) => {
    let { isMentee, isMentor } = this.state;
    if (isMentee && phase.assessmentOfMentee !== null) {
      return (
        <Link className='col-md-2 mr-2 btn btn-outline-success btn-sm'>
          Pending Evaluation
        </Link>
      );
    } else if (isMentee && phase.assessmentOfMentee === null) {
      return (
        <Link
          to={`/completePhase/${phase.id}`}
          className='col-md-2 mr-2 btn btn-outline-success btn-sm'
        >
          Pending Evaluation
        </Link>
      );
    }

    if (isMentor && phase.assessmentOfMentor !== null) {
      return (
        <Link className='col-md-2 mr-2 btn btn-outline-success btn-sm'>
          Pending Evaluation
        </Link>
      );
    } else if (isMentor && phase.assessmentOfMentor === null) {
      return (
        <Link
          to={`/completePhase/${phase.id}`}
          className='col-md-2 mr-2 btn btn-outline-success btn-sm'
          mentorshipId={this.props.match.params.id}
        >
          Pending Evaluation
        </Link>
      );
    }
  };

  componentDidMount() {
    if (!this.props.security.roles[0]?.includes('USER')) {
      this.props.history.push('/');
    } else {
      const { id } = this.props.match.params;
      this.getMentorship(id)
        .then(() => {
          this.getCurrentUser();
        })
        .catch((err) => {
          this.setState({ errors: err.response.data.message });
        });
    }
  }

  render() {
    return (
      <>
        <Header />
        <section
          className='section section-hero gradient-light--lean-right'
          style={{ paddingTop: '0px', paddingBottom: '1.4rem' }}
        >
          <div className='container'>
            <div className='row mt-5'>
              <div className='col-md-8'>
                <Link to='/'>
                  <small className='text-uppercase text-muted d-inline-block mb-3'>
                    <i className='fas fa-arrow-left'></i> Back
                  </small>
                </Link>

                <h1 className='mb-4'>Mentorship Details</h1>

                <p>If you want to work with this mentor, start the process.</p>
              </div>
            </div>
          </div>
        </section>

        <section
          className='section section-job-description gradient-light--upright'
          style={{ paddingTop: '2rem' }}
        >
          <div className='container'>
            <div className='row'>
              <div className='col-md-6'>
                {Object.keys(this.state.errors).length !== 0 && (
                  <div className='alert alert-danger' role='alert'>
                    {this.state.errors === 'No message available'
                      ? 'Mentorship not found'
                      : this.state.errors}
                  </div>
                )}

                <table className='table table-hover'>
                  <thead>
                    <tr></tr>
                  </thead>
                  <tbody>
                    <tr>
                      <th scope='row'>Mentor Name:</th>
                      <td>{this.state.mentorDisplayName}</td>
                    </tr>
                    <tr>
                      <th scope='row'>Mentee Name:</th>
                      <td>{this.state.menteeDisplayName}</td>
                    </tr>
                    <tr>
                      <th scope='row'>Start Date:</th>
                      <td>{this.state.startDate}</td>
                    </tr>
                    <tr>
                      <th scope='row'>Status:</th>
                      <td>{this.state.status} </td>
                    </tr>
                    {this.state.status !== 'Completed' ? (
                      <tr>
                        <th scope='row'>Active Phase:</th>
                        <td>{this.state.currentPhase} </td>
                      </tr>
                    ) : (
                      ''
                    )}
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <div className='container'>
            <div className='row'>
              <div className='col-md-10'>
                <div className='job-list__wrapper mb-4'>
                  <h3 className='h2 mb-4'>Phase List</h3>
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
                          <div className='row' key={index}>
                            <div className='col-md-12'>
                              <div key={index}>
                                <div className='card p-0 mb-3 border-0 shadow-sm shadow--on-hover'>
                                  <div className='card-body'>
                                    <span className='row justify-content-between align-items-center'>
                                      <span className='col-md-1 color--heading'>
                                        <span className='badge badge-circle background--success text-white'>
                                          {phase.phaseId}
                                        </span>{' '}
                                      </span>
                                      <span className='col-md-5  my-sm-0 color--text'>
                                        <i className='fas fa-book-reader'></i>{' '}
                                        {phase.name}
                                      </span>
                                      <span className='col-md-3 my-3 my-sm-0 color--text'>
                                        <i className='fas fa-calendar-alt'></i>{' '}
                                        {phase.endTime}
                                      </span>
                                      {phase.status === 'NOT_ACTIVE' && (
                                        <div className='col-md-2 mr-2 text-danger'>
                                          <i className='far fa-clock'></i> Not
                                          Active
                                        </div>
                                      )}
                                      {phase.status === 'ACTIVE' && (
                                        <Link
                                          to={`/completePhase/${phase.id}`}
                                          className='col-md-2 mr-2 btn btn-outline-success btn-sm'
                                        >
                                          Complete
                                        </Link>
                                      )}
                                      {phase.status === 'PENDING' &&
                                        this.getPendingPhaseButton(phase)}

                                      {phase.status === 'COMPLETED' && (
                                        <div className='col-md-2 mr-2 text-success'>
                                          <i className='far fa-check'></i>{' '}
                                          Completed
                                        </div>
                                      )}
                                    </span>
                                  </div>
                                </div>
                                <div className='row'>
                                  {phase.assessmentOfMentor && (
                                    <div className='col-md-5'>
                                      {' '}
                                      <div className='swiper-slide testimony__card p-3'>
                                        <blockquote className='blockquote shadow'>
                                          <footer className='blockquote-footer d-flex align-items-center'>
                                            <div className='testimony__info d-inline-block'>
                                              <span className='info-name d-block'>
                                                Mentor
                                              </span>
                                              <span className='info-company d-block'>
                                                {this.state.mentorDisplayName}
                                              </span>
                                            </div>
                                          </footer>
                                          <p className='ml-3'>
                                            {phase.assessmentOfMentor}
                                          </p>
                                          <span className='rating text-warning d-block mt-1'>
                                            {[
                                              ...Array(phase.ratingOfMentor),
                                            ].map((x, i) => (
                                              <i className='fas fa-star'></i>
                                            ))}{' '}
                                          </span>
                                        </blockquote>
                                      </div>
                                    </div>
                                  )}
                                  {phase.assessmentOfMentee && (
                                    <div className='col-md-5'>
                                      {' '}
                                      <div className='swiper-slide testimony__card p-3'>
                                        <blockquote className='blockquote shadow'>
                                          <footer className='blockquote-footer d-flex align-items-center'>
                                            <div className='testimony__info d-inline-block'>
                                              <span className='info-name d-block'>
                                                Mentee
                                              </span>
                                              <span className='info-company d-block'>
                                                {this.state.menteeDisplayName}
                                              </span>
                                            </div>
                                          </footer>
                                          <p className='ml-3'>
                                            {phase.assessmentOfMentee}
                                          </p>
                                          <span className='rating text-warning d-block mt-1'>
                                            {[
                                              ...Array(phase.ratingOfMentee),
                                            ].map((x, i) => (
                                              <i className='fas fa-star'></i>
                                            ))}{' '}
                                          </span>
                                        </blockquote>
                                      </div>
                                    </div>
                                  )}
                                </div>
                              </div>
                            </div>
                          </div>
                        </>
                      );
                    })
                  )}
                </div>
                <div className='container'>
                  <div className='row text-center'>
                    <div className='col'>
                      {this.state.hasPhase === false && (
                        <Link
                          to={`/planMentorship/${this.props.match.params.id}`}
                        >
                          <button
                            href='#0'
                            style={{ width: '20rem' }}
                            className='btn btn-success'
                          >
                            PLAN THE MENTORSHIP
                          </button>
                        </Link>
                      )}

                      {this.state.hasPhase === true &&
                        this.state.currentPhase == 0 && (
                          <button
                            href='#0'
                            style={{ width: '20rem' }}
                            className='btn btn-success'
                            onClick={this.startMentorship}
                          >
                            START THE MENTORSHIP
                          </button>
                        )}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </>
    );
  }
}

SelectMentor.propTypes = {
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps, {})(SelectMentor);
