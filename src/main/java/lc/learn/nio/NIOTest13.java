package lc.learn.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * java 编解码
 * charset
 */
public class NIOTest13 {

    public static void main(String[] args) throws IOException {

        //将一个文件的内容转的另外一个文件中
        String file1 = "NIOTest13_in.txt";
        String file2 = "NIOTest13_ou.txt";

        RandomAccessFile readFile = new RandomAccessFile(file1,"r");
        RandomAccessFile outFile = new RandomAccessFile(file2,"rw");

        long length = new File(file1).length();

        //获取操作文件的通道
        FileChannel inputChannel = readFile.getChannel();
        FileChannel writeChannel = outFile.getChannel();

        //内存映射文件 将文件内容放到堆外内存中直接操作
        MappedByteBuffer mappedByteBuffer = inputChannel.map(FileChannel.MapMode.READ_ONLY,0,length);

        //设置 解码
        Charset charset = Charset.forName("iso-8859-1");
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharsetEncoder charsetEncoder = charset.newEncoder();
        //将mappedByte buffer 转为字符字节Buffer
        CharBuffer decode = charsetDecoder.decode(mappedByteBuffer);

        //将字节buffer 写到另外的文件中
        ByteBuffer encode = charset.encode(decode);
        writeChannel.write(encode);

        //关闭访问
        outFile.close();
        readFile.close();
    }
}
