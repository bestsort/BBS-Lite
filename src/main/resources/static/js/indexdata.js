$(function () {
    $("#no_anything").css({
        display:"none",
    });
    $("#ajaxVal").children("input[name='jumpToUrl']")
        .val("/loadArticleList");
    to_article_page(1);
});

function to_article_page(to) {
    if (to === undefined)
        to = 1;
    $("#ajaxVal").children("input[name='pageNo']").val(to);
    let jsonData = form_to_dic();
    let url = jsonData["jumpToUrl"];
    debugger
    if (jsonData["pageSize"] === undefined)
        jsonData["pageSize"] = 10;
    ajax_get(url,jsonData,function (data) {
        if(data.extend.page.total>0){
            //构建文章列表信息
            build_article_list(data.extend,"#main_list");
            //构建分页信息
            build_page_nav(data,to_article_page);
        }else{
            $("#main_list").empty();
            $('.page_info-area').empty();
            $(".pagination").empty();
            $("#no_anything").css({
                display:"block",
            });
        }
    });
}