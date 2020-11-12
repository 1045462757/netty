package com.example.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) {

        // 创建BossGroup和WorkerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 服务器通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
//                            socketChannel.pipeline().addLast(new StringDecoder());
//                            socketChannel.pipeline().addLast(new StringEncoder());
//                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,
//                                    Unpooled.copiedBuffer(Config.DATA_PACK_SEPARATOR.getBytes())));
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给WorkerGroup的EventLoop对应的管道设置处理器

            System.out.println("...Server is ready...");

            // 绑定一个端口并且同步，生成了一个ChannelFuture对象
            ChannelFuture channelFuture = serverBootstrap.bind(9).sync();

            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
