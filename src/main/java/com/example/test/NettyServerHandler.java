package com.example.test;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[" + ctx.channel().remoteAddress() + "]" + " -->连接到服务器~~~");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[" + ctx.channel().remoteAddress() + "]" + "-->断开服务器~~~");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        System.out.println("server channelActive");
    }

    /**
     * 读取数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        // 复制内容到字节数组bytes
        byteBuf.readBytes(bytes);

//        System.out.println("收到数据包:" + Arrays.toString(bytes));
        System.out.println("收到数据包: client ip:[" + ctx.channel().remoteAddress() + "] -->" + HexBin.encode(bytes));

        // 解析数据包
        // 协议号 0x01登录包 0x23心跳包
        if (bytes.length < 4) {
            System.out.println("数据包过小");
            return;
        }
        byte protocolNum = bytes[3];

        if (protocolNum == 0x01) {
            // 登录包
            // byte[4]-byte[11] IMEI号
            String IMEI = Integer.toHexString(bytes[4]) + HexBin.encode(Arrays.copyOfRange(bytes, 5, 12));

            // byte[12]-byte[13] 类型识别码
            // byte[14]-byte[15] 时区语言

            // 信息序列号
            byte order1 = bytes[16];
            byte order2 = bytes[17];

            // 回复登录包
            // 错误校验号未实现
            byte[] response = new byte[]{0x78, 0x78, 0x05, 0x01, order1, order2, 0x00, 0x00, 0x0D, 0x0A};

            ByteBuf buffer = ctx.alloc().buffer();
            buffer.writeBytes(response);
            ctx.writeAndFlush(buffer);
            System.out.println("回复登录包: " + HexBin.encode(response));
        } else if (protocolNum == 0x23) {
            //心跳包

            // 信息序列号
            byte order1 = bytes[10];
            byte order2 = bytes[11];

            // 回复心跳包
            // 错误校验号未实现
            byte[] response = new byte[]{0x78, 0x78, 0x05, 0x23, order1, order2, 0x00, 0x00, 0x0D, 0x0A};
            ByteBuf buffer = ctx.alloc().buffer();
            buffer.writeBytes(response);
            ctx.writeAndFlush(buffer);
            System.out.println("回复心跳包: " + HexBin.encode(response));
        } else {
            //其他包
            System.out.println("其他包");
        }
    }

    /**
     * 读取数据完毕
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // 将数据写入到缓存并刷新
//        ctx.writeAndFlush(Unpooled.copiedBuffer("78 78 05 01 00 05 9F F8 0D 0A", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
//        System.out.println("server channelInactive");
    }
}
