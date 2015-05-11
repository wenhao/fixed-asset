'use strict';

import request from 'superagent';
import apisBuilder from '../../utils/apisBuilder';

let mock;

const endpoint = 'http://localhost:8080';

const tempAssetApis = {
    getUserAssets: {
        method: 'get',
        url: '/users/uid/assets'
    }
};

// mock the http request if not production
if (process.env.NODE_ENV == 'development' || process.env.NODE_ENV == 'test') {
    mock = require('./mock-asset')
}

/**
 * build apis from the config or add mock apis
 */
export default apisBuilder(tempAssetApis, endpoint, mock);