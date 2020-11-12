package com.example.nio;

import java.nio.IntBuffer;

public class BasicBuffer {

    public static void main(String[] args) {

        // 创建一个Buffer,容量为5
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 将Buffer读写转换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
