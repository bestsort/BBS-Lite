
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
 * 构建分页导航
 * @param data
 */
function build_page_nav(data) {
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
    var nav = $(".pagination");
    var firstLi = $("<li class='page-item'></li>")
        .append($("<a class='page-link'>首页</a>")
        .attr("href", "#"));

    var prli = $("<li class='page-item'></li>")
        .append($("<a class='page-link' aria-label='Previous'><span aria-hidden='true'>上一页</span></a>")
        .attr("href", "#"));
    //首页
    firstLi.click(function () {
        to_page(1);
    });
    //上一页
    prli.click(function () {
        var target = page.pageNum - 1;
        target = target == 0 ? 1 : target;
        to_page(target);
    });
    var lastLi = $("<li class='page-item'></li>")
        .append($("<a class='page-link'>尾页</a>").attr("href", "#"));
    var nextli = $("<li class='page-item'></li>")
        .append($("<a class='page-link' aria-label='Next'><span aria-hidden='true'>下一页</span></a>").attr("href", "#"));
    //末页
    lastLi.click(function () {
        //alert("转到:"+page.pages)
        to_page(page.pages);
    });
    //下一页
    nextli.click(function () {
        var target = page.pageNum + 1;
        target = target < page.pages ? target : page.pages;
        to_page(target);
    });

    if(!page.isFirstPage){
       nav.append(firstLi);
    }
    if (page.hasPreviousPage) {
        nav.append(prli);
    }

    $.each(data.extend.page.navigatepageNums, function (index, item) {
        var li = $("<li class='page-item'></li>");
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
            to_page(item);
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
    var sortBy = getParam("sortBy");
    var jsonData = {};
    jsonData["pageNo"] = pageno;
    jsonData["contentType"] = "application/json;charset=UTF-8";
    var array = ["search","tag","sortBy","category","topic"];
    for (let i in array) {
        var val = getParam(array[i]);
        if(typeof(val) != 'undefined'){
            jsonData[array[i]] = val;
        }
    }
    $.ajax({
        type: "GET",
        url: url,
        data: jsonData
        /*{
            "pageNo": pageno,
            "tag": getParam("tag"),
            "search": getParam("search"),
            "sortby": getParam("sortBy"),
            "category": getParam("category"),
            contentType: "application/json;charset=UTF-8"
        }*/,
        beforeSend: function () {
            //loadingIndex = layer.msg('加载数据~~', {icon: 16});
        },
        success: function (data) {
            if (data.code == "200") {
                if(data.extend.page.total>0){
                    //构建问题列表信息
                    build_question_list(data);
                    //构建分页信息
                    build_page_nav(data);
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

        const question = $('<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12 bbs-question-list-item"' +
            '        <div class="media media-margin" >\n' +
            '            <!-- 头像 -->\n' +
            '            <div class="media-left media-left-margin">\n' +
            '                <a href="#">\n' +
            '                    <img class="media-min img-rounded" src=' + item.userAvatarUrl + '>\n' +
            '                </a>\n' +
            '            </div>\n' +
            '            <!-- 描述 -->\n' +
            '            <div class="col-lg-11 col-md-11 col-xs-11 col-sm-11">\n' +
            '                        <span>\n' +
            '                            <a href="/question/' + item.id + '" class="media-heading">' + item.title + '</a>\n' +
            '                        </span>\n' +
            '                <br>\n' +
            '                <span class="aw-question-content">\n' +
            '                            <span>' + item.userName + ' • </span>\n' +
            '                            <span>' + item.viewCount + '次浏览 • </span>\n' +
            //'                            <span>' + item.commentCount + '个回复 • ></span>\n' +
            '                            <span>' + item.likeCount + '人点赞</span>\n' +
            '                            <span style="float:right">发表于 ' + formatTimestamp(item.gmtCreate) + '</span>\n' +
            '                        </span>\n' +
            '            </div>\n' +
            '        </div>');
        $("#question_wrapper").append(question);
    })
}