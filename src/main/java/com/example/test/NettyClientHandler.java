package com.example.test;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    }

    /**
     * 通道就绪
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        byte[] login = new byte[]{0x78, 0x78, 0x11, 0x01, 0x07, 0x52, 0x53, 0x36, 0x78,
                (byte) 0x90, 0x02, 0x42, 0x70, 0x00, 0x32, 0x01, 0x00, 0x05, 0x12, 0x79, 0x0D, 0x0A};
        ByteBuf buffer = Unpooled.copiedBuffer(login);
        ctx.writeAndFlush(buffer);
        System.out.println("发送登录包:" + HexBin.encode(login));
    }

    /**
     * 通道有读取事件时
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);//复制内容到字节数组bytes

        System.out.println("收到数据包:" + HexBin.encode(bytes));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("client channelInactive");
    }
}
