
$(function () {
    // 加载问题详情
    //TODO 非法访问限制
    debugger
    build_page();
    //build_right_list();
});



/**
 * 根据 id 查询问题详情
 */
function build_page() {
    $("#question_detail").empty();
    const url = "/loadQuestionDetail";
    const jsonData = {
        "id": getQuestionId(),
        "contentType": "application/json;charset=UTF-8"
    };

    $.ajax({
        type: "GET",
        url: url,
        data: jsonData,
        beforeSend: function () {
            //loadingIndex = layer.msg('加载数据~~', {icon: 16});
        },
        success: function (data) {
            if (data.code == "200") {
                load_question_detail(data);
                show_comment_btn();
                load_question_detail_right(data);

                $("html,body").animate({scrollTop: 0}, 0);//回到顶端
            } else {
                alert("error");
            }
        },
    });
}
function show_comment_btn() {
    let show_more = $("<div class='col-lg-12 col-md-12 col-xs-12 col-sm-12' style='text-align: center'></div>");
    show_more.append($("<a href='#' class='hvr-icon-hang' >查看评论 <i class='fa fa-chevron-down hvr-icon'></i></a>"));
    show_more.click(function () {
        show_more.empty();
        load_comment_info();
    });
    $("#question_detail").append(show_more);
}
function load_comment_info(){
    const url = "/loadComment";
    const jsonData = {
        "id": getQuestionId(),
        "contentType": "application/json;charset=UTF-8"
    };

    $.ajax({
        type: "GET",
        url: url,
        data: jsonData,
        beforeSend: function () {
            //loadingIndex = layer.msg('加载数据~~', {icon: 16});
        },
        success: function (data) {
            if (data.code == "200") {

            } else {
                alert("error");
            }
        },
    });
}


/**
 * 构建页面右栏
 * @param data question详情
 */
function load_question_detail_right(data) {
    const userInfo = data.extend.user;
    let userSimpleInfo = '\n' +
        '            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n' +
        '                <small style="color: #333">发起人</small>\n' +
        '                <hr>\n' +
        '                <div class="media media-left" style="width: 120%">\n' +
        '                    <a href="/">\n' +
        '                        <img class="media-min img-circle" src="' + userInfo.avatarUrl + '">\n' +
        '                    </a>\n' +
        /*'                    <form action="/follow" method="post" name="follow" target="targetIfr2">\n' +
        '                        <input type="hidden" name="followBy"\n' +
        '                               th:if="${questionInfoVo.user != null}" th:value="${questionInfoVo.user.id}">\n' +
        '                        <input type="hidden" name="followTo" th:value="${questionInfoVo.question.creator}">\n' +
        '\n' +
        '                        <button th:text="${\'关注|\' + questionInfoVo.user.name}"\n' +
        '                                class="btn btn-outline-success btn-sm"\n' +
        '                                style="margin-top: 13%;margin-left: 200%; width: 100%"\n' +
        '                                name="type" value="user" type="submit"></button>\n' +
        '                    </form>\n' +
        '                    <iframe name="targetIfr2" id="targetIfr2" style="display:none"></iframe>\n' +*/
        '                </div>\n' +
        '\n' +
        '                <hr>\n' +
        '                <small style="font-size: 12px;color: #1f1f1f;">\n' +
        '                    昵称:\n' +
        '                    <!-- TODO 跳转到个人主页 -->\n' +
        '                    <a href="#" style="color: #155faa">' + userInfo.name + '</a>\n' +
        '                    <br>\n';

        if (userInfo.htmlUrl !== "null") {
            userSimpleInfo +=
                '                    <span>\n' +
                '                        网址:<a target="_blank" href="' + userInfo.htmlUrl + '" style="color: #155faa">\n' +
                '                        ' + userInfo.htmlUrl + '</a>\n' +
                '                    </span>\n<br>';
        }
        if (userInfo.bio !== "null") {
            userSimpleInfo +=
                '                    <span>\n' +
                '                        简介:<span style="color: #155faa">'+ userInfo.bio + '</span>\n' +
                '                    </span>\n';
        }
        userSimpleInfo +=
        '            </div>\n' +
        '            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n' +
        '                <hr class="col-lg-11 col-md-11 col-sm-11 col-xs-11">\n' +
        '                <h4>相关问题</h4>\n' +
        '            </div>\n';
        $("#question_detail_right").append(userSimpleInfo);
}
/**
 * 构建HTML页面
 * @param data question详情
 */
function load_question_detail(data) {
    const questionInfo = data.extend.question;
    const tags = questionInfo.tag.split(' ');
    let questionDetail = '<h3>' +
        '                   <span>' + questionInfo.title + '</span></h3>\n' +
        '                      <span class="aw-question-content">\n' +
        '                      <span>' + questionInfo.viewCount + ' 次浏览 • </span>\n' +
        '                      <span>' + questionInfo.likeCount + ' 人点赞 • </span>\n' +
        '                      <span> 发表于' + formatTimestamp(questionInfo.gmtCreate) + '</span>\n' +
        '                   </span><br>\n';
    $.each(tags,function (index, item) {
        debugger
        const tagInfo =
            '<span class="label label-tag">\n' +
            '  <a href="/?tag=' +  item + '" target="_parent" style="color: #fff;cursor: pointer;text-decoration: none;">\n' +
            '      <i class="fas fa-tags"></i>\n' +
            '      <span>' + item + '</span>\n' +
            '  </a>\n' +
            '</span>\n';
        questionDetail += tagInfo;
    });
    questionDetail +=
    '<hr class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
    '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n' + questionInfo.description +
    '    <hr class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
    '</div>';
    $("#question_detail").append(questionDetail);
}

/**
 * 根据请求的url返回id值
 * @returns {string} id
 */
function getQuestionId() {
    return document.location.pathname.replace("/question/", "");
}