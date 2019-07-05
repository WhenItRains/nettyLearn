package lc.learn.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 堆外内存映射文件
 * 将文件内容加载到内存中，直接操作内存数据(buffer),系统会将数据同步到磁盘文件中去！！！
 */
public class NIOTest9 {

    public static void main(String[] args) throws IOException {


        RandomAccessFile randomAccessFile = new RandomAccessFile("NIOTest9.txt","rw");

        FileChannel channel = randomAccessFile.getChannel();
        //设置权限  0 到 5 个字节大小
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0,(byte)'a');
        map.put(5,(byte)'b');


        randomAccessFile.close();

    }
}
