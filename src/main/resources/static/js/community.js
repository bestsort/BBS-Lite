
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "pid":questionId,
            "content":content,
            "type":1
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
    console.log(questionId);
    console.log(content);
}