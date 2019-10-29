let question_like_count;
let question_follow_count;
let question_comment_count;
let question_view_count;

$(function () {
    // 加载问题详情
    //TODO 非法访问限制
    $("#question_detail").empty();
    const url = "/loadQuestionDetail";
    ajax_get(
        url,
        {"id": getQuestionId()},
        function (data) {
        document.title = data.extend.question.title;
        load_question_info(data.extend.question);
        if(data.extend.question.commentCount > 0) {
            show_more_comment();
        }
        load_question_detail_right(data.extend);
        $("html,body").animate({scrollTop: 0}, 0);//回到顶端
        },
        function (data) {
            fail_prompt(data.message);
            setTimeout(function () {
                location.href = "/";
            },1500)
        });
});

function click_options() {
    $(document).on('click',"#send-reply",function () {
        let content = $("#reply-text").val();
        ajax_post("/commitComment",{
            questionId:getQuestionId(),
            pid:$("#reply-to-id").val(),
            content:content},
            function () {

                $("#reply").modal('hide');
                load_comment_info();
            })
    });
    $(document).on('click', "#"+thumb_up_icon, function () {
        like_or_follow_question("/thumbUpQuestion",thumb_up_icon,"点赞",question_like_count);
    });
    $(document).on('click',"#icon-bianji",function () {
        location.href = "/publish?id=" + getQuestionId();
    });
    $(document).on('click',"#"+follow_icon,function () {
        like_or_follow_question("/followQuestion",follow_icon,"收藏",question_follow_count);
    });
    $(document).on('click',"#"+comment_icon, function(){
        let val = "#comment_input";
        if($(val).length === 0){
            $("#question_detail").append(
                '<div id="comment_input" class="col-lg-12 col-md-12 col-xs-12 col-sm-12 collapse" style="padding-right: 0;padding-bottom: 50px">' +
                '<textarea class="form-control" rows="5"></textarea>' +
                '<button class="btn btn-info right" id="commit_comment" style="' +
                'margin: 10px 0;' +
                'float: right;' +
                '">发布</button></div>'
            )
        }
        $(val).collapse('toggle');
    });
    $(document).on('click',"#commit_comment",function () {
        commit_comment();
    });
    $(document).on('click',".comment-post>span",function () {
        /**
         * 子评论
         */
        if ($(this).parents("li").length === 2){
            let own = $(this).prevAll("input");
            let pid = $(this).parents("li")[1];
            reply_comment(own,pid);
        }
        /**
         * 父评论
         */
        else {
            let commentId = $(this).prevAll("input").val();
            /**
             * 评论
             */
            if ($(this).next().length === 0){
                reply_comment($(this).prevAll("input"));
            }
            /**
             * 点赞
             */
            else{
                let count = $(this).children().text().replace(/[^\d]/g, '');
                let isActive = $(this).children().hasClass("on");
                thumb_up_comment(commentId,isActive,count,$(this));
            }
        }
    });
}

function reply_comment(kid,parent) {
    /**
     * 回复父评论
     */
    let reply_to = '@' + kid.parent().prev().children().text();
    if (parent === undefined){
        $("#reply-to-id").val(kid.val());
    }else {
        $("#reply-to-id").val($(parent).val());
    }
    $("#reply-label").val(reply_to);
    $("#reply").modal('show');
}
function commit_comment(val) {
    let jsonData = {
        "questionId":getQuestionId(),
        "pid":val,
        "content":$("#commit_comment").prev().val(),
    };
    ajax_post("/commitComment",jsonData,
        function (data) {
        success_prompt("成功");
        setTimeout(function () {
            load_comment_info();
        },1200);}
    );
}
function thumb_up_comment(id,isActive,count,e) {
    ajax_post("/thumbUpQuestion",
        {
            "id":id,
            "isActive": isActive,
            "type":"COMMENT"
        },
        function (data) {
            let info = data.extend;
            count = parseInt(count) + (info.isActive?1:-1);

            let html = '<span class="option"><i class="iconfont icon-zan' + (info.isActive?" on ":"") +'">'
                +(info.isActive?"已":"") + '点赞(' + count + ')</i></span>';
            e.replaceWith(html);
        });
}
function like_or_follow_question(url,icon,show,count) {
    ajax_post(url,
        {
            "id":getQuestionId(),
            "isActive":$("#"+icon).hasClass("on"),
            "type":"QUESTION"
        },
        function(data){
            let info = data.extend;
            count += (info.isActive?1:-1);
            add_option(count,show,icon,info.isActive,true);
        });
}

