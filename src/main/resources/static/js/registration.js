$(document).ready(function() {
    $('#register_btn').on('click', function () {

        var firstName = $('#register_first_name').val();
        var lastName = $('#register_last_name').val();
        var email = $('#register_email').val();
        var password = $('#register_password').val();
        var passwordRetype = $('#register_password').val();

        var user = new User(null, firstName, lastName, email, password, null);
        var userJson = JSON.stringify(user);


        if( validateEmail(email) && validatePassword(password) && password === passwordRetype){
            var user = new User(firstName, lastName, email, password);

            var url = 'http://localhost:8080/fakebook/registration/signup';
            $.ajax({
                url: url,
                data: userJson,
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                error: function(data) {
                    console.log("error: " + url);
                    return false;
                },
                success: function(data) {
                    if(data === true){
                        console.log('success: ' + url);
                        $.get("html/registration-email-alert.html", function (data) {
                            var wrapper= document.createElement('div');
                            wrapper.innerHTML = data;
                            var emailAlert = wrapper.firstChild;
                            var jQemailAlert = $(emailAlert);
                            $(jQemailAlert).find("strong#email_alert_strong").text(email);
                            $("#middle-body-space").empty();
                            $("#middle-body-space").append(jQemailAlert);
                            clearOnRegistration();
                        });
                    }
                    else{
                        $.get("html/registration-email-alert-failure.html", function (data) {
                            var wrapper= document.createElement('div');
                            wrapper.innerHTML = data;
                            var emailAlert = wrapper.firstChild;
                            var jQemailAlert = $(emailAlert);
                            $("#middle-body-space").empty();
                            $("#middle-body-space").append(jQemailAlert);
                            clearOnRegistration();
                        });
                    }
                    return false;
                }
            });
        }
        return false;
    });
});