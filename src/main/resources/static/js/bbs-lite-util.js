/**
 * 提交用户注册/登录表单
 */
$(function () {
    $('.auto-expand').each(function () {
        this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
        this.setAttribute('style', 'min-height: 100px;');
    }).on('input', function () {
        this.style.height = 'auto';
        let height = 100;
        if(this.scrollHeight > 100){
            height = this.scrollHeight;
        }
        debugger
        this.style.height = (height+5) + 'px';
    });

    $(document).on('click',"#change-login-method",function () {
        let account_login = $("#account-login");
        let email_login = $("#mail-login");
        let link = $("#change-login-method");
        if (account_login.hasClass("hide-all") === true){
            account_login.removeClass("hide-all");
            email_login.addClass("hide-all");
            link.text("邮箱登录");
        }else {
            email_login.removeClass("hide-all");
            account_login.addClass("hide-all");
            link.text("账号登录");
        }
    });
    $(document).on('click',"#login-button",function () {
       let password = $("input[name='login_password']").val();
       let isAccount = !$("#account-login").hasClass("hide-all");
       let type = (isAccount?'account':'email');
       let data = {
           "account": $("input[name='login-" + type + "']").val(),
           "password":password,
           "type": type
       };
       ajax_post("/login",data,function () {
               success_prompt("登录成功");
               setTimeout(function () {
                   location.reload();
               },1500)
           })
    });
    $(document).on('click', "button[name='github-login']", function () {
        location.href="/github-login";
    });

    $(document).on('click', "#login-link", function () {
        init_third_login_button();
        $("#login-link-modal").modal();
    });
    $(document).on('click', "#sign-up-link", function () {
        init_third_login_button();
        $("#sign-up-link-modal").modal();
    });
    $(document).on('click', "#send-sign-up-button", function () {
        let dom =$("#sign-up-form");
        if (dom.valid()) {
            let values = dom.serializeArray();
            let data = {
            };
            for (let index in values) {
                if (values[index].name == "sign_confirm_password")
                    continue;
                data[values[index].name.substring(5)] = values[index].value;
            }
            ajax_post("sign-up",data,function (data) {
                success_prompt("注册成功,请前往您的邮箱激活账户",3000);
                setTimeout(function () {
                    location.reload();
                },3500)
            });
        }
    });
});


/**
 * 注册,表单校验
 **/

$(function(){
    $("#sign-up-form").validate({
        errorPlacement:function(error,element) {
            fail_prompt(error,1500);
        },
        onfocusout:false,
        onkeyup:false,
        onclick:false,
        rules:{
            sign_password:{
                required:true,
                rangelength:[6,18]
            },
            sign_confirm_password:{
                required: true,
                equalTo: "input[name='sign_password']"
            },
            sign_email:{
                required:true,
                email: true
            },
            sign_account:{
                required:true,
                rangelength:[6,12]
            }
        },
        messages:{
            sign_account: {
                required:"账户不能为空",
                rangelength:"账号长度在6-12之间"
            },
            sign_password: {
                required:"密码不可为空",
                rangelength:"密码长度在6-18之间"
            },
            sign_confirm_password: {
                required:"重复密码不可为空",
                equalTo: "两次输入密码不一致"
            },
            sign_email:{
                required:"邮件地址不可为空",
                email:"邮件地址有误,请检查"
            }
        }
    });
});
/**
 * 将时间由 timestamp 格式化为 yyyy-MM-dd HH:MM:SS 的形式
 * @param timestamp
 * @returns {string}
 */
function formatTimestamp( timestamp ) {
    let dateObj = new Date( timestamp );
    let year = dateObj.getYear() + 1900;
    let month = dateObj.getMonth() + 1;
    let theDate = dateObj.getDate();
    let hour = dateObj.getHours();
    let minute = dateObj.getMinutes();
    let second = dateObj.getSeconds();
    return year +"-"+ month +"-" + theDate + " "+ hour +":"+ minute +":"+ second;
}

/**
 * 根据name获取当前url中的请求参数
 * @param name
 * @returns {*}
 */
function getParam(name){
    let search = document.location.search;
    let pattern = new RegExp("[?&]"+name+"\=([^&]+)", "g");
    let matcher = pattern.exec(search);
    let items = null;
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
}

/**
 * 弹出式提示框，默认1.2秒自动消失
 * @param message 提示信息
 * @param style 提示样式，有alert-info-success、alert-info-danger、alert-info-warning、alert-info-info
 * @param time 消失时间
 */
function prompt(message, style, time)
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
function success_prompt(message, time)
{
    prompt(message, 'alert-info-success', time);
};

// 失败提示
function fail_prompt(message, time)
{
    prompt(message, 'alert-info-danger', time);
};

// 提醒
function warning_prompt(message, time)
{
    prompt(message, 'alert-info-warning', time);
};

// 信息提示
function info_prompt(message, time)
{
    prompt(message, 'alert-info-info', time);
}

function open_loading(){
    if($("#loading").length === 0) {
        let loading =
            '<div id="loading" class="loader"><div class="loading">' +
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
}
function close_loading() {
    $("#loading").addClass("hide-all");
}

function init_third_login_button() {
    let third_login_button =
        '<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12 aw-article-content" style="margin-top: 20px;font-size: 14px">您可以用以下方式免注册登录<br>(部分暂时无法使用)</div>\n' +
        '                            <button name="github-login" class="col-lg-5 col-md-5 col-xs-5 col-sm-5 btn btn-dark third-login-button"><i class="iconfont icon-git margin-center"></i>GitHub</button>\n' +
        '                            <button name="baidu-login" class="col-lg-5 col-md-5 col-xs-5 col-sm-5 btn btn-primary third-login-button" disabled><i class="iconfont icon-baidu margin-center"></i>Baidu</button>\n' +
        '                            <button name="wechat-login" class="col-lg-5 col-md-5 col-xs-5 col-sm-5 btn btn-success third-login-button" disabled><i class="iconfont icon-weixin margin-center"></i>WeChat</button>\n' +
        '                            <button name="qq-login" class="col-lg-5 col-md-5 col-xs-5 col-sm-5 btn btn-info third-login-button" disabled><i class="iconfont icon-QQ margin-center"></i>QQ</button>';
    let button = $("div[name='third-login-button']");
    button.empty();
    button.append(third_login_button);
}

function ajax_function(url,data,success_function,method,fail_function) {
    $.ajax({
        type: method,
        url: url,
        data: data,
        beforeSend: open_loading(),
        success:function (data) {
            if (data.code === 200){
                success_function(data);
            }else {
                if (fail_function !== undefined){
                    fail_function(data);
                }
                else {fail_prompt(data.message);}
            }
        },
        complete: close_loading()
    });
}

function ajax_get(url,data,success,fail) {
    ajax_function(url,data,success,"GET",fail);
}
function ajax_post(url,data,success,fail) {
    ajax_function(url,data,success,"POST",fail);
}