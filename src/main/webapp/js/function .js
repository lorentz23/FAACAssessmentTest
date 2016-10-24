/**
 * JS file that call login JAX-RS and JAX-WS
 */

function login() {
    $.ajax({
    	type: "POST",
        url: "localhost/FAACAssessmentTest/rest/login",
        data: {username: document.login-form.username, password: document.login-form.password},
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    });
};

/*$('.message a').click(function(){
	   $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
});*/