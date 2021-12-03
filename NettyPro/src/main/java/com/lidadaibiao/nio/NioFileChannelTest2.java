package com.lidadaibiao.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author dadaibiaoLi
 * @Desc
 * @Date 2021/12/2 20:23
 */
public class NioFileChannelTest2 {

    public static void main(String[] args)throws Exception {
        //使用前面学习后的 ByteBuffer（缓冲）和 FileChannel（通道），将 file01.txt 中的数据读入到程序，并显示在控制台屏幕
        //假定文件已经存在
        //创建一个文件
        File file = new File("E:\\NioFile01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        //通过fileInputStream创建相关的channel
        FileChannel fileChannel = fileInputStream.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将通道的数据读入buffer
        fileChannel.read(byteBuffer);

        //将byteBuffer的字节流转换成String
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
