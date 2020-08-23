import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Header from './Layout/Header';
import axios from 'axios';

class MentorDetails extends Component {
  constructor() {
    super();

    this.state = {
      mainTopic: '',
      subTopics: '',
      thoughts: '',
      username: '',
      displayName: '',
      email: '',
    };
  }

  getApplication = async (id) => {
    let application = await axios.get(
      `http://localhost:8080/api/mentors/${id}?status=IN_PROGRESS`
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

  makeAccepted = async () => {
    const { id } = this.props.match.params;
    const status = {
      status: 'ACCEPTED',
    };
    await axios.put(
      `http://localhost:8080/api/mentors/changeStatus/${id}`,
      status
    );
    this.props.history.push('/');
  };

  makeNotAccepted = async () => {
    const { id } = this.props.match.params;
    const status = {
      status: 'NOT_ACCEPTED',
    };
    await axios.put(
      `http://localhost:8080/api/mentors/changeStatus/${id}`,
      status
    );
    this.props.history.push('/');
  };

  componentDidMount() {
    if (!this.props.security.roles[0]?.includes('ADMIN')) {
      this.props.history.push('/');
    } else {
      const { id } = this.props.match.params;
      this.getApplication(id);
    }
  }

  render() {
    //TODO
    //   <ul className='social-icons mb-0'>
    //   <li>
    //     <a href='#0'>
    //       <i className='fab fa-github color--white'></i>
    //     </a>
    //   </li>
    // </ul>
    return (
      <div>
        <Header />
        <section
          className='section section-hero gradient-light--lean-right'
          style={{ paddingTop: '2rem' }}
        >
          <div className='container'>
            <div className='row mt-5'>
              <div className='col-md-8'>
                <Link to='/adminPanel'>
                  <small className='text-uppercase text-muted d-inline-block mb-3'>
                    <i className='fas fa-arrow-left'></i> Back
                  </small>
                </Link>

                <h1 className='mb-4'>Pending Mentor Application</h1>

                <p>Please accept or deny the pending application.</p>
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
                <h3 className='h2 mb-4'>Who is?</h3>

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

                <h3 className='h2 mb-4'>Skills</h3>

                <ul className='mb-5 list-group'>
                  <li className='list-group-item active'>
                    {this.state.mainTopic}
                  </li>
                  <li className='list-group-item'>{this.state.subTopics}</li>
                </ul>

                <h3 className='h2 mb-4'>Thoughts</h3>
                <p className='mb-4 font-italic' style={{ fontSize: '20px' }}>
                  {this.state.thoughts}
                </p>

                <div className='container'>
                  <div className='row text-center'>
                    <div className='col'>
                      <button
                        href='#0'
                        className='btn btn-danger'
                        onClick={this.makeNotAccepted}
                      >
                        DENY
                      </button>
                    </div>
                    <div className='col'>
                      <button
                        href='#0'
                        className='btn btn-success'
                        onClick={this.makeAccepted}
                      >
                        ACCEPT
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

MentorDetails.propTypes = {
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps, {})(MentorDetails);
