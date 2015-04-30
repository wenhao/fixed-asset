'use strict';

import React from 'react'
import {
  RaisedButton,
  FontIcon,
  Paper
} from 'material-ui'
import {
  State
} from 'react-router'

import assetApi from '../services/asset'

var Asset = React.createClass({

  mixins: [State],

  getInitialState() {
    return {
      title: 'User Assets',
      assets: []
    }
  },
  componentDidMount() {
    this._getAssets()
  },
  render() {
    return (
      <Paper zDepth={1}>
        <ul>
          {this._renderAssets()}
        </ul>
        <RaisedButton secondary={true} onClick={this._getAssets}>
          <FontIcon className="muidocs-icon-custom-github example-button-icon"/>
          <span className="mui-raised-button-label example-icon-button-label">Get Assets</span>
        </RaisedButton>
      </Paper>
    );
  },
  _getAssets(userData) {
     assetApi.getUserAssets(userData).then(this.onAssetsLoad, this.onAssetsLoadFailed)
  },
  onAssetsLoad(result) {
  console.log(result);
    this.setState({
      assets: result.body
    })
  },
  onAssetsLoadFailed(error) {
    //
  },
  _renderAssets() {
    return this.state.assets.map(function(result) {
      return (
        <li className="asset__item"><ul>
          <li className="asset__attribute">Name: {result.assetName}</li>
          <li className="asset__attribute">Number: {result.assetNumber}</li>
          <li className="asset__attribute">Assigned Date: {result.assignDate}</li>
          <li className="asset__attribute">Type: {result.assetType}</li>
        </ul></li>
      )
    })
  }
})

export default Asset