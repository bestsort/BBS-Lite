$(function () {
    // 加载问题详情
    //TODO 非法访问限制
    $("#question_detail").empty();
    const url = "/loadQuestionDetail";
    const jsonData = {
        "id": getQuestionId(),
        contentType: "application/json;charset=UTF-8"
    };
    $.ajax({
        type: "GET",
        url: url,
        data: jsonData,
        beforeSend: function () {
            //loadingIndex = layer.msg('加载数据~~', {icon: 16});
        },
        success: function (data) {
            debugger;
            if (data.code == "200") {
                load_question_info(data.extend.question);
                if(data.extend.question.commentCount > 5)
                    show_more_comment();
                load_question_detail_right(data.extend);
                $("html,body").animate({scrollTop: 0}, 0);//回到顶端
            } else {
            }
        },
    });


    $("#icon-bianji").click(function () {

    });

    $("#icon-shoucang").click(function () {

    });

    $("#icon-pinglun").click(function () {

    });
    //build_right_list();
});

function click_options() {
    $(document).on('click', "#icon-zan", function () {
        thumb_up_question();
    });
}
function thumb_up_question() {
    let icon = "icon-zan";
    $.ajax({
        type: "POST",
        url: "/thumbUpQuestion",
        data: {
            "id":getQuestionId(),
            "isActive":$("#"+icon).hasClass("on")
        },
        success: function (data) {
            if (data.code == "200") {
                let info = data.extend;
                add_option(info.count,"点赞",icon,info.isActive,true);
            }
        },
    });
}

function add_option(count,show,icon,isActive,show_count) {
    if(isActive != null) {
        if (show_count) {
            show += '(' + count + ')';
        }
        let html = '<span class="option ' + (isActive ? 'on' : '') + '" value="' + isActive + '" id="'
            + icon + '"><i class="iconfont ' + icon + '"></i>' +
            (isActive ? '已' : '') + show + '</span>';

        if($("#"+icon).length > 0) {
            $("#"+icon).replaceWith(html);
        }else {
            $("#option_item").append(html);
        }
    }
}
/**
 * 加载可交互选项信息(编辑/点赞/收藏等)
 * @param creator
 * @param questionId
 */
function load_question_option(creator, {followCount: questionFollow, id: questionId, likeCount: questionThumb}) {
    const url = "/loadQuestionOption";
    const jsonData = {
        "questionId": questionId,
        "userId": creator,
        contentType: "application/json;charset=UTF-8"
    };

    $.ajax({
        type: "GET",
        url: url,
        data: jsonData,
        beforeSend: function () {
            //loadingIndex = layer.msg('加载数据~~', {icon: 16});
        },
        success: function (data) {
            debugger
            if (data.code == "200") {
                let options = data.extend.options;
                add_option(questionThumb,"点赞","icon-zan",options.isThumbUpQuestion,true);
                add_option(questionFollow,"关注","icon-shoucang",options.isFollowQuestion,true);
                add_option(null,"评论","icon-pinglun",false,false);
                add_option(null,"编辑问题","icon-bianji",options.isCreator,false);
                click_options();
            }
        },
    });
}


function show_more_comment() {

    let show_more = $("<div class='col-lg-12 col-md-12 col-xs-12 col-sm-12' style='text-align: center'></div>");
    show_more.append($("<span href='#' style='cursor: pointer;color: #03658c' id='show_more_comment'>" +
        "查看更多评论 <i class='iconfont icon-xiangxia'></i></span>"));
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
            }
        },
    });
}


/**
 * 构建页面右栏
 * @param data question详情
 */
function load_question_detail_right(data) {

    const userInfo = data.user;
    let userSimpleInfo = '\n' +
        '            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n' +
        '                <small style="color: #333">发起人</small>\n' +
        '                <hr>\n' +
        '                <div class="media media-left" style="width: 120%">\n' +
        '                    <a href="/">\n' +
        '                        <img class="media-min img-circle" src="' + userInfo.avatarUrl + '">\n' +
        '                    </a>\n' +
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


function load_question_info(questionInfo) {
    const tags = questionInfo.tag.split(' ');
    let questionDetail = '<h3>' +
        '                   <span>' + questionInfo.title + '</span></h3>\n' +
        '                      <span class="aw-question-content">\n' +
        '                      <span>' + questionInfo.viewCount + ' 次浏览 • </span>\n' +
        '                      <span>' + questionInfo.likeCount + ' 人点赞 • </span>\n' +
        '                      <span> 发表于' + formatTimestamp(questionInfo.gmtCreate) + '</span>\n' +
        '                   </span><br>\n';

    $.each(tags,function (index, item) {
        const tagInfo =
            '<span class="label label-tag">\n' +
            '  <a href="/?tag=' +  item + '" target="_parent" style="color: #fff;cursor: pointer;text-decoration: none;">\n' +
            '      <i class="iconfont icon-biaoqian1"></i>\n' +
            '      <span>' + item + '</span>\n' +
            '  </a>\n' +
            '</span>\n';
        questionDetail += tagInfo;
    });
    questionDetail +=
        '<hr class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
        '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n' + questionInfo.description +
        '<br><div class="col-lg-12 col-md-12 col-xs-12 col-sm-12" id="option_item"></div>' +
        '    <hr class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
        '</div><br>';
    $("#question_detail").append(questionDetail);
    load_question_option(questionInfo.creator,questionInfo);
}
/**
 * 根据请求的url返回id值
 * @returns {string} id
 */
function getQuestionId() {
    return document.location.pathname.replace("/question/", "");
}