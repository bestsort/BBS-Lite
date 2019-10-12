var E = window.wangEditor;
var editor = new E("#wangEditorToolbar","#wangEditorText");

$(function () {

    editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'fontName',  // 字体
        'italic',  // 斜体
        'underline',  // 下划线
        'strikeThrough',  // 删除线
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'link',  // 插入链接
        'list',  // 列表
        'justify',  // 对齐方式
        'quote',  // 引用
        'emoticon',  // 表情
        'image',  // 插入图片
        'table',  // 表格
        'code',  // 插入代码
        'redo'  // 重复
    ];
    editor.customConfig.uploadImgShowBase64 = true;   // 使用 base64 保存图片
    editor.customConfig.zIndex = 100;
    editor.create();
    // 加载问题详情
    function load_topic_option() {
        let id = getParam("id");
        let jsondata = {};
        if(id != null)
            jsondata["id"] = id;
        $.ajax({
            url: "/getPublishInfo",
            type: "GET",
            data:jsondata,
            success: function (data) {
                let question =  data.extend.publishInfo.question;
                if(question != null) {
                    $("#title").val(question.title);
                    $("#tag").val(question.tag);
                    editor.txt.html('<p>' + question.description + '</p>');
                }
                show_topic_option(data);
            },
        });
    }
    $("#push").click(function () {
        let json_data = {
            "description":editor.txt.html(),
            "tag":$("#tag").val(),
            "title":$("#title").val(),
            "topic":$("#topicType").val()
        };
        let juge = jude_not_null(json_data);
        if(juge.length === 0){
            push_question(json_data);
        }else{
            $("#myModal").modal('show');
        }
    });
    load_topic_option();
});
function push_question(json_data){
    success_prompt("发布成功");
    $.ajax({
        type: "GET",
        url: "/pushQuestion",
        data: json_data,
        success: function (data) {
            if(data.code == "200"){
                success_prompt("发布成功");
            }
            else{
                success_prompt(data.message);
            }
        }
    })
}

function show_topic_option(data) {
    let question = data.extend.publishInfo.question;
    let selectTopic = null;
    if(question != null){
        selectTopic = question.topic;
    }
    if(selectTopic == null)
        selectTopic = 0;
    let topicInfos = data.extend.publishInfo.topics;
    debugger
    let html = "";
    $.each(topicInfos,function (index,item) {
        if (item.id == selectTopic) {
            html += '<option value="' + item.id + '" selected>' + item.name + '</option>'
        } else {
            html += ('<option value="' + item.id + '">' + item.name + '</option>');
        }
    });
    $("#topicType").append(html);
}

function jude_not_null(json_data) {
    let result = [];
    for(let key in json_data){
        if (json_data[key] === "") {
            result.push(key);
        }
    }
    return result;
}