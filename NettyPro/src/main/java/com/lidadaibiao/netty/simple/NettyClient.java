package com.lidadaibiao.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author dadaibiaoLi
 * @Desc   客户端
 * @Date 2021/12/7 13:44
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {

        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建客户端启动对象
            //注意客户端使用的不是 serverBoostrap  而是BootStrap
            Bootstrap bootstrap = new Bootstrap();

            //设置相关参数
            bootstrap.group(group)//设置线程组
            .channel(NioSocketChannel.class)//红色纸客户端通道的实现类  、、反射机制
            .handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyClientHandler());//加入自己的处理器
                }
            });
            System.out.println("客户端 IS  OK~~~~~~~~");

            //启动客户端
            //关于ChannelFuture  涉及到了netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //关闭通道
            channelFuture.channel().closeFuture().sync();


        }finally {
                group.shutdownGracefully();
        }

    }

}
