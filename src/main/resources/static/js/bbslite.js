
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
