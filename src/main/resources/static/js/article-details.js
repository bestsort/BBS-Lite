let article_like_count;
let article_follow_count;
let article_comment_count;
let article_view_count;

$(function () {


    // 加载文章详情
    //TODO 非法访问限制
    $("#article_detail").empty();
    const url = "/loadArticleDetail";
    debugger
    ajax_get(
        url,
        {"id": getArticleId()},
        function (data) {
            document.title = data.extend.article.title + " | " + data.extend.user.name;
            load_article_info(data.extend.article);
            editormd.markdownToHTML("markdown-text", {
                htmlDecode      : "style,script,iframe",
                emoji           : true,
                taskList        : true,
                tex             : true
            });
            $("html,body").animate({scrollTop: 0}, 0);//回到顶端
            load_comment_info();
        },
        function (data) {
            fail_prompt(data.message);
            setTimeout(function () {
                location.href = "/";
            },1500)
        },function (data) {
            if(data.extend.article.commentCount > 0) {
                show_more_comment();
            }
            load_right_with_data(data.extend);
        });
});

function click_options() {
    $(document).on('click',"#send-reply",function () {
        let content = $("#reply-text").val();
        ajax_post("/commitComment",{
            articleId:getArticleId(),
            pid:$("#reply-to-id").val(),
            sendToUser:$("#reply-to-user").val(),
            content:content},
            function () {

                $("#reply").modal('hide');
                load_comment_info();
            })
    });
    $(document).on('click', "#"+thumb_up_icon, function () {
        like_or_follow_article("/thumbUpArticle",thumb_up_icon,"点赞",article_like_count);
    });
    $(document).on('click',"#icon-bianji",function () {
        location.href = "/publish?id=" + getArticleId();
    });
    $(document).on('click',"#"+follow_icon,function () {
        like_or_follow_article("/followArticle",follow_icon,"收藏",article_follow_count);
    });
    $(document).on('click',"#"+comment_icon, function(){
        let val = "#comment_input";
        if($(val).length === 0){
            $("#article_detail").append(
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
        let jsonData = {
            "articleId":getArticleId(),
            "content":$("#commit_comment").prev().val(),
        };
        ajax_post("/commitComment",jsonData,
            function (data) {
                success_prompt("成功");
                setTimeout(function () {
                    load_comment_info();
                },1200);}
        );
    });
    $(document).on('click',".comment-post>span",function () {
        /**
         * 子评论
         */
        let creator = $(this).prevAll("input[name='creator']").val();
        let ownId = $(this).prevAll("input[name='commentId']");

        if ($(this).parents("li").length === 2){
            let pid = $(this).parents("li")[1];
            reply_comment(ownId,creator,pid);
        }
        /**
         * 父评论
         */
        else {
            let commentId = $(this).prevAll("input[name='commentId']").val();
            /**
             * 评论
             */
            if ($(this).next().length === 0){
                reply_comment(ownId, creator);
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

function reply_comment(kid,creator,parent) {
    /**
     * 回复父评论
     */
    let reply_to = '@' + kid.parent().prev().children().text();
    if (parent === undefined){
        $("#reply-to-id").val(kid.val());
    }else {
        $("#reply-to-id").val($(parent).val());
    }
    $("#reply-to-user").val(creator);
    $("#reply-label").val(reply_to);
    $("#reply").modal('show');

}
function thumb_up_comment(id,isActive,count,e) {
    ajax_post("/thumbUpArticle",
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
function like_or_follow_article(url,icon,show,count) {
    ajax_post(url,
        {
            "id":getArticleId(),
            "isActive":$("#"+icon).hasClass("on"),
            "type":"ARTICLE"
        },
        function(data){
            let info = data.extend;
            count += (info.isActive?1:-1);
            add_option(count,show,icon,info.isActive,true);
        });
}

/**
 * 添加文章下方按钮(点赞/收藏/评论/编辑)
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
 * @param articleId
 */
function load_article_option(creator, {commentCount:articleComment,followCount: articleFollow, id: articleId, likeCount: articleThumb}) {
    const url = "/loadArticleOption";
    const jsonData = {
        "articleId": articleId,
        "userId": creator,
    };
    ajax_get(url,jsonData,function (data) {
        let options = data.extend.options;
        add_option(articleThumb,"点赞",thumb_up_icon,options.isThumbUpArticle,true);
        add_option(articleFollow,"收藏",follow_icon,options.isFollowArticle,true);
        add_option(null,"评论",comment_icon,false,false);
        add_option(null,"编辑文章","icon-bianji",options.isCreator,false);
        click_options();
    });
}


function show_more_comment() {
    let show_more = $("<div class='col-lg-12 col-md-12 col-xs-12 col-sm-12' style='text-align: center'></div>");
    show_more.append($("<span href='#' style='cursor: pointer;color: #155faa;font-size: 14px;' id='show_more_comment'>" +
        "查看评论( " + article_comment_count +" ) </span>"));
    show_more.click(function () {
        load_comment_info();
    });
    $("#article_detail").append(show_more);
}



function load_article_info(articleInfo) {
    article_view_count = articleInfo.viewCount;
    article_like_count = articleInfo.likeCount;
    article_follow_count = articleInfo.followCount;
    article_comment_count = articleInfo.commentCount;
    const tags = articleInfo.tag.split(' ');
    let articleDetail = '<h3>' +
        '                   <span>' + articleInfo.title + '</span></h3>\n' +
        '                      <span class="aw-article-content">\n' +
        '                      <span>' + article_view_count + ' 次浏览 • </span>\n' +
        '                      <span>' + article_like_count + ' 人点赞 • </span>\n' +
        '                      <span>' + article_comment_count + ' 人评论 • </span>\n' +
        '                      <span> 发表于' + formatTimestamp(articleInfo.gmtCreate) + '</span>\n' +
        '                   </span><br>\n';

    $.each(tags,function (index, item) {
        const tagInfo =
            '<span class="label label-tag hover-point">\n' +
            '  <a href="/?tag=' +  item + '" target="_parent" style="color: #fff;cursor: pointer;text-decoration: none;">\n' +
            '      <i class="iconfont icon-biaoqian1"></i>\n' +
            '      <span>' + item + '</span>\n' +
            '  </a>\n' +
            '</span>\n';
        articleDetail += tagInfo;
    });
    articleDetail +=
        '<hr class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
        '<div id="markdown-text" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 markdown-body editormd-html-preview">' +
        '<textarea style="display: none">' + articleInfo.description +'</textarea>' +
        '</div>'+
        '<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12" id="option_item"></div>' +
        '    <hr class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
        '</div>';
    $("#article_detail").append(articleDetail);
    load_article_option(articleInfo.creator,articleInfo);
}
/**
 * 根据请求的url返回id值
 * @returns {string} id
 */
function getArticleId() {
    return document.location.pathname.replace("/article/", "");
}
function load_comment_info(){
    const url = "/loadComment";
    ajax_get(url,{"id":getArticleId()},function (data){
        let article = $("#article_comment");
        $("#show_more_comment").parent("div").hide();
        article.empty();
        let comments = data.extend.comments;
        let article_creator = data.extend.creator;
        let view = '<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
            '    <ul id="comments-list" class="comments-list fadein-0_5s">\n' +
            '    </ul>\n' +
            '</div>';
        article.append(view);
        $.each(comments,function (index,item) {
            load_comment_html(item,article_creator);
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
        '                            <a href="/people?user='+ byUser.id + '">'+ byUser.name + '</a>\n' +
        '                        </h6>\n' +
        '                        <span class="comment-post pull-right" style="top: 0;"> \n' +
        '                            <input name="commentId" value="' + comment.id + '" hidden>' +
        '                            <input name="creator" value=" ' + byUser.id +'" hidden>' +
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
            let toUser = item.commentToUser;
            html +=
                '                <li>\n' +
                '                    <!-- Avatar -->\n' +
                '                    <div class="comment-avatar"><img src="' + bySonUser.avatarUrl + '" alt=""></div>\n' +
                '                    <!-- Contenedor del Comentario -->\n' +
                '                    <div class="comment-box">\n' +
                '                        <div class="comment-head">\n' +
                '                            <h6 class="comment-name ' + (bySonUser.id==creator?'by-author':'') + '"><a href="/people?user='+ bySonUser.id + '">'+ bySonUser.name + '</a></h6>\n' +
                '                            <span class="comment-post pull-right">\n' +
                '                            <input name="commentId" value="' + bySonUser.id + '" hidden>' +
                '                            <input name="creator" value="' + bySonUser.id + '" hidden>' +
                '                            <span class="option hover-point"  style="margin-right: 25px;top: 0"><i class="iconfont icon-fenxiang"></i>评论</span>\n' +
                '                            </span>\n' +
                '                        </div>\n' +
                '                        <div class="comment-content"><a class="comment-reply" href="/people?user='+ toUser.id + '">@'+ toUser.name+': </a>'+ item.content +
                '                            <span>' + formatTimestamp(item.gmtCreate) + '</span>\n' +
                '                    </div></div>\n' +
                '                </li>\n';
        });
        html +=                 '        </ul>\n';
    $("#comments-list").append(html);
}
