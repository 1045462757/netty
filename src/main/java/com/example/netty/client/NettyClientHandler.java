//package com.example.netty.client;
//
//import com.example.netty.msg.*;
//import com.example.netty.reply.ReplyClientBody;
//import com.example.netty.reply.ReplyServerBody;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.timeout.IdleStateEvent;
//import io.netty.util.ReferenceCountUtil;
//
//public class NettyClientHandler extends SimpleChannelInboundHandler<BaseMsg> {
//
//    //利用写空闲发送心跳检测消息
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent e = (IdleStateEvent) evt;
//            switch (e.state()) {
//                case WRITER_IDLE:
//                    PingMsg pingMsg = new PingMsg();
//                    ctx.writeAndFlush(pingMsg);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    @Override
//    protected void messageReceived(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) {
//        MsgType msgType = baseMsg.getType();
//        switch (msgType) {
//            case LOGIN: {
//                //向服务器发起登录
//                LoginMsg loginMsg = new LoginMsg();
//                loginMsg.setPassword("12345678");
//                loginMsg.setUserName("test");
//                channelHandlerContext.writeAndFlush(loginMsg);
//            }
//            break;
//            case PING: {
//                PingMsg pingMsg = (PingMsg) baseMsg;
//                System.out.println("收到服务器心跳回复包:" + pingMsg.getPingPackage());
//            }
//            break;
//            case ASK: {
//                ReplyClientBody replyClientBody = new ReplyClientBody("客户端发送询问包");
//                ReplyMsg replyMsg = new ReplyMsg();
//                replyMsg.setBody(replyClientBody);
//                channelHandlerContext.writeAndFlush(replyMsg);
//            }
//            break;
//            case REPLY: {
//                ReplyMsg replyMsg = (ReplyMsg) baseMsg;
//                ReplyServerBody replyServerBody = (ReplyServerBody) replyMsg.getBody();
//                System.out.println("收到服务器登录回复包:" + replyServerBody.getServerInfo());
//            }
//            default:
//                break;
//        }
//        ReferenceCountUtil.release(msgType);
//    }
//}
