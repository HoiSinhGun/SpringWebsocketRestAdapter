var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#msgs").html("");
}

function connect() {
    var socket = new SockJS('/sock_ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/users/queue/bridgemsg', function (singleResponse) {
                    showSingleResponse(singleResponse);
        });
    });
}

function uuidv4() {
  return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
    (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
  );
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMsg() {
    send()
}

function send() {
//    var chat_id = $("#chatSelect").val();
    var innerData = {
        'toUiAdapterId': 1,
        'data': $("#msg_data").val()
    };
    var jsonStringInner = JSON.stringify(innerData);
    var data = {
        'requestId': uuidv4(),
        'paramMap':
            {
                'data': jsonStringInner
            }
        };
    var endpoint = "/socket/bridge/msgs/create";
    var jsonString = JSON.stringify(data);
    console.log(jsonStringInner);
    console.log(data);
    console.log(jsonString);
    stompClient.send(endpoint, {}, jsonString);


    async_call(data).done(function(){
        // function1 is done, we can now call function2
        console.log('async_call is done!');
        console.log("finished send")
    });
}

function async_call(data){
     var dfrd1 = $.Deferred();
     setTimeout(function(){
         // doing async stuff
           // doing async stuff
         stompClient.subscribe("/socket/bridge/request/" + data.requestId + "/response", function (singleResponse) {
                             console.log('Subscribing for: ' + data.requestId + ': ' + singleResponse)
                             showSingleResponse(singleResponse, dfrd1);
                 });
         console.log('showSingleResponse is done!');
     }, 2000);
     promise = dfrd1.promise();
     console.log('Promise: ' + promise);
     return promise;
 }



function showSingleResponse(message, dfrd1) {
    var msg_body = JSON.parse(message.body);
    console.log('msg_body: ' + msg_body);
    msg_obj = msg_body.payload;
    $("#msgs").append("<tr><td style='width:50px'>" + msg_body.requestId + "</td><td style='width:50px'>" + msg_obj.userId + "</td><td>" + msg_obj.data + "</td></tr>");
    dfrd1.resolve();
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMsg(); });
});

