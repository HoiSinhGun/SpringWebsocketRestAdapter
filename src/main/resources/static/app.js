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
}


function showSingleResponse(message) {
    var msg_body = JSON.parse(message.body);
    console.log(msg_body);
    msg_obj = msg_body.payload;
    $("#msgs").append("<tr><td style='width:50px'>" + msg_body.requestId + "</td><td style='width:50px'>" + msg_obj.userId + "</td><td>" + msg_obj.data + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMsg(); });
});

