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
      mentorDisplayName: '',
      menteeDisplayName: '',
      status: '',
      startDate: '',
    };
  }

  getMentorship = async (id) => {
    let application = await axios.get(
      `http://localhost:8080/api/mentorships/${id}`
    );
    this.setState({
      mentorDisplayName: application.data.mentorDisplayName,
      menteeDisplayName: application.data.menteeDisplayName,
      status: application.data.status,
      startDate: application.data.startDate,
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

  componentDidMount() {
    if (!this.props.security.roles[0]?.includes('USER')) {
      this.props.history.push('/');
    } else {
      const { id } = this.props.match.params;
      this.getMentorship(id);
    }
  }

  render() {
    return (
      <div>
        <Header />
        <section
          class='section section-hero gradient-light--lean-right'
          style={{ paddingTop: '0px', paddingBottom: '1.4rem' }}
        >
          <div class='container'>
            <div class='row mt-5'>
              <div class='col-md-8'>
                <Link to='/adminPanel'>
                  <small class='text-uppercase text-muted d-inline-block mb-3'>
                    <i class='fas fa-arrow-left'></i> Back
                  </small>
                </Link>

                <h1 class='mb-4'>Mentorship Details</h1>

                <p>If you want to work with this mentor, start the process.</p>
              </div>
            </div>
          </div>
        </section>

        <section
          class='section section-job-description gradient-light--upright'
          style={{ paddingTop: '2rem' }}
        >
          <div class='container'>
            <div class='row'>
              <div class='col-md-7 order-md-1'>
                <h3 class='h2 mb-4'>Who is the Mentor?</h3>

                <p class='mb-5'>
                  <table class='table'>
                    <thead class='thead-light'>
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

                <h3 class='h2 mb-4'>Mentor's Skills</h3>

                <ul class='mb-5 list-group'>
                  <li class='list-group-item active'>{this.state.mainTopic}</li>
                  <li class='list-group-item'>{this.state.subTopics}</li>
                </ul>

                <h3 class='h2 mb-4'>Mentor's Thoughts</h3>
                <p className='mb-4 font-italic' style={{ fontSize: '20px' }}>
                  {this.state.thoughts}
                </p>

                <div className='container'>
                  <div className='row text-center'>
                    <div className='col'>
                      <button
                        href='#0'
                        style={{ width: '20rem' }}
                        class='btn btn-success'
                        onClick={this.planTheProcess}
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
