import React, { Component } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';
import { Link, useHistory } from 'react-router-dom';
import { connect } from 'react-redux';
import Header from './Layout/Header';

class MyApplication extends Component {
  constructor(props) {
    super(props);
    this.state = {
      mentorships: [],
    };
  }

  getMentorships = async () => {
    let mentorships = await axios.get(
      'http://localhost:8080/api/mentorships/me'
    );
    this.setState({
      mentorships: mentorships.data,
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
            <div className='col-md-5'>
              <h1 className='display-3 text-center mt-4'>Mentors</h1>
              <div class='post card p-0 border-0 shadow-sm'>
                <Link>
                  <div class='card-body'>
                    <div class='card-title d-inline mb-4'>
                      <i class='fas fa-user-edit text-primary'></i>
                      <h3 className='d-inline'> Mentor: </h3>
                      <h3
                        className='d-inline'
                        style={{ fontWeight: '300', fontStyle: 'italic' }}
                      >
                        Tayfur
                      </h3>
                    </div>
                    <div class='card-title mt-3'>
                      <i class='fas fa-book-open text-info'></i>
                      <h3 className='d-inline'> Topic: </h3>
                      <h3
                        className='d-inline'
                        style={{ fontWeight: '300', fontStyle: 'italic' }}
                      >
                        Backend
                      </h3>
                    </div>
                    <div class='card-title mt-3'>
                      <i class='far fa-clock text-warning'></i>
                      <h3 className='d-inline'> Status: </h3>
                      <h3
                        className='d-inline'
                        style={{ fontWeight: '300', fontStyle: 'italic' }}
                      >
                        Completed
                      </h3>
                    </div>
                  </div>
                </Link>
              </div>
              <div class='post card p-0 border-0 shadow-sm mt-3'>
                <Link>
                  <div class='card-body'>
                    <div class='card-title d-inline mb-4'>
                      <i class='fas fa-user-edit text-primary'></i>
                      <h3 className='d-inline'> Mentor: </h3>
                      <h3
                        className='d-inline'
                        style={{ fontWeight: '300', fontStyle: 'italic' }}
                      >
                        Tayfur
                      </h3>
                    </div>
                    <div class='card-title mt-3'>
                      <i class='fas fa-book-open text-info'></i>
                      <h3 className='d-inline'> Topic: </h3>
                      <h3
                        className='d-inline'
                        style={{ fontWeight: '300', fontStyle: 'italic' }}
                      >
                        Backend
                      </h3>
                    </div>
                    <div class='card-title mt-3'>
                      <i class='far fa-clock text-warning'></i>
                      <h3 className='d-inline'> Status: </h3>
                      <h3
                        className='d-inline'
                        style={{ fontWeight: '300', fontStyle: 'italic' }}
                      >
                        Completed
                      </h3>
                    </div>
                  </div>
                </Link>
              </div>
            </div>
            <div className='col-md-2'></div>
            <div className='col-md-5'>
              <h1 className='display-3 text-center mt-4'>Mentees</h1>
              <div class='post card p-0 border-0 shadow-sm'>
                <Link>
                  <div class='card-body'>
                    <div class='card-title d-inline mb-4'>
                      <i class='fas fa-user-edit text-primary'></i>
                      <h3 className='d-inline'> Mentor: </h3>
                      <h3
                        className='d-inline'
                        style={{ fontWeight: '300', fontStyle: 'italic' }}
                      >
                        Tayfur
                      </h3>
                    </div>
                    <div class='card-title mt-3'>
                      <i class='fas fa-book-open text-info'></i>
                      <h3 className='d-inline'> Topic: </h3>
                      <h3
                        className='d-inline'
                        style={{ fontWeight: '300', fontStyle: 'italic' }}
                      >
                        Backend
                      </h3>
                    </div>
                    <div class='card-title mt-3'>
                      <i class='far fa-clock text-warning'></i>
                      <h3 className='d-inline'> Status: </h3>
                      <h3
                        className='d-inline'
                        style={{ fontWeight: '300', fontStyle: 'italic' }}
                      >
                        Completed
                      </h3>
                    </div>
                  </div>
                </Link>
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
