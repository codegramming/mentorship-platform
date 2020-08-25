import React, { Component } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import Header from './Layout/Header';

class MyApplication extends Component {
  constructor(props) {
    super(props);
    this.state = {
      mentors: [],
      mentees: [],
    };
  }

  getMentorships = async () => {
    let mentorships = await axios.get(
      'http://localhost:8080/api/mentorships/me'
    );
    this.setState({
      mentors: mentorships.data.mentors,
      mentees: mentorships.data.mentees,
    });
  };

  componentDidMount() {
    if (this.props.security.roles[0] == 'ADMIN') {
      this.props.history.push('/adminPanel');
    } else {
      this.getMentorships();
    }
  }

  render() {
    return (
      <>
        <Header />
        <div className='container'>
          <div className='row'>
            <div className='col-md-12'>
              <h1 className='display-3 text-center mt-4'>My Mentorships</h1>
              <hr />
              <h3 className='display-4 text-center mt-4'>Mentor</h3>

              <div className='job-list__wrapper mb-6'>
                {this.state.mentors.length === 0 ? (
                  <div
                    className='alert alert-info text-center'
                    role='alert'
                    style={{ marginTop: 30 }}
                  >
                    <h3>NO ANY MENTOR RELATION</h3>
                  </div>
                ) : (
                  this.state.mentors.map((mentor, index) => {
                    return (
                      <>
                        <div key={index}>
                          <Link
                            to={`/selectMentor/${mentor.id}`}
                            className='card p-0 mb-3 border-0 shadow-sm shadow--on-hover'
                          >
                            <div className='card-body'>
                              <span className='row justify-content-between align-items-center'>
                                <span className='col-md-3 color--heading'>
                                  <span className='badge badge-circle background--success text-white mr-6'>
                                    SE
                                  </span>{' '}
                                  <i className='fas fa-chalkboard-teacher mr-1'></i>
                                  {mentor.mentee.user.displayName}
                                </span>

                                <span className='col-md-4 my-3 my-sm-0 color--text'>
                                  <i className='fas fa-book-reader'></i>{' '}
                                  {mentor.mentor.mainTopic}(
                                  {mentor.mentor.subTopics})
                                </span>

                                <span className='col-md-2 my-3 my-sm-0 color--text'>
                                  <i className='fad fa-clock'></i>{' '}
                                  {mentor.status}
                                </span>

                                <span className='d-none d-md-block col-1 text-center color--text'>
                                  <small>
                                    <i className='fas fa-chevron-right'></i>
                                  </small>
                                </span>
                              </span>
                            </div>
                          </Link>
                        </div>
                      </>
                    );
                  })
                )}
              </div>
              <hr />
            </div>

            <div className='col-md-12'>
              <h3 className='display-4 text-center mt-4'>Mentees</h3>

              <div className='job-list__wrapper mb-6'>
                {this.state.mentees.length === 0 ? (
                  <div
                    className='alert alert-info text-center'
                    role='alert'
                    style={{ marginTop: 30 }}
                  >
                    <h3>NO ANY MENTEE RELATION</h3>
                  </div>
                ) : (
                  this.state.mentees.map((mentee, index) => {
                    return (
                      <>
                        <div key={index}>
                          <Link
                            to={`/selectMentor/${mentee.id}`}
                            className='card p-0 mb-3 border-0 shadow-sm shadow--on-hover'
                          >
                            <div className='card-body'>
                              <span className='row justify-content-between align-items-center'>
                                <span className='col-md-3 color--heading'>
                                  <span className='badge badge-circle background--success text-white mr-6'>
                                    SE
                                  </span>{' '}
                                  <i className='fas fa-chalkboard-teacher mr-1'></i>
                                  {mentee.mentor.user.displayName}
                                </span>

                                <span className='col-md-4 my-3 my-sm-0 color--text'>
                                  <i className='fas fa-book-reader'></i>{' '}
                                  {mentee.mentor.mainTopic}(
                                  {mentee.mentor.subTopics})
                                </span>

                                <span className='col-md-2 my-3 my-sm-0 color--text'>
                                  <i className='fad fa-clock'></i>{' '}
                                  {mentee.status}
                                </span>

                                <span className='d-none d-md-block col-1 text-center color--text'>
                                  <small>
                                    <i className='fas fa-chevron-right'></i>
                                  </small>
                                </span>
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
          </div>
        </div>
      </>
    );
  }
}

MyApplication.propTypes = {
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps, {})(MyApplication);
