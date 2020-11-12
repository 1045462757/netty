package com.example.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {

    public static void main(String[] args) throws IOException {
        String str = "hello world";

        // 创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\file01.txt");

        // 通过fileOutputStream获取对应的FileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(str.getBytes());

        byteBuffer.flip();

        // 将byteBuffer的数据写入到fileChannel
        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }
}