/**
 * 添加问题下方按钮(点赞/收藏/评论/编辑)
 * @param count 计数信息(点赞/收藏等)
 * @param show  展示的文字
 * @param icon  图标信息(class)
 * @param isActive 是否选中
 * @param show_count 是否展示计数信息
 */
function add_option(count,show,icon,isActive,show_count) {
    if(isActive != null) {
        let html = '<span class="option hover-point ' + (isActive ? 'on' : '') + '" value="' + isActive + '" id="'
            + icon + '"' +
            (icon===comment_icon?' data-toggle="collapse" aria-expanded="false" aria-controls="comment_input"':'') +
            '><i class="iconfont ' + icon + '"></i>' + (isActive ? '已' : '');

        if($("#"+icon).length > 0) {
            let count2 = $("#"+icon).text().replace(/[^\d]/g, '');
            count2 = parseInt(count2) + (isActive?1:-1);
            if (show_count) {
                show += '(' + count2 + ')';
            }
            html += show + '</span>';
            $("#"+icon).replaceWith(html);
        }else {
            if (show_count) {
                show += '(' + count + ')';
            }

            html += show + '</span>';
            $("#option_item").append(html);
        }
    }
}
/**
 * 加载可交互选项信息(编辑/点赞/收藏等)
 * @param creator
 * @param questionId
 */
function load_question_option(creator, {commentCount:questionComment,followCount: questionFollow, id: questionId, likeCount: questionThumb}) {
    const url = "/loadQuestionOption";
    const jsonData = {
        "questionId": questionId,
        "userId": creator,
    };
    ajax_get(url,jsonData,function (data) {
        let options = data.extend.options;
        add_option(questionThumb,"点赞",thumb_up_icon,options.isThumbUpQuestion,true);
        add_option(questionFollow,"收藏",follow_icon,options.isFollowQuestion,true);
        add_option(null,"评论",comment_icon,false,false);
        add_option(null,"编辑问题","icon-bianji",options.isCreator,false);
        click_options();
    });
}


function show_more_comment() {
    let show_more = $("<div class='col-lg-12 col-md-12 col-xs-12 col-sm-12' style='text-align: center'></div>");
    show_more.append($("<span href='#' style='cursor: pointer;color: #155faa;font-size: 14px;' id='show_more_comment'>" +
        "查看评论( " + question_comment_count +" )  <i class='iconfont icon-xiangxia'></i></span>"));
    show_more.click(function () {
        load_comment_info();
    });
    $("#question_detail").append(show_more);
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
        '                <img class="media-min img-circle" src="' + userInfo.avatarUrl + '">\n' +
        '                </div>\n' +
        '\n' +
        '                <hr>\n' +
        '                <small style="font-size: 12px;color: #1f1f1f;">\n' +
        '                    昵称:\n' +
        '                    <!-- TODO 跳转到个人主页 -->\n' +
        '                    <a href="/profile/user?id='+ userInfo.id + '" style="color: #155faa">' + userInfo.name + '</a>\n' +
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
        userSimpleInfo +=
        '            </div>\n' +
        '            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n' +
        '                <hr class="col-lg-11 col-md-11 col-sm-11 col-xs-11">\n' +
        '                <h4>相关问题</h4>\n' +
        '            </div>\n';
        $("#question_detail_right").append(userSimpleInfo);
}


