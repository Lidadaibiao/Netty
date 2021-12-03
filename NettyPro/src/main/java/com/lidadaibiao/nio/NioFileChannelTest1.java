package com.lidadaibiao.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author dadaibiaoLi
 * @Desc
 * @Date 2021/12/2 20:18
 */
public class NioFileChannelTest1 {
    //使用前面学习后的 ByteBuffer（缓冲）和 FileChannel（通道），将 “hello,尚硅谷” 写入到 file01.txt 中
    //文件不存在就创建
    public static void main(String[] args) throws Exception{
        String str = "hello java";
        //创建一个输出流 - -- 》Channel
        FileOutputStream outputStream = new FileOutputStream("E:\\NioFile01.txt");
        //通过FileOutputStream获得相应的FileChannel
        //fileChannel 真实类型 是fileChannelImpl
        FileChannel fileChannel = outputStream.getChannel();
        //创建一个缓冲区byteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        //将str写入byteBuffer缓冲区中
        byteBuffer.put(str.getBytes());
        //对byteBuffer进行  filp  翻转
        byteBuffer.flip();
        //将byteBuffer写入到fileChannel中去
        fileChannel.write(byteBuffer);
        //关闭流
        outputStream.close();
    }
}
