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
      newPhase: {},
      name: '',
      endDate: '',
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
    };
    this.setState({ phases: [...this.state.phases, newPhase] });
    /*const { id } = this.props.match.params;
    await axios
      .post(`http://localhost:8080/api/mentorships/addPhase/${id}`, newPhase)
      .then(() => {
        this.getPhases(id);
      });*/
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
                      <div className='invalid-feedback'>{errors.endDate}</div>
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