function load_question_info(questionInfo) {
    question_view_count = questionInfo.viewCount;
    question_like_count = questionInfo.likeCount;
    question_follow_count = questionInfo.followCount;
    question_comment_count = questionInfo.commentCount;
    const tags = questionInfo.tag.split(' ');
    let questionDetail = '<h3>' +
        '                   <span>' + questionInfo.title + '</span></h3>\n' +
        '                      <span class="aw-question-content">\n' +
        '                      <span>' + question_view_count + ' 次浏览 • </span>\n' +
        '                      <span>' + question_like_count + ' 人点赞 • </span>\n' +
        '                      <span>' + question_comment_count + ' 人评论 • </span>\n' +
        '                      <span> 发表于' + formatTimestamp(questionInfo.gmtCreate) + '</span>\n' +
        '                   </span><br>\n';

    $.each(tags,function (index, item) {
        const tagInfo =
            '<span class="label label-tag hover-point">\n' +
            '  <a href="/?tag=' +  item + '" target="_parent" style="color: #fff;cursor: pointer;text-decoration: none;">\n' +
            '      <i class="iconfont icon-biaoqian1"></i>\n' +
            '      <span>' + item + '</span>\n' +
            '  </a>\n' +
            '</span>\n';
        questionDetail += tagInfo;
    });
    questionDetail +=
        '<hr class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
        '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n' + questionInfo.description +'</div>'+
        '<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12" id="option_item"></div>' +
        '    <hr class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
        '</div>';
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
function load_comment_info(){
    const url = "/loadComment";
    ajax_get(url,{"id":getQuestionId()},function (data){
        let question = $("#question_comment");
        $("#show_more_comment").parent("div").hide();
        question.empty();
        let comments = data.extend.comments;
        let question_creator = data.extend.creator;
        let view = '<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
            '    <ul id="comments-list" class="comments-list">\n' +
            '    </ul>\n' +
            '</div>';
        question.append(view);
        $.each(comments,function (index,item) {
            load_comment_html(item,question_creator);
        })
    });
}

function load_comment_html(comment,creator) {
    let byUser = comment.commentByUser;
    let html =
        '        <li style="margin: 0" value="' + comment.id + '">\n' +
        '            <div class="comment-main-level">\n' +
        '                <div class="comment-avatar"><img src="' + byUser.avatarUrl + '" alt=""></div>\n' +
        '                <div class="comment-box">\n' +
        '                    <div class="comment-head">\n' +
        '                        <h6 class="comment-name ' + (byUser.id==creator?'by-author ':'') + '">\n' +
        '                            <a href="'+ byUser.htmlUrl + '">'+ byUser.name + '</a>\n' +
        '                        </h6>\n' +
        '                        <span class="comment-post pull-right" style="top: 0;"> \n' +
        '                            <input name="commentId" value="' + comment.id + '" hidden="true">' +
        '                            <span class="option hover-point"><i class="iconfont icon-zan'+ (comment.isActive?" on ":"") + '">'+ (comment.isActive?"已":"") + '点赞(' + comment.likeCount + ')</i></span>\n' +
        '                            <span class="option hover-point" style="margin-right: 25px"><i class="iconfont icon-fenxiang" ></i>评论</span>\n' +
        '                    </span></div>\n' +
        '                    <div class="comment-content">' + comment.content +
        '                        <span> ' + formatTimestamp(comment.gmtCreate) + '</span>\n' +
        '</div>\n' +
        '                </div>\n' +
        '            </div>\n<!-- Respuestas de los comentarios -->\n' +
        '            <ul class="comments-list reply-list">\n';
        $.each(comment.son,function (index,item) {
            let bySonUser = item.commentByUser;
            html +=
                '                <li>\n' +
                '                    <!-- Avatar -->\n' +
                '                    <div class="comment-avatar"><img src="' + bySonUser.avatarUrl + '" alt=""></div>\n' +
                '                    <!-- Contenedor del Comentario -->\n' +
                '                    <div class="comment-box">\n' +
                '                        <div class="comment-head">\n' +
                '                            <h6 class="comment-name ' + (bySonUser.id==creator?'by-author':'') + '"><a href="'+ bySonUser.htmlUrl + '">'+ bySonUser.name + '</a></h6>\n' +
                '                            <span class="comment-post pull-right">\n' +
                '                            <input value="' + bySonUser.id + '" hidden="true">' +
                '                                 <span class="option hover-point"  style="margin-right: 25px;top: 0"><i class="iconfont icon-fenxiang"></i>评论</span>\n' +
                '                            </span>\n' +
                '                        </div>\n' +
                '                        <div class="comment-content">'+ item.content +
                '                            <span>' + formatTimestamp(item.gmtCreate) + '</span>\n' +
                '                    </div></div>\n' +
                '                </li>\n';
        });
        html +=                 '        </ul>\n';
    $("#comments-list").append(html);
}
