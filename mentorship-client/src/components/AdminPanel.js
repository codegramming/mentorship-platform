import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import Header from './Layout/Header';
import axios from 'axios';

class AdminPanel extends Component {
  constructor(props) {
    super(props);
    this.state = {
      applications: [],
    };
  }

  componentDidMount() {
    if (!this.props.security.roles[0]?.includes('ADMIN')) {
      this.props.history.push('/');
    } else {
      this.getApplications();
    }
  }

  getApplications = async () => {
    let applications = await axios.get(`http://localhost:8080/api/mentors`);
    this.setState({
      applications: applications.data,
    });
  };

  render() {
    return (
      <>
        <Header />
        <div className='projects'>
          <div className='container'>
            <div className='row'>
              <div className='col-md-12'>
                <h1 className='display-4 text-center mt-4'>
                  Pending Mentor Applications
                </h1>
                <hr />
                <div class='job-list__wrapper mb-6'>
                  {this.state.applications.length === 0 ? (
                    <div
                      class='alert alert-info text-center'
                      role='alert'
                      style={{ marginTop: 30 }}
                    >
                      <h3>NO NEW APPLICATIONS</h3>
                    </div>
                  ) : (
                    this.state.applications.map((application, index) => {
                      return (
                        <>
                          <div key={index}>
                            <Link
                              to={`/mentorDetails/${application.id}`}
                              class='card p-0 mb-3 border-0 shadow-sm shadow--on-hover'
                            >
                              <div class='card-body'>
                                <span class='row justify-content-between align-items-center'>
                                  <span class='col-md-5 color--heading'>
                                    <span class='badge badge-circle background--success text-white mr-6'>
                                      SE
                                    </span>{' '}
                                    <i class='fas fa-chalkboard-teacher mr-1'></i>
                                    {application.user.displayName}
                                  </span>

                                  <span class='col-5 col-md-3 my-3 my-sm-0 color--text'>
                                    <i class='fas fa-book-reader'></i>{' '}
                                    {application.mainTopic}
                                  </span>

                                  <span class='col-7 col-md-3 my-3 my-sm-0 color--text'>
                                    <i class='fad fa-ellipsis-h-alt'></i>{' '}
                                    {application.subTopics}
                                  </span>

                                  <span class='d-none d-md-block col-1 text-center color--text'>
                                    <small>
                                      <i class='fas fa-chevron-right'></i>
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
        </div>
      </>
    );
  }
}

AdminPanel.propTypes = {
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps)(AdminPanel);
