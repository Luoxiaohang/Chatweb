package com.chatweb.single;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.catalina.websocket.WebSocketServlet;

public class SocketServer extends WebSocketServlet {
	private static final long serialVersionUID = 1L;
	public final Set<ChatWebSocket> users = new CopyOnWriteArraySet<ChatWebSocket>();

	public static int USERNUMBER = 1;

	@Override
	protected StreamInbound createWebSocketInbound(String arg0,
			HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		return new ChatWebSocket(users);
	}

	public class ChatWebSocket extends MessageInbound {

		private String username;
		private Set<ChatWebSocket> users = new CopyOnWriteArraySet<ChatWebSocket>();;

		public ChatWebSocket() {

		}

		public ChatWebSocket(Set<ChatWebSocket> users) {
			this.users = users;
		}

		@Override
		protected void onTextMessage(CharBuffer message) throws IOException {
			// 这里处理的是文本数据
			onMessage(message.toString());
		}
		
		public void onMessage(String data) {
			String[] val1 = data.split("\\t");
			if (val1[0].equals("NAME")) {
				String[] val2 = val1[1].split("_");
				for (ChatWebSocket user : users) {
					if (user.username.equals(val2[0])) {
						user.username = val2[1];
					}
				}
			} else if (val1[0].equals("MSG")) {
				String[] val2 = val1[1].split("_");
				for (ChatWebSocket user : users) {
					if (user.username.equals(val2[1])) {
						try {
							CharBuffer temp = CharBuffer.wrap(data);
							user.getWsOutbound().writeTextMessage(temp);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} else {
				System.out.println("ERROR");
			}

		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			// this.connection=connection;
			this.username = "#" + String.valueOf(USERNUMBER);
			USERNUMBER++;
			try {
				String message = "NAME" + "\t" + this.username;
				CharBuffer buffer = CharBuffer.wrap(message);
				this.getWsOutbound().writeTextMessage(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			users.add(this);
		}

		@Override
		protected void onClose(int status) {
			users.remove(this);

		}

		@Override
		protected void onBinaryMessage(ByteBuffer arg0) throws IOException {

		}

	}

}
