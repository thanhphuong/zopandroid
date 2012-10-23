$(document).bind('deviceready', function() {
    $(function() {
        $('#loginForm').submit(function(){
            var uid = $('#login').val();
            var pwd = $('#password').val();
            $.getJSON('http://xxx.com/phonegap/login.php',
                { userid: uid, password: pwd },
                function(json) {
                    $.each(json, function(k, v) {
                        if(v == "true" || v == true){
                            window.location="page2.html";
                        } else {
                            $('#loginError').html("Login failed, username and/or password don't match");
                        }
                });
            });
            return false;
        });
    });
});

function submit1() {
    
    return false;

}

function dataRequest() {
    var output = $('#output').text('Loading data...');

    $.ajax({
        url: 'http://samcroft.co.uk/demos/updated-load-data-into-phonegap/landmarks.php',
        dataType: 'jsonp',
        jsonp: 'jsoncallback',
        timeout: 5000,
        success: function(data, status){
            output.empty();
            
            $.each(data, function(i,item){ 
                var landmark = '<h1>'+item.name+'</h1>'
                + '<p>'+item.latitude+'<br>'
                + item.longitude+'</p>';

                output.append(landmark);
            });
        },
        error: function(){
            output.text('There was an error loading the data.');
            navigator.notification.confirm(
                'Something went wrong. Would you like to retry?',
                yourCallback,
                'Error',
                'No,Yes'
            );
        }
    });
}
