/**
 * 提交用户注册/登录表单
 */
$(function () {
    let dic = getUrlVars();
    let form = $("#ajaxVal");
    for (let i in dic){
        form.children("input[name='" +i + "']").val(dic[i]);
    }

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
    $('#search-form').submit(function (e) {
        e.preventDefault();
        window.location.href="/?search=" + $("#search-field").val();
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
 * 构建页面右栏
 * @param data article详情
 */
function load_right_with_data(data) {
    const userInfo = data.user;
    let userSimpleInfo = '\n' +
        '            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 fadein-1s">\n' +
        '                <small style="color: #333">用户信息</small>\n' +
        '                <hr>\n' +
        '                <div class="media media-left" style="width: 120%">\n' +
        '                <img class="media-min img-circle" style="border-radius: 30%" src="' + userInfo.avatarUrl + '">\n' +
        '                </div>\n' +
        '\n' +
        '                <hr>\n' +
        '                <small style="font-size: 12px;color: #1f1f1f;">\n' +
        '                    昵称:\n' +
        '                    <!-- TODO 跳转到个人主页 -->\n' +
        '                    <a href="/people?user='+ userInfo.id + '" style="color: #155faa">' + userInfo.name + '</a>\n' +
        '                    <br>\n';

    if (userInfo.htmlUrl !== null) {
        userSimpleInfo +=
            '                    <span>\n' +
            '                        网址:<a target="_blank" href="' + userInfo.htmlUrl + '" style="color: #155faa">\n' +
            '                        ' + userInfo.htmlUrl + '</a>\n' +
            '                    </span>\n<br>';
    }
    if (userInfo.bio !== null) {
        userSimpleInfo +=
            '                    <span>\n' +
            '                        简介:<span style="color: #155faa">'+ userInfo.bio + '</span>\n' +
            '                    </span>\n';
    }
    $("#article_detail_right").append(userSimpleInfo);
    return userInfo.name;
}


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
    let month = (dateObj.getMonth() + 1);
    month = (month<10?"0":"") + month;
    let theDate = dateObj.getDate();
    theDate = (theDate<10?"0":"") + theDate;
    let hour = dateObj.getHours();
    hour = (hour<10?"0":"") + hour;
    let minute = dateObj.getMinutes();
    minute = (minute<10?"0":"") + minute;
    let second = dateObj.getSeconds();
    second = (second<10?"0":"") + second;
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
function getUrlVars() {
    let vars = {}, hash;
    let url = window.location.search;
    if(url.indexOf("?") !== -1){
    let hashes = url.slice(url.indexOf("?")+1).split('&');
        for (let i = 0; i < hashes.length; i++) {
            hash = hashes[i].split('=');
            let name = hash[0].replace("#","");
            vars[name] = decodeURI(hash[1].replace("#",0));
        }
    }
    return vars;
}

function form_to_dic() {
    let params = $("#ajaxVal").serializeArray();
    var values = {};
    for (x in params) {
        if (params[x].value !== "")
            values[params[x].name] = params[x].value;
    }
    return values;
}
/**
 * 构建分页导航
 * @param data
 * @param url
 */
function build_page_nav(data,func) {
    const page = data.extend.page;
    /**
     * 设置当前页
     */
    currentpage = page.pageNum;
    /**
     * 设置末页
     */
    totalpageo = page.pages;
    //$('.page_info-area').empty();
    $(".pagination").empty();
    //$('.page_info-area').append("当前第" + page.pageNum + "页,共" + page.pages + "页,共" + page.total + "条记录")
    //分页导航
    if (totalpageo > 1){
        let nav = $(".pagination");
        let firstLi = $("<li class='page-item'></li>")
            .append($("<a class='page-link'>首页</a>")
                .attr("href", "#"));

        let prli = $("<li class='page-item'></li>")
            .append($("<a class='page-link' aria-label='Previous'><span aria-hidden='true'>上一页</span></a>")
                .attr("href", "#"));
        //首页
        firstLi.click(function () {
            func(1);
        });
        //上一页
        prli.click(function () {
            var target = page.pageNum - 1;
            target = target == 0 ? 1 : target;
            func(target);
        });
        let lastLi = $("<li class='page-item'></li>")
            .append($("<a class='page-link'>尾页</a>").attr("href", "#"));
        let nextli = $("<li class='page-item'></li>")
            .append($("<a class='page-link' aria-label='Next'><span aria-hidden='true'>下一页</span></a>").attr("href", "#"));
        //末页
        lastLi.click(function () {
            //alert("转到:"+page.pages)
            func(page.pages);
        });
        //下一页
        nextli.click(function () {
            var target = page.pageNum + 1;
            target = target < page.pages ? target : page.pages;
            func(target);
        });

        if(!page.isFirstPage){
            nav.append(firstLi);
        }
        if (page.hasPreviousPage) {
            nav.append(prli);
        }

        $.each(data.extend.page.navigatepageNums, function (index, item) {
            const li = $("<li class='page-item'></li>");
            if (data.extend.page.pageNum == item) {
                li.addClass("active");
                li.append($("<span class='page-link'>" + item + "</span>").attr("href", "#"));
            }
            else {
                li.append($("<a class='page-link'>" + item + "</a>").attr("href", "#"));
            }
            //点击翻页
            li.click(function () {
                $(".pagination li").removeClass("active");
                $(this).addClass("active");
                func(item);
                return false;
            });
            nav.append(li);
        });

        if(page.hasNextPage){
            nav.append(nextli);
        }
        if(!page.isLastPage){
            nav.append(lastLi);
        }
    }
}

/**
 * @description 构建文章列表
 * @param data
 * @param build_to
 */
function build_article_list(data,build_to) {
    //清空
    $(build_to).empty();
    const articles = data.page.list;
    $.each(articles, function (index, item) {
        const article = $('<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12 bbs-article-list-item"' +
            '        <div class="media media-margin" >\n' +
            '            <!-- 头像 -->\n' +
            '            <div class="media-left media-left-margin">\n' +
            '                <a href="#">\n' +
            '                    <img class="media-min img-rounded" style="border-radius: 30%" src=' + item.userAvatarUrl + '>\n' +
            '                </a>\n' +
            '            </div>\n' +
            '            <!-- 描述 -->\n' +
            '            <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
            '                        <span>\n' +
            '                            <a href="/article/' + item.id + '" class="media-heading">' + item.title + '</a>\n' +
            '                        </span>\n' +
            '                <br>\n' +
            '                <span class="aw-article-content">\n' +
            '                            <span>' + item.userName + ' • </span>\n' +
            '                            <span>' + item.viewCount + '次浏览 • </span>\n' +
            //'                            <span>' + item.commentCount + '个回复 • ></span>\n' +
            '                            <span>' + item.likeCount + '人点赞 • </span>\n' +
            '                            <span>' + item.commentCount + '人评论</span>\n' +
            '                            <span style="float:right">发表于 ' + formatTimestamp(item.gmtCreate) + '</span>\n' +
            '                        </span>\n' +
            '            </div>\n' +
            '        </div>');
        $(build_to).append(article);
    })
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

function ajax_function(url,data,success_function,method,fail_function,complete) {
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
        complete: function (data) {
            if (complete !== undefined){
                complete(data.responseJSON);
            }
            close_loading();
        }
    });
}

function ajax_get(url,data,success,fail,complete) {
    ajax_function(url,data,success,"GET",fail,complete);
}
function ajax_post(url,data,success,fail,complete) {
    ajax_function(url,data,success,"POST",fail,complete);
}