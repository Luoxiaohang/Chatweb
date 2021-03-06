function websocket() {

	if (!window.WebSocket && window.MozWebSocket)
		window.WebSocket = window.MozWebSocket;
	if (!window.WebSocket)
		alert("No Support");
	var ws;

	$(document).ready(function() {

		$("#sendbutton").attr("disabled", false);
		$("#sendbutton").click(sendMessage);

		startWebSocket();
	});

	function sendMessage() {
		var othername = $("#othername").val();
		var msg = "MSG\t" + username + "_" + othername + "_"
				+ $("#message").val();
		send(msg);
	}

	function send(data) {
		console.log("Send:" + data);
		ws.send(data);
	}

	function startWebSocket() {
		ws = new WebSocket("ws://127.0.0.1:8080/Chatweb/chatGroup");
		ws.onopen = function() {
			console.log("success open");
			$("#sendbutton").attr("disabled", false);
		};
		ws.onmessage = function(event) {
			console.log("RECEIVE:" + event.data);
			handleData(event.data);
		};
		ws.onclose = function(event) {
			console.log("Client notified socket has closed", event);
		};

	}

	function handleData(data) {
		var vals = data.split("\t");
		var msgType = vals[0];
		switch (msgType) {
		case "NAME":
			var msg = vals[1];
			var mes = "NAME" + "\t" + msg + "_" + username;
			send(mes);
			break;
		case "MSG":
			var val2s = vals[1].split("_");
			var from = val2s[0];
			var message = val2s[2];

			var texta = document.getElementsByTagName("textarea")[0];
			textarea.innerHTML = textarea.value + "\r\n" + from + ":" + message;
			break;
		default:
			break;

		}
	}
}