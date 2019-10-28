$(function () {
    ajax_post(location.href,{},function () {
        success_prompt("激活成功");
        setTimeout(function () {location.href = "/"},1500);
        },
        function (data) {
            fail_prompt(data.message);
            setTimeout(function () {
                location.href = "/";
            },1200)
        });
});