
$(function () {
    //总记录数
    var totalpageo;
    //当前页
    var currentpage;
    //到第几页,默认到第一页
    to_page(1);
    //加载右边的数据
    //build_right_list();
});

/**
 * @description 到问题页第几页
 * @param pageno
 */
function to_page(pageno) {
    $("#no_quetions").css({
        display:"none",
    });
    /**
     * 加载完成之后,发送请求到服务器,拿到json数据,构建列表数据
     */
    var url = "/loadQuestionList";
    $.ajax({
        type: "GET",
        url: url,
        data: {
            "pageNo": pageno,
            "tag": $("#tag_param").val(),
            "search": $("#search_param").val(),
            "sortby": $("#sortby_param").attr("sortby"),
            "category": $("#category_param").val(),
            contentType: "application/json;charset=UTF-8"
        },
        beforeSend: function () {
            //loadingIndex = layer.msg('加载数据~~', {icon: 16});
        },
        success: function (data) {
            if (data.code == "200") {
                if(data.extend.page.total>0){
                    //构建问题列表信息
                    build_question_list(data);
                    //构建分页信息
                    //build_page_nav(data);
                }else{
                    $("#question_wrapper").empty();
                    $('.page_info-area').empty();
                    $(".pagination").empty();
                    $("#no_quetions").css({
                        display:"block",
                    });
                }
                $("html,body").animate({scrollTop: 0}, 0);//回到顶端
            } else {
                layer.msg(data.extend.msg, {time: 2000, icon: 5, shift: 6}, function () {
                });
            }
        },
    });

}


/**
 * @description 构建问题列表
 * @param data
 */
function build_question_list(data) {
    //清空
    $("#question_wrapper").empty();
    var questions = data.extend.page.list;
    $.each(questions, function (index, item) {

        var question = $('<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12"' +
            '        <div class="media media-margin" >\n' +
            '            <!-- 头像 -->\n' +
            '            <div class="media-left media-left-margin">\n' +
            '                <a href="#">\n' +
            '                    <img class="media-min img-rounded" src='+ item.userAvatarUrl + '>\n' +
            '                </a>\n' +
            '            </div>\n' +
            '            <!-- 描述 -->\n' +
            '            <div class="col-lg-11 col-md-11 col-xs-11 col-sm-11">\n' +
            '                        <span>\n' +
            '                            <a href="/question/'+ item.id+ '" class="media-heading">'+ item.title +'</a>\n' +
            '                        </span>\n' +
            '                <br>\n' +
            '                <span class="aw-question-content">\n' +
            '                            <span>'+ item.userName + ' • </span>\n' +
            '                            <span>'+ item.viewCount + '次浏览 • </span>\n' +
            //'                            <span>' + item.commentCount + '个回复 • ></span>\n' +
            '                            <span>' + item.likeCount + '人点赞</span>\n' +
            '                            <span style="float:right">发表于 ' + formatTimestamp(item.gmtCreate) + '</span>\n' +
            '                        </span>\n' +
            '            </div>\n' +
            '        </div>');
        $("#question_wrapper").append(question);
    })
}