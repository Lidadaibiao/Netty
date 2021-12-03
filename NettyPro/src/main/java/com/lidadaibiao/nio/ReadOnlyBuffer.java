package com.lidadaibiao.nio;

import java.nio.ByteBuffer;

/**
 * @author dadaibiaoLi
 * @Desc
 * @Date 2021/12/2 20:50
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {
        //可以将一个普通 Buffer 转成只读 Buffer

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte)i);
        }

        //读取
        byteBuffer.flip();

        ///得到一个只读的 Buffer
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
        //ReadOnlyBufferException
        readOnlyBuffer.put((byte) 100);

    }
}
