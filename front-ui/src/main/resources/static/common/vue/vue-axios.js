/**
 * http请求的get方法
 * @param {*} url url中包含了参数了
 * @param {*} callback 回调函数
 */
function get(url, callback) {
    if (typeof callback != "function") {
        console.log('回调函数异常');
        return;
    }
    var data;
    axios.get(url).then(function (response) {
        data = response.data;
        //返回异步结果
        if (callback) {
            callback(data);
        }
    }).catch(function (error) {
        console.log(error);
    });
}


/**
 * http的post方法
 * @param {*} url url中包含了参数了
 * @param {*} params 请求体
 * @param {*} callback 回调函数
 */
function post(url, params, callback) {
    if (typeof callback != "function") {
        console.log('回调函数异常');
        return;
    }
    axios.defaults.headers['Content-Type'] = 'text/plain';
    var req = JSON.stringify(params);
    axios.post(url, req).then(function (response) {
        data = response.data;
        //返回异步结果
        if (callback) {
            callback(data);
        }
    }).catch(function (error) {
        console.log(error);
    });
}


/**
 * http的删除请求
 * @param {*} url 请求地址，已经是包含路径中的参数了
 * @param {*} callback 回调函数
 */
function deletion(url, callback) {
    if (typeof callback != "function") {
        console.log('回调函数异常');
        return;
    }
    axios.delete(url).then(function (response) {
        data = response.data;
        //返回异步结果
        if (callback) {
            callback(data);
        }
    }).catch(function (error) {
        console.log(error);
    });
}

/**
 * http的修改请求
 * @param {*} url 请求地址，已经是包含路径中的参数了
 * @param {*} params 请求体
 * @param {*} callback 回调函数
 */
function put(url, params, callback) {
    if (typeof callback != "function") {
        console.log('回调函数异常');
        return;
    }
    axios.put(url, params).then(function (response) {
        data = response.data;
        //返回异步结果
        if (callback) {
            callback(data);
        }
    }).catch(function (error) {
        console.log(error);
    });
}

