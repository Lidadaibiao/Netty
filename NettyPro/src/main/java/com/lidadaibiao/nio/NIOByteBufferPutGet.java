package com.lidadaibiao.nio;

import java.nio.ByteBuffer;

/**
 * @author dadaibiaoLi
 * @Desc
 * @Date 2021/12/2 20:45
 */
public class NIOByteBufferPutGet {


    public static void main(String[] args) {
        //ByteBuffer 支持类型化的 put 和 get，put 放入的是什么数据类型，get 就应该使用相应的数据类型来取出，否则可能有 BufferUnderflowException 异常


        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        //test
        byteBuffer.putInt(1);
        byteBuffer.putShort((short) 22);
        byteBuffer.putChar('c');
        byteBuffer.putLong(10000L);

       //取出
        byteBuffer.flip();
//        System.out.println(byteBuffer.getInt());
//        System.out.println(byteBuffer.getShort());
//        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getInt());


    }
}
