$(function () {
    list_option();
});

function list_option() {
    $("#people_option").empty();
    let isFirst = 0;
    $.each(peopel_center_option, function (index, item) {
        debugger
        let li_optopn;
        if (isFirst === 0){
            isFirst++;
            li_optopn = '<li class="disabled active">\n' +
                '     <a id="'+ index + '">'+ item +'</a>\n' +
                '</li>';
        }else {
            li_optopn = '<li>\n' +
                '     <a id="'+ index + '">'+ item +'</a>\n' +
                '</li>';
        }
        $(document).on('click',"#"+index,function () {
            let index = this.id;
            eval(index)(index);
        });
        $("#people_option").append(li_optopn);
    });
}


var QUESTION = function () {
    $("#people_list").empty();



    let aa='<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12 bbs-question-list-item" <div="">\n' +
    '            <div class="col-lg-11 col-md-11 col-xs-11 col-sm-11">\n' +
    '                        <span>\n' +
    '                            <a href="/question/4" class="media-heading">爱仕达所</a>\n' +
    '                        </span>\n' +
    '                <br>\n' +
    '                <span class="aw-question-content">\n' +
    '                            <span>分类 • </span>\n' +
    '                            <span>0 次浏览・</span>\n' +
    '                            <span>0 人点赞・</span>\n' +
    '                            <span>0 人评论</span>\n' +
    '                            <span style="float:right">发表于 2019-11-3 9:31:25</span>\n' +
    '                        </span>\n' +
    '            </div>\n' +
    '        </div>'
};

var COMMENT = function (str) {
    alert(str)
};

var FOLLOW = function () {

};