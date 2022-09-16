function doRequest(url, data, _callback, _error_callback) {
    $.ajax({url:url,
        type:"POST",
        dataType: "json",
        contentType : 'application/json',
        async: true,
        data: JSON.stringify(data),
        success:function(result){
            if (result.success) {
                if (_callback) {
                    _callback(result);
                }
            } else {
                if (_error_callback) {
                    _error_callback(result);
                }
                default_alert();
            }
        },
        error: function(result) {
            if (_error_callback) {
                _error_callback(result);
            }
            default_alert();
        }});
}

function uploadFile(url, data, _callback, _error_callback) {
    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        cache: false,
        processData: false,
        contentType: false,
        success:function(result){
            if (result.success) {
                if (_callback) {
                    _callback(result);
                }
            } else {
                if (_error_callback) {
                    _error_callback(result);
                }
                default_alert();
            }
        },
        error: function(result) {
            if (_error_callback) {
                _error_callback(result);
            }
            default_alert();
        }});
}

function default_alert() {
    $.messager.alert('请求错误','请求错误，请刷新页面重试!','error');
}

function default_success_notify($msg){
    $.messager.show({
        title:'成功',
        msg: $msg != null ? $msg : '保存成功!',
        showType:'show',
        timeout: 1000
    });
}

function notify_msg($title, $msg){
    $.messager.alert($title,$msg);
}