package com.lidadaibiao.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author dadaibiaoLi
 * @Desc
 * @Date 2021/12/2 20:32
 */
public class NioFileChannelCopyTest3 {

    public static void main(String[] args) throws Exception{
        //使用 FileChannel（通道）和方法 read、write，完成文件的拷贝
        //拷贝一个文本文件 1.txt，放在项目下即可
        File file = new File("E:\\NioFile01.txt");
        FileInputStream fileInputStream = new FileInputStream("E:\\NioFile01.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\NioFile01Copy.txt");
        //获取各个流的通道 Channel
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
        //获取一个文件缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //开始读取文件
        while (true){
            //这里有一个重要的操作，一定不要忘了
            /*
             public final Buffer clear() {
                position = 0;
                limit = capacity;
                mark = -1;
                return this;
            }
             */

            byteBuffer.clear();
            //读取数据到byteBuffer里面
            int red = fileInputStreamChannel.read(byteBuffer);
            System.out.println("read ===== "+red);
            if (red == -1){
                //表示读完
                break;
            }
            //将buffer的数据 写入 通道 Channel
            //这里一定要进行一个转换，读写转换
            byteBuffer.flip();
            fileOutputStreamChannel.write(byteBuffer);

        }

        //最后关闭流
        fileInputStream.close();
        fileOutputStream.close();
    }

}
