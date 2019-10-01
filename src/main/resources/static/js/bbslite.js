
function search(e) {
    var str = $("#tag").val();
    alert(str);

}
/* 提交一级回复 */
function pComment(e) {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    var reg = /^\s*$/;
    if(content != null && !reg.test(content)){
        $.ajax({
            type:"POST",
            url:"/comment",
            contentType:'application/json',
            data:JSON.stringify({
                "pid":1,
                "content":content,
                "level":1,
                "questionId":questionId
            }),
            success:function (response) {
                if(response.code === 200){
                    $("#comment_section").hide();
                }
                else{
                    if(response.code === 4){
                        var isAccepted = confirm(response.message);
                        if(isAccepted) {
                            window.open("https://github.com/login/oauth/authorize?client_id=08b076ae3cf904cf324c&redirect_uri=http://localhost:8080/callback&scope=user&state=1&&allow_signup=true");
                            window.localStorage.setItem("closable","true");
                        }
                    }
                    else {
                        alert(response.message);
                    }
                }
                console.log(response);
            },
            dataType:"json"
        });
    }
    else {
        //TODO 回复内容为空的时候
        alert("回复内容不可为空");

    }
}



function comment() {
}

function hideoptions(e) {
    const id = "reply-" + e.getAttribute("data-id");
    document.getElementById(id).style.display="none";
}
function showoptions(e) {
    const id = "reply-" + e.getAttribute("data-id");
    document.getElementById(id).style.display="inline-block";
}
/* 评论的展开与折叠 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    // 折叠评论
    if(comments.hasClass("in")) {
        comments.removeClass("in");
        e.classList.remove("active");
    }
    // 展开评论
    else{
        comments.addClass("in");
        e.classList.add("active");
    }
}

function thumb_up_question() {
    var question_id = $("#thumb_up_to").val();
    var user_id = $("#thumb_up_by").val();
    var is_ghost = false;
    if(user_id === "" || user_id ==null) {
        is_ghost = true;
    }
    $.ajax({
        type:"POST",
        url:"/question/{question_id}",
        async:"true",
        success:function (response) {
            if(response.code === 200){
                $("#comment_section").hide();
            }
            else{
                alert("提交失败");
            }
        },
        contentType:'application/json',
        data:JSON.stringify({
            "isGhost":is_ghost,
            "thumbUpBy":user_id,
            "thumbUpTo":question_id,
            "type": 1
        }),
        dataType: "json"
    });

    debugger;
}

$('form').submit(function (event) {
    event.preventDefault();
    var form = $(this);

    if (!form.hasClass('fupload')) {
        //普通表单

        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: form.stringify(),
        }).success(function () {
            //成功提交
        }).fail(function (jqXHR, textStatus, errorThrown) {
            //错误信息
        });
        debugger;
    }
    else {
        // mulitipart form,如文件上传类
        var formData = new FormData(this);
        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: formData,
            mimeType: "multipart/form-data",
            contentType: false,
            cache: false,
            processData: false
        }).success(function () {
            //成功提交
        }).fail(function (jqXHR, textStatus, errorThrown) {
            //错误信息
        });
    };
});

$('.collapse').collapse();