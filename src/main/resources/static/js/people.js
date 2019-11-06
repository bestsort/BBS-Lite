let default_option = undefined;
$(function () {
    let forms = $("#ajaxVal");
    forms.children("input[name='userId']").val(getParam("user"));
    $(document).on('click',"#people_option>li",function () {
        let id = $(this).children("a").attr("id");
        let fa = $("#"+id).parent();
        fa.parent().children().removeClass("disabled");
        fa.parent().children().removeClass("active");
        fa.addClass("disabled");
        fa.addClass("active");
    });
    $.each(peopel_center_option, function (index, item) {
        if (index !== "PRIVATE") {
            add_to_option(index, item);
        }
    });
    forms.children("input[name='jumpToUrl']").val(request_url[default_option]);
    to_people_page(1);
    ajax_get("/getUserInfo",{id:getParam("user")},
        function (data) {
            let isOwn = data.extend.isOwn;
            load_right_with_data(data.extend);
            if(isOwn){
                let item = peopel_center_option["PRIVATE"]
                debugger
                $.each(item,function (index,item) {
                    add_to_option(index,item,isOwn);
                })
            }
    });
});

function add_to_option(index,item,isOwn){
    let li_optopn;
    if (default_option === undefined){
        default_option = index;
        li_optopn = '<li class="disabled active">\n' +
            '     <a id="'+ index + '">' + item +'</a>\n' +
            '</li>';
    }else {
        li_optopn = '<li>\n' +
            '     <a id="'+ index + '">' + item +'</a>\n' +
            '</li>';
    }
    $(document).on('click',"#"+index,function () {
        document.title = item;
        let index = this.id;
        $("#ajaxVal").children("input[name='jumpToUrl']").val(request_url[index]);
        to_people_page(1);
    });
    document.title = peopel_center_option[default_option];
    $("#people_option").append(li_optopn);
    return default_option;
}

let ARTICLE = function (data) {
    let articles = data.page.list;
    $.each(articles, function (index, item) {
        let article = $('<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12 bbs-article-list-item"' +
            '        <div class="media media-margin" >\n' +
            /*                '            <!-- 头像 -->\n' +\
                        '            <div class="media-left media-left-margin">\n' +
                        '                <a href="#">\n' +
                        '                    <img class="media-min img-rounded" src=' + item.userAvatarUrl + '>\n' +
                        '                </a>\n' +
                        '            </div>\n' +*/
            '            <!-- 描述 -->\n' +

            '<a href="/article/' + item.id + '" style="text-decoration:none">'+
            '            <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">\n' +
            '                        <span>\n' +
            '                            <span class="media-heading-deep font-bigger">' + item.title + '</span>\n' +
            '                        </span>\n' +
            '                <p class="media-heading article-descrption">' + item.description.substring(0,300) + '</p>' +
            '                <span class="aw-article-content">\n' +
            '                            <span>' + item.viewCount + '次浏览 • </span>\n' +
            '                            <span>' + item.followCount + '人收藏 • </span>\n' +
            //'                            <span>' + item.commentCount + '个回复 • ></span>\n' +
            '                            <span>' + item.likeCount + '人点赞 • </span>\n' +
            '                            <span>' + item.commentCount + '人评论</span>\n' +
            '                            <span style="float:right">发表于 ' + formatTimestamp(item.gmtCreate) + '</span>\n' +
            '                        </span>\n' +
            '            </div></a>\n' +
            '        </div>');
        $("#main_list").append(article);
    });
};

let FOLLOW_ARTICLE = function (data) {
    if(data.page.total>0){
        //构建文章列表信息
        build_article_list(data,"#main_list");
        //构建分页信息
    }else{
        $('.page_info-area').empty();
        $(".pagination").empty();
        $("#no_anything").css({
            display:"block",
        });
    }
};

var COMMENT = function (data) {
    let comments = data.page.list;
    $.each(comments, function (index, item) {
        let article =
            '   <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12 media media-margin" style="border-bottom: 1px solid #eee">' +
            '       <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">' +
            '           <a href="/article/' + item.commentToId + '" style="text-decoration:none">' +
            '               <div class="media-heading article-descrption">' +
            '                   <span style="font-size: 16px;">评论了文章</span>' +
            '                   <span class="media-heading-deep" style="font-size:16px;font-weight: 700;">' + item.commentToTitle + ':</span>' +
            '                   <span style="float:right;margin-top: 3px">' + formatTimestamp(item.commentTime) + '</span>' +
            '                   <p>' +item.commentContent.substring(0,70) + '</p>' +
            '               </div>' +
            '           </a>' +
            '       </div>' +
            '   </div>';
        $("#main_list").append(article);
    });
};

var FOLLOW = function () {
    alert(un_complete)
};
var FANS = function () {
    alert(un_complete);
};

function to_people_page(to) {
    $("#ajaxVal").children("input[name='pageNo']").val(to);
    let jsonData = form_to_dic();
    let url = jsonData["jumpToUrl"];
    ajax_get(url,jsonData,function (data) {
        $("#main_list").empty();
        if(data.extend.page.total>0){
            $("#no_anything").css({display:"none"});
            //构建列表信息
            eval(data.extend.func)(data.extend);
            //构建分页信息
            build_page_nav(data,eval(to_people_page));
        }else{
            $('.page_info-area').empty();
            $(".pagination").empty();
            $("#no_anything").css({
                display:"block",
            });
        }
    });
}