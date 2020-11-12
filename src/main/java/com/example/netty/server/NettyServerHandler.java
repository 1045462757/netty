//package com.example.netty.server;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.util.CharsetUtil;
//
//public class NettyServerHandler extends SimpleChannelInboundHandler<Object> {
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) {
//        //channel失效，从Map中移除
//        NettyChannelMap.remove((SocketChannel) ctx.channel());
//    }
//
////    @Override
////    protected void messageReceived(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) {
////        if (MsgType.LOGIN.equals(baseMsg.getType())) {
////            LoginMsg loginMsg = (LoginMsg) baseMsg;
////            if ("test".equals(loginMsg.getUserName()) && "12345678".equals(loginMsg.getPassword())) {
////                //登录成功,把channel存到服务端的map中
////                NettyChannelMap.add(loginMsg.getClientId(), (SocketChannel) channelHandlerContext.channel());
////                System.out.println("收到客户端的登录包:" + loginMsg.getLoginPackage());
////                System.out.println("client" + loginMsg.getClientId() + " 登录成功");
////
////                // 发送登录回复包
////                ReplyServerBody replyBody = new ReplyServerBody("78 78 05 01 00 05 9F F8 0D 0A");
////                ReplyMsg replyMsg = new ReplyMsg();
////                replyMsg.setBody(replyBody);
////                NettyChannelMap.get(loginMsg.getClientId()).writeAndFlush(replyMsg);
////            }
////        } else {
////            if (NettyChannelMap.get(baseMsg.getClientId()) == null) {
////                //说明未登录，或者连接断了，服务器向客户端发起登录请求，让客户端重新登录
////                LoginMsg loginMsg = new LoginMsg();
////                channelHandlerContext.channel().writeAndFlush(loginMsg);
////            }
////        }
////        switch (baseMsg.getType()) {
////            case PING: {
////                PingMsg pingMsg = (PingMsg) baseMsg;
////                PingMsg replyPing = new PingMsg();
////                replyPing.setPingPackage("78 78 05 23 01 00 67 0E 0D 0A");
////                System.out.println("收到客户端心跳包:" + pingMsg.getPingPackage());
////                NettyChannelMap.get(pingMsg.getClientId()).writeAndFlush(replyPing);
////            }
////            break;
////            case ASK: {
////                //收到客户端的请求
////                AskMsg askMsg = (AskMsg) baseMsg;
////                if ("authToken".equals(askMsg.getParams().getAuth())) {
////                    ReplyServerBody replyBody = new ReplyServerBody("服务器回复包");
////                    ReplyMsg replyMsg = new ReplyMsg();
////                    replyMsg.setBody(replyBody);
////                    NettyChannelMap.get(askMsg.getClientId()).writeAndFlush(replyMsg);
////                }
////            }
////            break;
////            case REPLY: {
////                //收到客户端
////                ReplyMsg replyMsg = (ReplyMsg) baseMsg;
////                ReplyClientBody clientBody = (ReplyClientBody) replyMsg.getBody();
////                System.out.println("收到客户端回复包" + clientBody.getClientInfo());
////            }
////            break;
////            default:
////                break;
////        }
////        ReferenceCountUtil.release(baseMsg);
////    }
//
//    @Override
//    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object msg) {
//        ByteBuf byteBuf = (ByteBuf) msg;
//
//        System.out.println("client发送的消息:" + byteBuf.toString(CharsetUtil.UTF_8));
//    }
//}
