import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Multiselect } from 'multiselect-react-dropdown';
import Header from './Layout/Header';
import axios from 'axios';

class SearchMentor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      userMainTopic: '',
      mentors: [],
      topics: [],
      mainTopics: [],
      subTopics: [],
      searchText: '',
    };

    this.multiselectRef = React.createRef();
  }

  componentDidMount() {
    if (!this.props.security.roles[0]?.includes('USER')) {
      this.props.history.push('/');
    } else {
      this.getMentors();
      this.getTopics();
    }
  }

  onSearchChange = (e) => {
    this.setState({ searchText: e.target.value });
  };

  onSearchSubmit = async (e) => {
    e.preventDefault();
    let searchText = this.state.searchText;
    let mentors = await axios.get(
      `http://localhost:8080/api/mentors/search?thoughts=${searchText}`
    );
    this.setState({ mentors: mentors.data });
  };

  getMentors = async () => {
    let mentors = await axios.get(`http://localhost:8080/api/mentors/accepted`);
    this.setState({
      mentors: mentors.data,
    });
  };

  getTopics = async () => {
    axios.get(`http://localhost:8080/api/topics`).then((res) => {
      let mainTopics = [];
      for (let i = 0; i < res.data.length; i++) {
        mainTopics.push(res.data[i].title);
      }
      this.setState({ mainTopics, topics: res.data });
    });
  };

  handleTopicChange = async (event) => {
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
    this.getMain(userMainTopic);
  };

  getMain = async (userMainTopic) => {
    let mentorsWithMain = await axios.get(
      `http://localhost:8080/api/mentors/search?main=${userMainTopic}`
    );
    this.setState({ mentors: mentorsWithMain.data });
  };

  resetValues() {
    // By calling the belowe method will reset the selected values programatically
    this.multiselectRef.current.resetSelectedValues();
  }

  onSelect = (selectedList, selectedItem) => {
    let selected = this.state.userSubTopics;
    selected += `${selectedItem.name},`;
    this.setState({ userSubTopics: selected, errors: {} }, () => {
      this.getSubs(this.state.userSubTopics);
    });
  };

  onRemove = (selectedList, selectedItem) => {
    let myList = '';
    for (let i = 0; i < selectedList.length; i++) {
      myList += `${selectedList[i].name},`;
    }
    this.setState({ userSubTopics: myList }, () => {
      if (!this.state.userSubTopics.length == 0) {
        this.getSubs(this.state.userSubTopics);
      } else {
        this.getMain(this.state.userMainTopic);
      }
    });
  };

  getSubs = async (subs) => {
    let mentorsWithMain = await axios.get(
      `http://localhost:8080/api/mentors/search?subs=${subs}`
    );
    this.setState({ mentors: mentorsWithMain.data });
  };

  render() {
    return (
      <>
        <Header />
        <div className='projects'>
          <div className='container'>
            <div className='row'>
              <div className='col-md-12'>
                <h1 className='display-4 text-center mt-4'>Mentors</h1>
                <hr />
                <form class='filter-form mt-5 mb-4'>
                  <div class='row'>
                    <div class='col-md-6 mb-3'>
                      <div class='form-group'>
                        <label for='jobPosition'>Main Skill :</label>
                        <span>
                          <select
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
                        </span>
                      </div>
                    </div>
                    <div class='col-md-6 mb-3'>
                      <div class='form-group'>
                        <span class='multiselect-native-select'>
                          <label>Side Skills</label>
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
                            onSelect={this.onSelect}
                            onRemove={this.onRemove}
                            ref={this.multiselectRef}
                          />
                        </span>
                      </div>
                    </div>
                  </div>
                  <div className='row'>
                    <div className='col-md-6'>
                      <div className='form-group'>
                        <input
                          class='form-control'
                          type='search'
                          placeholder='Search'
                          aria-label='Search'
                          onChange={this.onSearchChange}
                        />
                      </div>
                    </div>
                    <div className='col-md-0'>
                      <div className='form-group'>
                        <button
                          class='btn btn-success'
                          onClick={this.onSearchSubmit}
                        >
                          <i class='fas fa-search'></i>
                        </button>
                      </div>
                    </div>
                  </div>
                </form>

                <div class='job-list__wrapper mb-6'>
                  {this.state.mentors.length === 0 ? (
                    <div
                      class='alert alert-info text-center'
                      role='alert'
                      style={{ marginTop: 30 }}
                    >
                      <h3>NO ANY MENTOR</h3>
                    </div>
                  ) : (
                    this.state.mentors.map((application, index) => {
                      return (
                        <>
                          <div key={index}>
                            <Link
                              to={`/selectMentor/${application.id}`}
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

SearchMentor.propTypes = {
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps)(SearchMentor);
