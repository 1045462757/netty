package com.example.netty.client;

import com.example.netty.msg.Constants;
import com.example.netty.msg.LoginMsg;
import com.example.netty.msg.PingMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyClientBootstrap {

    private final int port;

    private final String host;

    private SocketChannel socketChannel;

    public NettyClientBootstrap(int port, String host) throws InterruptedException {
        this.port = port;
        this.host = host;
        start();
    }

    private void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.group(eventLoopGroup);
        bootstrap.remoteAddress(host, port);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new IdleStateHandler(20, 10, 0));
                socketChannel.pipeline().addLast(new ObjectEncoder());
                socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
//                socketChannel.pipeline().addLast(new NettyClientHandler());
            }
        });
        ChannelFuture future = bootstrap.connect(host, port).sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel) future.channel();
            System.out.println("...连接服务器成功...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Constants.setClientId("001");
        NettyClientBootstrap bootstrap = new NettyClientBootstrap(9, "localhost");

        LoginMsg loginMsg = new LoginMsg();
        loginMsg.setPassword("12345678");
        loginMsg.setUserName("test");
        loginMsg.setLoginPackage("78 78 11 01 07 52 53 36 78 90 02 42 70 00 32 01 00 05 12 79 0D 0A");
        bootstrap.socketChannel.writeAndFlush(loginMsg);
        while (true) {
            TimeUnit.SECONDS.sleep(5);
            PingMsg pingMsg = new PingMsg();
            pingMsg.setPingPackage("78 78 0B 23 C0 01 22 04 00 01 00 08 18 72 0D 0A");
            bootstrap.socketChannel.writeAndFlush(pingMsg);
        }
    }
}
