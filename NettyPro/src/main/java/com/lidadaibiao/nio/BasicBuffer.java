package com.lidadaibiao.nio;

import java.nio.IntBuffer;

/**
 * @author dadaibiaoLi
 * @Desc
 * @Date 2021/12/2 19:58
 */
public class BasicBuffer {

    public static void main(String[] args) {
        //举例说明nio的使用
        //创建一个Buffer, 大小为 5, 即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向buffer 存放数据
//        intBuffer.put(10);
//        intBuffer.put(11);
//        intBuffer.put(12);
//        intBuffer.put(13);
//        intBuffer.put(14);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }

        //如何从buffer读取数据
        //将buffer转换，读写切换(!!!)
        /*
        public final Buffer flip() {
        limit = position; //读数据不能超过5
        position = 0;
        mark = -1;
        return this;
    }*/
        //如何从buffer读取数据
        intBuffer.flip();
//        intBuffer.position(0);
//        intBuffer.limit(3);
//        System.out.println(intBuffer.get());
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
