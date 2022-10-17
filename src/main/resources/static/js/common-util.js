function doRequest(url, data, _callback, _error_callback) {
    let token = localStorage.getItem("material-token");
    $.ajax({url:url,
        type:"POST",
        dataType: "json",
        contentType : 'application/json',
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("material-token", token)
        },
        data: JSON.stringify(data),
        success:function(result,status,xhr){
            if (result.success) {
                let token = getHeader(xhr)['material-token'];
                cacheToken(token);
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
    //startProgress();
    let token = localStorage.getItem("material-token");
    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        cache: false,
        processData: false,
        contentType: false,
        beforeSend: function(request){
            request.setRequestHeader("material-token", token)
        },
        success:function(result,status,xhr){
            $('#uploading').dialog('close');
            if (result.success) {
                let token = getHeader(xhr)['material-token'];
                cacheToken(token);
                if (_callback) {
                    _callback(result);
                }
                success_upload_alert();
            } else {
                if (_error_callback) {
                    _error_callback(result);
                }
                default_alert();
            }
        },
        error: function(result) {
            $('#uploading').dialog('close');
            if (_error_callback) {
                _error_callback(result);
            }
            default_alert();
        }});
}

function default_alert() {
    $.messager.alert('请求错误','请求错误，请刷新页面重试!','error');
}

function success_upload_alert() {
    $.messager.show({
        title:'上传成功',
        msg: $msg != null ? $msg : '上传成功!',
        showType:'show',
        timeout: 1000
    });
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

function cacheToken(value) {
    localStorage.setItem("material-token", value);
}

function getHeader(xhr) {
    let headers = xhr.getAllResponseHeaders();
    let arr = headers.trim().split(/[\r\n]+/);
    let headerMap = {};
    arr.forEach(function (line) {
        let parts = line.split(': ');
        let header = parts.shift();
        let value = parts.join(': ');
        headerMap[header] = value;
    });
    return headerMap;
}

function getParamValue(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

function startProgress(){
    $('#uploading').dialog('open');
    let value = $('#progress').progressbar('getValue');
    if (value < 100){
        value += Math.floor(Math.random() * 10);
        $('#p').progressbar('setValue', value);
        //setTimeout(arguments.callee, 1000*60);
    }
}