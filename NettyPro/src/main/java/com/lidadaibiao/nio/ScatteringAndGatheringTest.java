package com.lidadaibiao.nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author dadaibiaoLi
 * @Desc
 * @Date 2021/12/3 9:31
 */
public class ScatteringAndGatheringTest {
    /**
     * Scattering：将数据写入到 buffer 时，可以采用 buffer 数组，依次写入 [分散]
     * Gathering：从 buffer 读取数据时，可以采用 buffer 数组，依次读
     */

    public static void main(String[] args) throws Exception{
        //使用ServerSocketChannel 和SocketChannel 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress  = new InetSocketAddress(7000);

        //绑定Socket并启动

        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建byteBuffer数组
        ByteBuffer[] byteBuffer = new ByteBuffer[2];
        byteBuffer[0] = ByteBuffer.allocate(5);
        byteBuffer[1] = ByteBuffer.allocate(3);


        //客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();

        int messageLength = 8; //假定从客户端接收 8 个字节

        //循环打印
        while (true){

            int byteRead = 0;

            while (byteRead<messageLength){
                //读取数据
                long read = socketChannel.read(byteBuffer);
                //累计读取的字节数
                byteRead += read;
                System.out.println("byteRead = " + byteRead);
                //使用流打印
                Arrays.asList(byteBuffer).stream().map(buffer -> "position ="+buffer.position()+" , limit ="+buffer.limit()).forEach(System.out::println);
            }
            //将所有的buffer 进行filp
            Arrays.asList(byteBuffer).forEach(byteBuffer1 -> byteBuffer1.flip());

            //将数据读出显示到客户端
            long byteWrite = 0;

            while (byteWrite < messageLength){
                long l = socketChannel.write(byteBuffer);
                byteWrite += l;

            }
            //将所有的buffer进行clear
            Arrays.asList(byteBuffer).forEach(byteBuffer1 -> {
                byteBuffer1.clear();
            });
            System.out.println("byteRead  = " + byteRead +" byteWrite " + byteWrite+ " messageLength" + messageLength);
        }

    }


}
