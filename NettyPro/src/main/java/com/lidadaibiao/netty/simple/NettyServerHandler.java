package com.lidadaibiao.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * @author dadaibiaoLi
 * @Desc 说明
 * 1. 我们自定义一个Handler 需要继承netty 规定好的某个HandlerAdapter(规范)
 * 2. 这时我们自定义一个Handler , 才能称为一个handler
 * @Date 2021/12/7 12:46
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据事件(这里我们可以读取到客户端发送过来的消息)

    /**
     *1 channelHandlerContext ctx: 上下文对象，含有管道pipeline 通道channel 地址
     * 2 object msg:就是客户端发送的数据 默认object
     */

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程" + Thread.currentThread().getName() + "channel == " + ctx.channel());
        System.out.println("sever ctx = " + ctx);
        System.out.println("看看channel   和  popeline的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();



        //将msg转成一个bytebuf
        //bytebuf你所MOP的bytebuffer 这是Netty自身提供的
        ByteBuf byteBuf =  (ByteBuf)msg;
        System.out.println("客户端发送消息是" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:" + channel.remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //wirteAndFlush 是Write + flush
        //将数据写入到缓存 并刷新
        //一般将  我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,客户端 w(ﾟДﾟ)w",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
