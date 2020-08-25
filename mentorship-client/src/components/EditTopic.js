import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Header from './Layout/Header';

import axios from 'axios';
let phaseId = 0;

class EditTopic extends Component {
  constructor() {
    super();

    this.state = {
      topics: [],
      newPhase: {},
      subTopics: [],
      mainTopic: '',
      selectedMain: '',
      selectedSide: '',
      AddedSubsMain: '',
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
    this.getTopics(id);
  }

  getTopics = async (id) => {
    await axios.get(`http://localhost:8080/api/topics`).then((res) => {
      this.setState({
        topics: res.data,
        AddedSubsMain: res.data[0].title,
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

    if (this.state.mainTopic !== '') {
      const topic = {
        title: this.state.mainTopic,
      };
      await axios
        .post(`http://localhost:8080/api/topics`, topic)
        .then(() => {
          this.getTopics();
          this.setState({ mainTopic: '' });
        })
        .catch((errors) => {
          this.setState({ errors: errors.response.data.validationErrors });
        });
    } else {
      const validationErrors = {
        mainTopic: 'Main Topic cannot be blank.',
      };
      this.setState({ errors: validationErrors });
    }
  };

  onSubmitSub = async (e) => {
    e.preventDefault();

    if (this.state.subTopic !== '') {
      const subTopic = {
        subTitle: this.state.subTopic,
      };
      await axios
        .post(
          `http://localhost:8080/api/topics/${this.state.AddedSubsMain}`,
          subTopic
        )
        .then(() => {
          this.getTopics();
          this.setState({
            subTopic: '',
          });
        })
        .catch((errors) => {
          this.setState({ errors: errors.response.data.validationErrors });
        });
    } else {
      const validationErrors = {
        subTopic: 'Sub Topic cannot be blank.',
      };
      this.setState({ errors: validationErrors });
    }
  };

  handleTopicChange = async (event) => {
    event.preventDefault();
    const selectedMain = event.target.value;
    this.setState({ selectedMain });
    for (let i = 0; i < this.state.topics.length; i++) {
      if (selectedMain == this.state.topics[i].title) {
        this.setState({
          subTopics: this.state.topics[i].subTitle,
        });
      }
    }
  };

  handleMainForSub = async (event) => {
    event.preventDefault();
    const AddedSubsMain = event.target.value;
    this.setState({ AddedSubsMain });
  };

  handleSideChange = async (event) => {
    event.preventDefault();
    const selectedSide = event.target.value;
    this.setState({ selectedSide });
  };

  handleDelete = async (type, item) => {
    await axios
      .delete(`http://localhost:8080/api/topics/remove?${type}=${item}`)
      .then(() => {
        this.setState({ selectedMain: '', selectedSide: '' });
        this.getTopics();
      })
      .catch((errors) => {
        this.setState({ errors: errors.response.data.validationErrors });
      });
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
                <h1 className='display-4 text-center mt-4'>Update Topic</h1>
                <hr />

                {Object.keys(errors).length !== 0 && (
                  <div className='alert alert-danger' role='alert'>
                    {errors.mainTopic}
                    {errors.subTopic} {'  '}Please update your options and try
                    again.
                  </div>
                )}

                {this.state.submit === true && (
                  <div className='alert alert-success' role='alert'>
                    Congratulations, your application has been received. You
                    will be redirected in 3 seconds.
                  </div>
                )}
                <div className='row'>
                  <div className='col-md-6'>
                    <form onSubmit={this.onSubmit}>
                      <div className='form-group mt-3'>
                        <h2 className='text-center'>Add Main Topic</h2>
                        <label className='required-field' htmlFor='email'>
                          Topic Name
                        </label>
                        <input
                          type='text'
                          className={
                            errors.mainTopic
                              ? 'form-control form-control-lg is-invalid'
                              : 'form-control form-control-lg'
                          }
                          placeholder='Main Topic'
                          name='mainTopic'
                          value={this.state.mainTopic}
                          onChange={this.onChange}
                        ></input>
                        {errors.mainTopic && (
                          <div className='invalid-feedback'>
                            {errors.mainTopic}
                          </div>
                        )}
                      </div>

                      <input
                        type='submit'
                        className='btn btn-primary btn-block mt-4'
                      />
                    </form>
                  </div>
                  <div className='col-md-6'>
                    <form onSubmit={this.onSubmitSub}>
                      <div className='form-group mt-3'>
                        <h2 className='text-center'>Add Sub Topic</h2>
                        <form className='filter-form mb-4'>
                          <>
                            <div className='form-group'>
                              <label htmlFor='jobPosition'>Main Skill :</label>
                              <span>
                                <select
                                  onChange={this.handleMainForSub}
                                  className='custom-select mb-3'
                                >
                                  {this.state.topics.map((topic, i) => {
                                    return (
                                      <option key={i} value={topic.title}>
                                        {topic.title}
                                      </option>
                                    );
                                  })}
                                </select>
                              </span>
                            </div>
                          </>
                        </form>
                        <label className='required-field' htmlFor='email'>
                          Sub Name
                        </label>

                        <input
                          type='text'
                          className={
                            errors.subTopic
                              ? 'form-control form-control-lg is-invalid'
                              : 'form-control form-control-lg'
                          }
                          placeholder='Sub Topic'
                          name='subTopic'
                          value={this.state.subTopic}
                          onChange={this.onChange}
                        ></input>
                        {errors.subTopic && (
                          <div className='invalid-feedback'>
                            {errors.subTopic}
                          </div>
                        )}
                      </div>

                      <input
                        type='submit'
                        className='btn btn-primary btn-block mt-4'
                      />
                    </form>
                  </div>
                </div>
                <h1 className='display-4 text-center mt-4'>
                  Show/Remove Topics
                </h1>
                <hr />

                {Object.keys(errors).length !== 0 && (
                  <div className='alert alert-danger' role='alert'>
                    {errors.removeMainTopic}
                    {errors.removeSubTopic}
                    {'  '}Please update your options and try
                  </div>
                )}
                <div
                  className='row'
                  style={{ marginTop: '3rem', marginBottom: '3rem' }}
                >
                  <div className='col-md-6'>
                    <div className='col-md-12 m-auto'></div>
                    <form className='filter-form mb-4'>
                      <>
                        <div className='form-group'>
                          <label htmlFor='jobPosition'>Main Skill :</label>
                          <span>
                            <select
                              onChange={this.handleTopicChange}
                              className='custom-select mb-3'
                            >
                              <option defaultValue={0} value=''>
                                Please Select Main Skill
                              </option>
                              {this.state.topics.map((topic, i) => {
                                return (
                                  <option key={i} value={topic.title}>
                                    {topic.title}
                                  </option>
                                );
                              })}
                            </select>
                          </span>
                        </div>
                      </>
                    </form>
                  </div>
                  <div className='col-md-6'>
                    <div className='form-group'>
                      <label htmlFor='jobPosition'>Side Skills :</label>
                      <span>
                        <select
                          onChange={this.handleSideChange}
                          className='custom-select mb-3'
                        >
                          <option defaultValue={''} value=''>
                            Please Select Side Skill
                          </option>
                          {this.state.subTopics?.map((topic, i) => {
                            return (
                              <option key={i} value={topic.title}>
                                {topic}
                              </option>
                            );
                          })}
                        </select>
                      </span>
                    </div>
                  </div>
                </div>
                {this.state.selectedSide !== '' &&
                this.state.selectedSide !== undefined ? (
                  <div className='col-md-9'>
                    <div className='job-list__wrapper mb-6'>
                      <div>
                        <Link
                          onClick={() =>
                            this.handleDelete('sub', this.state.selectedSide)
                          }
                          className='card p-0 mb-3 border-0 shadow-sm shadow--on-hover'
                        >
                          <div className='card-body'>
                            <span className='row justify-content-between align-items-center'>
                              <span className='col-md-8 color--heading'>
                                <span className='badge badge-circle background--warning text-white mr-6'>
                                  S
                                </span>{' '}
                                <i className='fas fa-chalkboard-teacher mr-1'></i>
                                {this.state.selectedSide}
                              </span>

                              <h4>
                                <span className='d-none d-md-block col-1 text-center text-danger'>
                                  <i className='fas fa-trash'></i>
                                </span>
                              </h4>
                            </span>
                          </div>
                        </Link>
                      </div>
                    </div>
                  </div>
                ) : (
                  ''
                )}
                {this.state.selectedMain !== '' ? (
                  <div className='col-md-9'>
                    <div className='job-list__wrapper mb-6'>
                      <div>
                        <Link
                          onClick={() =>
                            this.handleDelete('main', this.state.selectedMain)
                          }
                          className='card p-0 mb-3 border-0 shadow-sm shadow--on-hover'
                        >
                          <div className='card-body'>
                            <span className='row justify-content-between align-items-center'>
                              <span className='col-md-8 color--heading'>
                                <span className='badge badge-circle background--success text-white mr-6'>
                                  T
                                </span>{' '}
                                <i className='fas fa-chalkboard-teacher mr-1'></i>
                                {this.state.selectedMain}
                              </span>

                              <h4>
                                <span className='d-none d-md-block col-1 text-center text-danger'>
                                  <i className='fas fa-trash'></i>
                                </span>
                              </h4>
                            </span>
                          </div>
                        </Link>
                      </div>
                    </div>
                  </div>
                ) : (
                  ''
                )}
              </div>
            </div>
          </div>
        </div>
      </>
    );
  }
}

EditTopic.propTypes = {
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
  security: state.security,
});

export default connect(mapStateToProps, {})(EditTopic);
