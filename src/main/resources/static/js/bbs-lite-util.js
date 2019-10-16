/**
 * 将时间由 timestamp 格式化为 yyyy-MM-dd HH:MM:SS 的形式
 * @param timestamp
 * @returns {string}
 */
function formatTimestamp( timestamp ) {
    var dateObj = new Date( timestamp );
    var year = dateObj.getYear() + 1900;
    var month = dateObj.getMonth() + 1;
    var theDate = dateObj.getDate();
    var hour = dateObj.getHours();
    var minute = dateObj.getMinutes();
    var second = dateObj.getSeconds();
    return year +"-"+ month +"-" + theDate + " "+ hour +":"+ minute +":"+ second;
}

/**
 * 根据name获取当前url中的请求参数
 * @param name
 * @returns {*}
 */
var getParam = function(name){
    var search = document.location.search;
    var pattern = new RegExp("[?&]"+name+"\=([^&]+)", "g");
    var matcher = pattern.exec(search);
    var items = null;
    if(null != matcher){
        try{
            items = decodeURIComponent(decodeURIComponent(matcher[1]));
        }catch(e){
            try{
                items = decodeURIComponent(matcher[1]);
            }catch(e){
                items = matcher[1];
            }
        }
    }
    return items;
};

/**
 * 弹出式提示框，默认1.2秒自动消失
 * @param message 提示信息
 * @param style 提示样式，有alert-info-success、alert-info-danger、alert-info-warning、alert-info-info
 * @param time 消失时间
 */
var prompt = function (message, style, time)
{
    style = (style === undefined) ? 'alert-info-success' : style;
    time = (time === undefined) ? 1200 : time;
    $('<div>')
        .appendTo('body')
        .addClass('alert-info ' + style)
        .html(message)
        .show()
        .delay(time)
        .fadeOut();
};

// 成功提示
var success_prompt = function(message, time)
{
    prompt(message, 'alert-info-success', time);
};

// 失败提示
var fail_prompt = function(message, time)
{
    prompt(message, 'alert-info-danger', time);
};

// 提醒
var warning_prompt = function(message, time)
{
    prompt(message, 'alert-info-warning', time);
};

// 信息提示
var info_prompt = function(message, time)
{
    prompt(message, 'alert-info-info', time);
};

var open_loading = function(){
    if($("#loading").length === 0) {
        let loading = '<div id="loading" class="loader"><div class="loading">' +
            '<div></div>' +
            '<div></div>' +
            '<div></div>' +
            '<div></div>' +
            '<div></div>' +
            '</div></div>';
        $("body").append(loading);
    }else {
        $("#loading").removeClass("hide-all");
    }
};
var close_loading = function () {
    $("#loading").addClass("hide-all");
};