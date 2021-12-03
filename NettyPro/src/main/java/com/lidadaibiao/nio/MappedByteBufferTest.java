package com.lidadaibiao.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author dadaibiaoLi
 * @Desc
 * @Date 2021/12/2 20:53
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception{
        //MappedByteBuffer 可让文件直接在内存（堆外内存）修改,操作系统不需要拷贝一次
        RandomAccessFile randomAccessFile = new RandomAccessFile("E:\\NioFile01Copy_副本.txt", "rw");
        //获得一个通道
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数 1:FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数 2：0：可以直接修改的起始位置
         * 参数 3:5: 是映射到内存的大小（不是索引位置），即将 1.txt 的多少个字节映射到内存
         * 可以直接修改的范围就是 0-5
         * 实际类型 DirectByteBuffer
         */

        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0, (byte) 'H');
        map.put(3, (byte) 'E');
        map.put(5,(byte)'F');//IndexOutOfBoundsException


        randomAccessFile.close();
        System.out.println("修改成功");


    }
}
