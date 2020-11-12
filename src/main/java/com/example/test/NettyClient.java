package com.example.test;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static SocketChannel socketChannel;

    public void init() throws InterruptedException {
        // 客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        // 创建客户端启动对象
        Bootstrap bootstrap = new Bootstrap();

        // 设置相关参数
        bootstrap.group(group) // 设置线程组
                .channel(NioSocketChannel.class) // 设置客户端通道的实现类
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
//                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,
//                                Unpooled.copiedBuffer(Config.DATA_PACK_SEPARATOR.getBytes())));
//                        socketChannel.pipeline().addLast(new StringDecoder());
//                        socketChannel.pipeline().addLast(new StringEncoder());
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });

        System.out.println("...client is ready...");

        // 启动客户端去连接服务端
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9).sync();
        if (channelFuture.isSuccess()) {
            socketChannel = (SocketChannel) channelFuture.channel();
        }

        // 关闭通道连接进行监听
//        channelFuture.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        NettyClient nettyClient = new NettyClient();
        nettyClient.init();

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            byte[] ping = new byte[]{0x78, 0x78, 0x0B, 0x23, 0x00, 0x01, 0x22, 0x04, 0x00,
                0x01, 0x00, 0x08, 0x18, 0x72, 0x0D, 0x0A};
            ByteBuf b = Unpooled.copiedBuffer(ping);
            socketChannel.writeAndFlush(b);
            System.out.println("发送心跳包:" + HexBin.encode(ping));
        }
    }
}
