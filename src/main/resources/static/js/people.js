$(function () {
    list_option();
    load_right_without_data();
});

function list_option() {
    $("#people_option").empty();
    let default_option = undefined;
    $.each(peopel_center_option, function (index, item) {
        let li_optopn;
        if (default_option === undefined){
            default_option = index;
            li_optopn = '<li class="disabled active">\n' +
                '     <a id="'+ index + '">'+ item +'</a>\n' +
                '</li>';
        }else {
            li_optopn = '<li>\n' +
                '     <a id="'+ index + '">'+ item +'</a>\n' +
                '</li>';
        }
        $(document).on('click',"#"+index,function () {
            document.title = item;
            let index = this.id;
            eval(index)(index);
        });
        $("#people_option").append(li_optopn);
    });
    document.title = peopel_center_option[default_option];
    eval(default_option)(default_option);
}


let ARTICLE = function () {
    $("#people_list").empty();
    ajax_get("/loadArticleList",{
        userId:getParam("user")
    },
    function (data) {
    //清空
        $("#people_list").empty();
        const articles = data.extend.page.list;
        $.each(articles, function (index, item) {
            const article = $('<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12 bbs-article-list-item"' +
                '        <div class="media media-margin" >\n' +
    /*                '            <!-- 头像 -->\n' +
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
                '                <p class="media-heading article-descrption">' + item.description.substring(1,50) + '</p>' +
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
            $("#people_list").append(article);
        })
    });
};

var COMMENT = function (str) {
    alert(un_complete)
};

var FOLLOW = function () {
    alert(un_complete)
};
var FANS = function () {
    alert(un_complete);
};