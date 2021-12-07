package com.lidadaibiao.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author dadaibiaoLi
 * @Desc  Netty 服务器在 6668 端口监听，客户端能发送消息给服务器"hello,服务器~
 * 服务器可以回复消息给客户端"hello,客户端~
 * 目的：对 Netty 线程模型有一个初步认识，便于理解 Netty 模型理论
 * 编写服务端
 * 编写客户端
 * 对 netty 程序进行分析，看看 netty 模型特点
 * @Date 2021/12/7 12:31
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建BossGroup 和 WorkerGroup
        //说明
        //1创建两个线程组  bossGoup 和workerGroup
        //2 bossGroup负责处理连接请求，真正的客户端业务处理会交个  workerGroup
        //3两个都是无限循环
        //4bossGroup 和 workerGroup 含有的子线程 (NioEventLoop)
        //默认实际 cpu核数  * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //创建服务器端启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程进行设置
            bootstrap.group(bossGroup,workGroup)//设置两个线程组
            .channel(NioServerSocketChannel.class)//使用NioSocketChannel作为服务器的通道实现
            .option(ChannelOption.SO_BACKLOG,128)//设置线程队列等待连接个数
            .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
//            .handler(null)//// 该 handler对应 bossGroup , childHandler 对应 workerGroup
            .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象(匿名对象)
                //给pipeline设置处理器
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    System.out.println("客户socketchannel hashcode=" + ch.hashCode()); //可以使用一个集合管理 SocketChannel， 再推送消息时，可以将业务加入到各个channel 对应的 NIOEventLoop 的 taskQueue 或者 scheduleTaskQueue                }
                    ch.pipeline().addLast(new NettyServerHandler());
                }
            });// 给我们的workerGroup 的 EventLoop 对应的管道设置处理器
            System.out.println("·············服务器is ready......");
            //绑定一个端口并且同步生成了一个 ChannelFuture 对象（也就是立马返回这样一个对象）
            //启动服务器(并绑定端口)
            ChannelFuture cf = bootstrap.bind(6668).sync();

            //给cf注册监听器  监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()){
                        System.out.println("监听端口6668 成功");
                    }else {
                        System.out.println("监听6668端口 失败");
                    }
                }
            });
            //对关闭通道事件  进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
