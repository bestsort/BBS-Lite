
$(function () {
    $(document).on('click', "#github-login", function () {
        location.href="/github-login";
    });
    $(document).on('click', "#sign-up-link", function () {
        $("#sign-up-link-modal").modal();
    });
    $(document).on('click', "#send-sign-up-button", function () {
        let dom =$("#sign-up-form");
        if (dom.valid()) {
            let values = dom.serializeArray();
            let data = {
            };
            for (let index in values) {
                if (values[index].name == "sign_confirm_password")
                    continue;
                data[values[index].name.substring(5)] = values[index].value;
            }
            debugger
            $.ajax({
                type:"POST",
                url:"/sign-up",
                data:data,
                beforeSend:open_loading(),
                success:function (data) {
                    if(data.code == 200){
                        success_prompt("注册成功,请前往您的邮箱激活账户",3000);
                        setTimeout(function () {
                            location.reload();
                        },3500)
                    }
                    else {
                        fail_prompt(data.message);
                    }
                },
                complete:close_loading()
            })
        }
    });
});

/**
 * 表单校验
 **/

$(function(){
    $("#sign-up-form").validate({
        errorPlacement:function(error,element) {
            fail_prompt(error,1500);
        },
        onfocusout:false,
        onkeyup:false,
        onclick:false,
        rules:{
            sign_password:{
                required:true,
                rangelength:[6,18]
            },
            sign_confirm_password:{
                required: true,
                equalTo: "input[name='sign_password']"
            },
            sign_email:{
                required:true,
                email: true
            },
            sign_account:{
                required:true,
                rangelength:[6,12]
            }
        },
        messages:{
            sign_account: {
                required:"账户不能为空",
                rangelength:"账号长度在6-12之间"
            },
            sign_password: {
                required:"密码不可为空",
                rangelength:"密码长度在6-18之间"
            },
            sign_confirm_password: {
                required:"重复密码不可为空",
                equalTo: "两次输入密码不一致"
            },
            sign_email:{
                required:"邮件地址不可为空",
                email:"邮件地址有误,请检查"
            }
        }
    });
});