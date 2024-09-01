$(document).on({
    ready : function() {
        let username = $("#username").val();
        let password = $("#password").val();
        console.log("username: " + username + "|Password: " + password);
        if(username == "" || password == "") {
            $("#submit-btn").prop("disabled", false);
        }
    }
})

$("#username").keyup(function() {
    let username = $("#username").val();

    let password = $("#password").val();

    if(username.trim() && password.trim()){
        $("#submit-btn").prop("disabled", false);
    } else {
        $("#submit-btn").prop("disabled", true);
    }
});

$("#password").keyup(function() {
    let username = $("#username").val();
    let password = $("#password").val();

    if(password.trim()){
        $("#show-pwd").show();
    } else {
        $("#show-pwd").hide();
    }
    if(username.trim() && password.trim()){
        $("#submit-btn").prop("disabled", false);
    } else {
        $("#submit-btn").prop("disabled", true);
    }
});

$("#show-pwd").click(function() {
    if($("#password").attr("type") == "password"){
        $("#password").attr("type", "text");
    } else {
        $("#password").attr("type", "password");
    }
})