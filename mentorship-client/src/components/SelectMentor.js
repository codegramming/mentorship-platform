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
      mainTopic: '',
      subTopics: '',
      thoughts: '',
      username: '',
      displayName: '',
      email: '',
      currentUser: '',
      isMentor: false,
      errors: {},
    };
  }

  getApplication = async (id) => {
    let application = await axios.get(
      `http://localhost:8080/api/mentors/${id}?status=ACCEPTED`
    );
    this.setState({
      mainTopic: application.data.mainTopic,
      subTopics: application.data.subTopics,
      thoughts: application.data.thoughts,
      displayName: application.data.user.displayName,
      username: application.data.user.username,
      email: application.data.user.email,
    });
  };

  startTheProcess = async () => {
    const { id } = this.props.match.params;
    const mentorshipDto = {
      mentorId: id,
    };
    await axios
      .post(`http://localhost:8080/api/mentorships`, mentorshipDto)
      .then((res) => {
        const mentorshipId = res.data.id;
        this.props.history.push(`/mentorshipDetails/${mentorshipId}`);
      })
      .catch((error) => {
        this.setState({
          errors: error.response.data.validationErrors,
        });
      });
  };

  getUser = async () => {
    await axios
      .get('http://localhost:8080/api/auth/user/me')
      .then((res) => {
        this.setState({ currentUser: res.data.username });
      })
      .then(() => {
        if (this.state.currentUser === this.state.username) {
          this.setState({ isMentor: true });
        }
      });
  };

  componentDidMount() {
    if (!this.props.security.roles[0]?.includes('USER')) {
      this.props.history.push('/');
    } else {
      const { id } = this.props.match.params;
      this.getApplication(id).then(() => {
        this.getUser();
      });
    }
  }

  render() {
    return (
      <div>
        <Header />
        <section
          className='section section-hero gradient-light--lean-right'
          style={{ paddingTop: '0px', paddingBottom: '1.4rem' }}
        >
          <div className='container'>
            <div className='row mt-5'>
              <div className='col-md-8'>
                <Link to='/adminPanel'>
                  <small className='text-uppercase text-muted d-inline-block mb-3'>
                    <i className='fas fa-arrow-left'></i> Back
                  </small>
                </Link>

                <h1 className='mb-4'>Start Mentorship</h1>

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
              <div className='col-md-7 order-md-1'>
                {Object.keys(this.state.errors).length !== 0 && (
                  <div className='alert alert-danger' role='alert'>
                    {this.state.errors.existMentorship}
                  </div>
                )}
                <h3 className='h2 mb-4'>Who is the Mentor?</h3>

                <p className='mb-5'>
                  <table className='table'>
                    <thead className='thead-light'>
                      <tr>
                        <th scope='col'>Display Name</th>
                        <th scope='col'>Username</th>
                        <th scope='col'>Email</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>{this.state.displayName}</td>
                        <td>{this.state.username}</td>
                        <td>{this.state.email}</td>
                      </tr>
                    </tbody>
                  </table>
                </p>

                <h3 className='h2 mb-4'>Mentor's Skills</h3>

                <ul className='mb-5 list-group'>
                  <li className='list-group-item active'>
                    {this.state.mainTopic}
                  </li>
                  <li className='list-group-item'>{this.state.subTopics}</li>
                </ul>

                <h3 className='h2 mb-4'>Mentor's Thoughts</h3>
                <p className='mb-4 font-italic' style={{ fontSize: '20px' }}>
                  {this.state.thoughts}
                </p>

                <div className='container'>
                  <div className='row text-center'>
                    <div className='col'>
                      <button
                        href='#0'
                        style={{ width: '20rem' }}
                        className='btn btn-success'
                        onClick={this.startTheProcess}
                        disabled={this.state.isMentor}
                      >
                        SELECT THE MENTOR
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
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
