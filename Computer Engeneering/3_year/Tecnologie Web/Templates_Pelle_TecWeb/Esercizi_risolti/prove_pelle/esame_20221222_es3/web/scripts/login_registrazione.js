$("#username").keyup(function() {
    let email = $("#username").val();
    let password = $("#password").val();

    console.log("Username: " + email + "\tPassword: " + password);
    if(email.trim() && password.trim()){
        $("#submit-btn").prop("disabled", false);
    } else {
        $("#submit-btn").prop("disabled", true);
    }
});

$("#password").keyup(function() {
    let email = $("#username").val();
    let password = $("#password").val();

    console.log("Username: " + email + "\tPassword: " + password);
    if(password.trim()){
        $("#show-pwd").show();
    } else {
        $("#show-pwd").hide();
    }
    if(email.trim() && password.trim()){
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