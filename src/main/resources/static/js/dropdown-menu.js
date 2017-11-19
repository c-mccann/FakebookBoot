$(document).ready(function(){
        $('.dropdown-menu').click(function(e) {
            e.stopPropagation();
        });


    $("#login_li").click(function(){
        $(".login-hides").toggle();
        $(".register-hides").hide();
    });


    $("#register_li").click(function(){
        $(".register-hides").toggle();
        $(".login-hides").hide();
    });


    $("#logout_li").click(function(){
        $(".logout-hides").toggle();
    });


    $("#login_btn").click(function () {
        var email = $("#login_email").val();
        var password = $("#login_password").val();
        var url = 'http://localhost:8080/fakebook/users/' + email;
        var validEmail = validateEmail(email);
        var validPassword = validatePassword(password);
        console.log("validEmail: " + validEmail);
        console.log("validPassword: " + validPassword);
        if( validEmail && validPassword && password){
            $.ajax({
                url: url,
                dataType: 'json',
                type: 'GET',
                error: function() {
                    console.log("error: " + url);
                    $.get("html/login-error-alert.html", function (data) {
                        var wrapper = document.createElement('div');
                        wrapper.innerHTML = data;
                        var alert = wrapper.firstChild;
                        $("#middle-body-space").empty();
                        $("#middle-body-space").append(alert);
                        console.log("added login error alert");
                    });
                },

                success: function(data) {
                    var jsonString = JSON.stringify(data);
                    var user = JSON.parse(jsonString);
                    console.log("success: " + url);
                    console.log("\t" + jsonString);
                    if(user.accountCreated === null){
                        $.get("html/unactivated-account-login-alert.html", function (data) {
                            var wrapper = document.createElement('div');
                            wrapper.innerHTML = data;
                            var alert = wrapper.firstChild;
                            $("#middle-body-space").empty();
                            $("#middle-body-space").append(alert);
                            console.log("added unactivated account login alert");
                        });
                    }

                    if(password === user.password && user.accountCreated !== null){
                        loginSuccess(jsonString);
                    }
                }
            });
        }
        return false; // stop refresh
    });

    $("#login_password").on("input", function(){
        var val = $("#login_password").val();
        if(validatePassword(val)){
            $("#login_password").css({"background-color": "#adec98"});
        }
        else{
            $("#login_password").css({"background-color": "#ec98ad"});
        }
    });


    $("#register_email").on("input", function(){
        var val = $("#register_email").val();
        if(validateEmail(val)){
            $("#register_email").css({"background-color": "#adec98"});
        }
        else{
            $("#register_email").css({"background-color": "#ec98ad"});
        }
    });
    $("#register_password").on("input", function(){
        var val = $("#register_password").val();
        if(validatePassword(val)){
            $("#register_password").css({"background-color": "#adec98"});
        }
        else{
            $("#register_password").css({"background-color": "#ec98ad"});
        }
    });
    $("#register_password_retype").on("input", function(){
        var val = $("#register_password").val();
        var val2 = $("#register_password_retype").val();
        if(val === val2){
            $("#register_password_retype").css({"background-color": "#adec98"});
        }
        else{
            $("#register_password_retype").css({"background-color": "#ec98ad"});
        }
    });


    $("#logout_btn").click(function () {
        var url = 'http://localhost:8080/fakebook/logout';

        $.ajax({
            url: url,
            type: 'GET',
            error: function() {
                console.log("error: " + url);
            },
            success: function(data) {
                if(JSON.stringify(data) === 'true'){
                    console.log("success: " + url);
                    settingsMenuLoadOnLogout();
                    loadPageOnLogout();

                }
                else{
                    console.log("error in controller: " + url);
                }
            }
        });
        unloadSearchBar();
        unloadProfileSidebar();
        return false; // stop refresh
    });
});


function loadPageOnLogout() {
    $("#middle-body-space").empty();
    $.get("html/fakebook-header.html", function (data) {
        var wrapper = document.createElement('div');
        wrapper.innerHTML = data;
        fakebookHeader = wrapper.firstChild;
        $("#left-header-space").empty();
        $("#left-header-space").append(fakebookHeader);
    });
}
// function hideSettingsDropdown(){
//     $("#no_session_dropdown_list").hide();
// }

function clearOnRegistration(){
    $("#register_first_name").val("");
    $("#register_last_name").val("");
    $("#register_email").val("");
    $("#register_password").val("");
    $("#register_password_retype").val("");
    $("#register_li").trigger('click');

}