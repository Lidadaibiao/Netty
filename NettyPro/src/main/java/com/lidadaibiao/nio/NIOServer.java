package com.lidadaibiao.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * @author dadaibiaoLi
 * @Desc
 * @Date 2021/12/3 10:06
 */
public class NIOServer {


    public static void main(String[] args) throws Exception{
        //编写一个 NIO 入门案例，实现服务器端和客户端之间的数据简单通讯（非阻塞）
        //目的：理解 NIO 非阻塞网络编程机制

        //创建一个serverSocketChannel  ---从而获得连接过来的客户端通道  SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //创建一个选择器（Selector对象）
        Selector selector = Selector.open();

        //绑定客户端  端口6666在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞  重要
        serverSocketChannel.configureBlocking(false);

        //  //把 serverSocketChannel 注册到  selector 关心 事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("注册后的selectionkey 数量=" + selector.keys().size()); // 1

        //循环等待客户端连接
        while (true){

            //这里等待一秒，没有客户端连接
            if (selector.select(1000) == 0){   //无事情发生
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            //如果返回的>0, 就获取到相关的 selectionKey集合
            //1.如果返回的>0， 表示已经获取到关注的事件
            //2. selector.selectedKeys() 返回关注事件的集合
            //   通过 selectionKeys 反向获取通道

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys 数量 = " + selectionKeys.size());

            //遍历 Set<SelectionKey>, 使用迭代器遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                //获取到SelectionKey
                SelectionKey key = iterator.next();
                //根据Key做相应的通道处理
                //如果是 OP_ACCEPT, 有新的客户端连接
                if (key.isAcceptable()){
                    //该该客户端生成一个 SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成了一个 socketChannel " + socketChannel.hashCode());
                    //配置 将客户端配置为非阻塞

                    socketChannel.configureBlocking(false);
                    //将socketChannel 注册到selector, 关注事件为 OP_READ， 同时给socketChannel
                    //关联一个Buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));


                    System.out.println("客户端连接后 ，注册的selectionkey 数量=" + selector.keys().size()); //2,3,4..
                }
                if (key.isReadable()){
                    //通过key获取Channel
                    SocketChannel channel = (SocketChannel) key.channel();

                    //获取到该channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();

                    //将通道的内容 读取到buffer里面去
                    channel.read(byteBuffer);

                    System.out.println("form 客户端 "+new String(byteBuffer.array()));
                }
                //手动从集合中移动当前的selectionKey, 防止重复操作
                iterator.remove();
            }
        }
    }
}
