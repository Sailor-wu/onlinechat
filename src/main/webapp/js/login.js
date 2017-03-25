// 定义常量
const OK = 200;
const PHONE_ALREADY_EXIST = 601;
const ALREADY_LOGIN = 609;
var bgImgs = ['1.jpg', '2.jpg', '3.jpg'];
var index = 1;
setInterval(function () {
    if (index > 2) {
        index = 0;
    }
    var bgImg = bgImgs[index++];
    var background = "url(../img/" + bgImg + ")";
    $("body").css({"background-image": background});
}, 3000);

$("#register").on("click", function () {
    layer.open({
        type: 1,
        title: '用户注册',
        shadeClose: false,
        shade: 0.3,
        area: ['400px', '370px'],
        content: $("#reg-dlg"),
        btn: ['立即注册'],
        scrollbar: false,
        btn1: function () {
            if (checkRegInfo()) {
                var index = layer.load(2);
                $.post("/user/register", {
                    "nickname": $("#nickname").val().trim(),
                    "phone": $("#phone").val().trim(),
                    "pwd": $("#password").val().trim()
                }, function (result) {
                    layer.close(index);
                    if (result.code === OK) {
                        var msg = result.result.nickname + "恭喜您注册成功。<br>请记住您的账号，您的账号是:" + result.result.account + " 稍后使用该账号进行登录。";
                        layer.alert(msg, {
                            icon: 1,
                            title: '注册成功',
                            closeBtn: 0
                        }, function () {
                            layer.closeAll();
                            clearInfo();
                        });
                    } else if (result.code === PHONE_ALREADY_EXIST) {
                        layer.msg('该手机号码已经被注册了哦，请换一个试试吧', {icon: 5}, clearInfo);
                    } else {
                        layer.msg('注册失败了。。请稍后重试吧', {icon: 5}, clearInfo);
                    }
                }, "json");
            }
        }
    });
});

function clearInfo() {
    $("#nickname").val("");
    $("#phone").val("");
    $("#password").val("");
    $("#confirm").val("");
}

function checkRegInfo() {
    if ($("#nickname").val().trim() == "") {
        layer.tips('昵称不能为空', '#nickname');
        return false;
    }
    var phone = $("#phone").val().trim();
    if (phone == "") {
        layer.tips('手机号码不能为空', '#phone');
        return false;
    }
    if (!(/^1[34578]\d{9}$/.test(phone))) {
        layer.tips('手机号码格式不正确', '#phone');
        return false;
    }
    var password = $("#password").val().trim();
    if (password == "") {
        layer.tips('密码不能为空', '#password');
        return false;
    }
    var confirm = $("#confirm").val().trim();
    if (confirm == "") {
        layer.tips('请再次确认密码', '#confirm');
        return false;
    }
    if (password != confirm) {
        layer.msg('两次输入密码不匹配，请重新输入');
        return false;
    }
    return true;
}

$("#login-btn").on("click", function () {
    var acct = $("#acct").val().trim();
    var pwd = $("#pwd").val().trim();

    if (acct == "") {
        layer.tips('账号不为空', '#acct');
        return false;
    } else if (pwd == "") {
        layer.tips('密码不能为空', '#pwd');
        return false;
    } else {
        $.post("/user/login", {"acct": acct, "pwd": pwd}, function (result) {
            if (result.code === OK) {
                window.location = "/";
            } else if (result.code === ALREADY_LOGIN) {
                layer.alert("该账户已在其他地方登录，请核实是否本人操作，如非本人操作，请尽快更改密码以确保账户安全", {icon: 2, title: "登录异常"});
            } else {
                $(".error-msg").show();
            }
        }, "json");
    }
});
