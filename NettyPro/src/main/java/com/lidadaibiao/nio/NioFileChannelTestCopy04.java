package com.lidadaibiao.nio;

import sun.nio.ch.FileChannelImpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author dadaibiaoLi
 * @Desc
 * @Date 2021/12/2 20:39
 */
public class NioFileChannelTestCopy04 {
    public static void main(String[] args) throws  Exception{
        //使用前面学习后的 ByteBuffer（缓冲）和 FileChannel（通道），将 file01.txt 中的数据读入到程序，并显示在控制台屏幕
        //假定文件已经存在



        //创建输入 输出流
        FileInputStream fileInputStream = new FileInputStream("E:\\NioFile01.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\NioFile01Copy_副本.txt");

        //创建对应流的通道
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        //使用 transferForm 完成拷贝
        destCh.transferFrom(sourceCh,0,sourceCh.size());
        //关闭流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
