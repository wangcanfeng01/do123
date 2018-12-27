/**
 *  Tale全局函数对象   var tale = new $.tale();
 */
$.extend({
    tale: function () {
    }
});

/**
 * 成功后的弹框
 * @param options
 */
$.tale.prototype.alertOk = function (options) {
    options = options.length ? {text: options} : ( options || {} );
    options.title = options.title || '操作成功';
    options.text = options.text;
    options.showCancelButton = false;
    options.showCloseButton = false;
    options.type = 'success';
    this.alertBox(options);
};

/**
 * 提示成功，并在700毫秒之后刷新页面
 * @param text
 */
$.tale.prototype.alertOkAndReload = function (text) {
    this.alertOk({
        text: text
    });
    setTimeout(function () {
        window.location.reload();
    }, 700);

}

/**
 * 错误提示
 * @param options
 */
$.tale.prototype.alertError = function (options) {
    options = options.length ? {text: options} : ( options || {} );
    options.title = options.title || '错误信息';
    options.text = options.text;
    options.type = 'error';
    this.alertBox(options);
};

/**
 * 警告弹框
 * @param options
 */
$.tale.prototype.alertWarn = function (options) {
    options = options.length ? {text: options} : ( options || {} );
    options.title = options.title || '警告信息';
    options.text = options.text;
    options.timer = 3000;
    options.type = 'warning';
    this.alertBox(options);
};

/**
 * 确认是否执行请求
 * @param options
 */
$.tale.prototype.alertConfirm = function (options) {
    options = options || {};
    options.title = options.title || '确定要删除吗？';
    options.text = options.text;
    options.showCancelButton = true;
    options.type = 'question';
    this.alertBox(options);
};

/**
 * 公共弹框
 * @param options
 */
$.tale.prototype.alertBox = function (options) {
    swal({
        title: options.title,
        text: options.text,
        type: options.type,
        timer: options.timer || 9999,
        showCloseButton: options.showCloseButton,
        showCancelButton: options.showCancelButton,
        showLoaderOnConfirm: options.showLoaderOnConfirm || false,
        confirmButtonColor: options.confirmButtonColor || '#3085d6',
        cancelButtonColor: options.cancelButtonColor || '#d33',
        confirmButtonText: options.confirmButtonText || '确定',
        cancelButtonText: options.cancelButtonText || '取消',
    }).then(function (e) {
        options.then && options.then(e);
    }).catch(swal.noop);
};


/**
 * 显示动画
 */
$.tale.prototype.showLoading = function () {
    if ($('#tale-loading').length == 0) {
        $('body').append('<div id="tale-loading"></div>');
    }
    $('#tale-loading').show();
};

/**
 * 隐藏动画
 */
$.tale.prototype.hideLoading = function () {
    $('#tale-loading') && $('#tale-loading').hide();
};


/**
 * 全局post函数
 *
 * @param options   参数
 */
$.tale.prototype.post = function (options) {
    var self = this;
    $.ajax({
        type: 'POST',
        url: options.url,
        data: options.data || {},
        async: options.async || false,
        dataType: 'json',
        success: function (result) {
            self.hideLoading();
            options.success && options.success(result);
        },
        error: function () {
            //
        }
    });
};

/**
 * 全局put函数
 *
 * @param options   参数
 */
$.tale.prototype.put = function (options) {
    var self = this;
    $.ajax({
        type: 'put',
        url: options.url,
        data: options.data || {},
        async: options.async || false,
        dataType: 'json',
        success: function (result) {
            self.hideLoading();
            options.success && options.success(result);
        },
        error: function () {
            //
        }
    });
};

/**
 * 全局delete函数
 *
 * @param options   参数
 */
$.tale.prototype.delete = function (options) {
    var self = this;
    $.ajax({
        type: 'delete',
        url: options.url,
        success: function (result) {
            self.hideLoading();
            options.success && options.success(result);
        },
        error: function () {
            //
        }
    });
};



