package com.chatweb.web;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.logging.Logger;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

public class MyMessageInbound extends MessageInbound {
    private int userIdName = 0;
    private Logger logger = Logger.getLogger(MyMessageInbound.class.getName());

    public int getUserIdName() {
        return userIdName;
    }

    protected void onOpen(WsOutbound outbound) {
        super.onOpen(outbound);
        userIdName = outbound.hashCode();
        SocketServlet.getSocketList().add(this);
        logger.info("Server onOpen");
    }

    protected void onClose(int status) {
    	SocketServlet.getSocketList().remove(this);
        super.onClose(status);
        logger.info("Server onClose");
    }

    // �ж�������Ϣ���ݵ��������Ƶ���ļ�����
    @Override
    protected void onBinaryMessage(ByteBuffer buffer) throws IOException {
        logger.info("Binary Message Receive: " + buffer.remaining());
    }

    @Override
    protected void onTextMessage(CharBuffer buffer) throws IOException {
        String msgOriginal = buffer.toString();

        int startIndex = msgOriginal.indexOf("!@#$%");
        String nikeName = msgOriginal.substring(0, startIndex);
        String textMsg = msgOriginal.substring(startIndex + 5);
        // ���ַ������װ����������
        // �������ַ����齫֧���»����������������޸Ľ����������޸ģ���֮��Ȼ

        String countMsg = SocketServlet.getSocketList().size() + "��ͬʱ����";
        logger.info("Server onTextMessage: " + countMsg + nikeName + ":"
                + textMsg);

        String msg1 = nikeName + ": " + textMsg;
        String msg2 = "��: " + textMsg;

        for (MyMessageInbound messageInbound : SocketServlet.getSocketList()) {
            CharBuffer msgBuffer1 = CharBuffer.wrap(msg1);
            CharBuffer msgBuffer2 = CharBuffer.wrap(msg2);

            WsOutbound outbound = messageInbound.getWsOutbound();
            if (messageInbound.getUserIdName() != this.getUserIdName()) {
                outbound.writeTextMessage(msgBuffer1);
                outbound.flush();
            } else {
                outbound.writeTextMessage(msgBuffer2);
            }
        }
    }
}
