$(function () {
    $.ajax({
        url:location.href,
        type: "POST",
        data: {
            contentType: "application/json;charset=UTF-8"
        },
        success:function (data) {
            if(data.code === 200) {
                success_prompt("激活成功");
                setTimeout(function () {
                    location.href = "/";
                },1500);
            }
            else{
                fail_prompt(data.message);
                setTimeout(function () {
                    location.href = "/";
                },1200)
            }
        }
    })
});