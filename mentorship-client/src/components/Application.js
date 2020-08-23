import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import Header from './Layout/Header';
import { Multiselect } from 'multiselect-react-dropdown';

import axios from 'axios';

class Application extends Component {
  constructor() {
    super();

    this.state = {
      userMainTopic: '',
      userSubTopics: '',
      thoughts: '',
      pending: true,
      submit: false,
      options: [],
      mainTopics: [],
      topics: [],
      subTopics: [],
      selectedValue: [],
      success: false,
      errors: {},
    };

    this.multiselectRef = React.createRef();
  }

  componentDidMount() {
    if (
      this.props.security.roles[0] == 'ADMIN' &&
      !this.props.security.validToken
    ) {
      this.props.history.push('/adminPanel');
    }
    axios.get(`http://localhost:8080/api/topics`).then((res) => {
      let mainTopics = [];
      for (let i = 0; i < res.data.length; i++) {
        mainTopics.push(res.data[i].title);
      }
      this.setState({ mainTopics, topics: res.data });
    });
  }

  handleTopicChange = (event) => {
    event.preventDefault();
    const userMainTopic = event.target.value;
    this.setState({ userMainTopic, pending: false, errors: {} });
    let subTopicIndex = this.state.mainTopics;
    subTopicIndex = subTopicIndex.indexOf(event.target.value);
    let optionsArray = [];
    this.resetValues();
    this.setState({ userSubTopics: '' });
    this.setState({ subTopics: this.state.topics[subTopicIndex]?.subTitle });

    for (
      let i = 0;
      i < this.state.topics[subTopicIndex]?.subTitle.length;
      i++
    ) {
      let element = {
        name: `${this.state.topics[subTopicIndex].subTitle[i]}`,
        id: i,
      };
      optionsArray.push(element);
    }
    this.setState({ options: optionsArray });
  };

  resetValues() {
    // By calling the belowe method will reset the selected values programatically
    this.multiselectRef.current.resetSelectedValues();
  }

  onSelect = (selectedList, selectedItem) => {
    let selected = this.state.userSubTopics;
    selected += `${selectedItem.name},`;
    this.setState({ userSubTopics: selected, errors: {} });
  };

  onRemove = (selectedList, selectedItem) => {
    this.setState({ userSubTopics: selectedList });
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
    let selected = this.state.userSubTopics;
    selected = selected.substring(0, selected.length - 1);
    const newApplication = {
      thoughts: this.state.thoughts,
      mainTopic: this.state.userMainTopic,
      subTopics: selected,
    };

    await axios
      .post('http://localhost:8080/api/mentors/apply', newApplication)
      .then(() => {
        this.setState({ submit: true });
        setTimeout(
          function () {
            this.props.history.push('/');
          }.bind(this),
          3000
        );
      })
      .catch((error) => {
        this.setState({ errors: error.response.data.validationErrors });
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
              <div className='col-md-8 m-auto'>
                <h5 className='display-4 text-center mt-2'>Become a Mentor</h5>
                <hr />

                {Object.keys(errors).length !== 0 && (
                  <div className='alert alert-danger' role='alert'>
                    Please fill out all fields.
                  </div>
                )}

                {this.state.submit === true && (
                  <div className='alert alert-success' role='alert'>
                    Congratulations, your application has been received. You
                    will be redirected in 3 seconds.
                  </div>
                )}

                <form onSubmit={this.onSubmit}>
                  <label className='required-field' htmlFor='email'>
                    Main Skill
                  </label>
                  <select
                    id='il'
                    onChange={this.handleTopicChange}
                    className='custom-select mb-3'
                  >
                    <option selected value={0}>
                      Please Select Your Main Skill
                    </option>
                    {this.state.mainTopics.map((topic, i) => {
                      return (
                        <option key={i} value={topic}>
                          {topic}
                        </option>
                      );
                    })}
                  </select>

                  {errors.mainTopic && (
                    <div className='form-group invalid-feedback'>
                      {errors.mainTopic}dafdfasd
                    </div>
                  )}
                  <label className='required-field' htmlFor='email'>
                    Side Skills
                  </label>
                  <Multiselect
                    placeholder='Please Select Your Side Skills'
                    style={{
                      inputField: {
                        width: '100%',
                      },
                    }}
                    disable={this.state.pending}
                    options={this.state.options} // Options to display in the dropdown
                    displayValue='name' // Property name to display in the dropdown options
                    selectedValue={this.state.selectedValue}
                    onSelect={this.onSelect}
                    onRemove={this.onRemove}
                    ref={this.multiselectRef}
                  />

                  <div className='form-group mt-3'>
                    <label className='required-field' htmlFor='email'>
                      Thoughts
                    </label>
                    <textarea
                      className={
                        errors.thoughts
                          ? 'form-control form-control-lg is-invalid'
                          : 'form-control form-control-lg'
                      }
                      placeholder='Thoughts'
                      name='thoughts'
                      value={this.state.thoughts}
                      onChange={this.onChange}
                    ></textarea>
                    {errors.thoughts && (
                      <div className='invalid-feedback'>{errors.thoughts}</div>
                    )}
                  </div>
                  <input
                    disabled={this.state.submit}
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

Application.propTypes = {
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
  security: state.security,
});

export default connect(mapStateToProps, {})(Application);
