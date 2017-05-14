<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!--  
<script type="text/javascript">
	function sendMessage(element) {
		var p = element.parentNode;
		var e = p.getElementsByTagName("span")[0];
		e.innerHTML = "<input type='text' value='请输入消息' name='Message'>"
	}
</script>
-->
</head>
<body>
	<div>
		<table width="100%">
			<tr>
				<td align="left" valign="top"><c:forEach
						items="${requestScope.friends}" var="friend">
						<div>
							<span><c:out value='${friend}'></c:out></span> <br> <input
								type='text' value='请输入消息' name='Message' id="Message"> <input
								type="button" value="发消息" onclick="sendMessage(this)"> <br>
						</div>
					</c:forEach></td>
				<td align="right"><textarea id='textarea' rows="30" cols="50"
						wrap="hard"></textarea></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		function sendMessage(e) {
			var pn = e.parentNode;
			var message = pn.getElementsByTagName("input")[0].value;
			var friendName = pn.getElementsByTagName("span")[0].innerHTML;
            var content = "你对" + friendName + "说: " + message
			
			var ws = new WebSocket(
					"ws://${pageContext.request.contextPath}/ChatServlet");
			
			ws.onopen = function() {
				console.log("open");
				ws.send(content);
			}
			
			var textarea = document.getElementById("textarea");
			textarea.innerHTML = textarea.value + "\r\n" + content;

			ws.onmessage = function(evt) {
				console.log(evt.data);

			}
			ws.onclose = function() {
				console.log("websocketclosed");
			}
			ws.onerror = function() {
				console.log("websocketerrored");
			}
		}
	</script>
	<!--  
	<script type="text/javascript">
		function sendMessage(e) {
			var pn = e.parentNode;
			var message = pn.getElementsByTagName("input")[0].value;
			var friendName = pn.getElementsByTagName("span")[0].innerHTML;
			var ajax = new XMLHttpRequest();
			var url = "${pageContext.request.contextPath}/ChatServlet";
			var method = "POST";
			ajax.open(method, url, true);
			ajax.setRequestHeader("content-type",
					"application/x-www-form-urlencoded");
			var content = "Message=" + "你对" + friendName + "说: " + message
					+ ";";
			ajax.send(content);
			ajax.onreadystatechange = function() {
				if (ajax.readyState == 4) {
					if (ajax.status = 200) {
						var responseText = ajax.responseText;
						var textarea = document.getElementById("textarea");
						textarea.innerHTML = textarea.value + "<br>"
								+ responseText;
					}
				}
			}
		}
	</script>
	-->
</body>
</html>