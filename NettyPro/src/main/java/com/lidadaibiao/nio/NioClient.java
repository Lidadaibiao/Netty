package com.lidadaibiao.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author dadaibiaoLi
 * @Desc
 * @Date 2021/12/3 10:32
 */
public class NioClient {


    public static void main(String[] args)throws Exception {
        //得到一个客户端
        SocketChannel socketChannel = SocketChannel.open();

        //配置非阻塞
        socketChannel.configureBlocking(false);

        //提供服务器端的ip 和 端口
        boolean connect = socketChannel.connect(new InetSocketAddress("192.168.81.121", 6666));

        //连接服务器
        if (!connect){
            //可能不会一下子连接上，所以在判断最终
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作..");
            }
        }
        //如果连接成功就发送数据
        String str = "hello java";

//        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将一个数据用buffer 缓冲区包裹
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //发送数据，将 buffer 数据写入 channel
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}
