import React from 'react'
import {
  RaisedButton,
  FontIcon,
  Paper,
  TextField
} from 'material-ui'
import {
  State
} from 'react-router'

import userApi from '../services/user'

var Home = React.createClass({

  mixins: [State],

  getInitialState() {
    return {
      title: ''
    }
  },

  render() {
    return (
      <Paper zDepth={1} className="page-auth">
        <div className="login-group">
          <RaisedButton className="login-button" secondary={true} onClick={this._login}>
            <FontIcon className="muidocs-icon-custom-github example-button-icon"/>
            <span className="mui-raised-button-label example-icon-button-label">Log in</span>
          </RaisedButton>
        </div>
      </Paper>
    )
  },

  _login(){
    this.context.router.transitionTo('login');
  }

});

export default Home
