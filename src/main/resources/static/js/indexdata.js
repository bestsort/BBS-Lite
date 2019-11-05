$(function () {
    $("#no_anything").css({
        display:"none",
    });
    $("#ajaxVal").children("input[name='jumpToUrl']")
        .val("/loadArticleList");
    to_artical_page(1);
});

function to_artical_page(to) {
    $("#ajaxVal").children("input[name='pageNo']").val(to);
    let jsonData = form_to_dic();
    let url = jsonData["jumpToUrl"];

    ajax_get(url,jsonData,function (data) {
        if(data.extend.page.total>0){
            //构建文章列表信息
            build_article_list(data.extend,"#main_list");
            //构建分页信息
            build_page_nav(data,to_artical_page);
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