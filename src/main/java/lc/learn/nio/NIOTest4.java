package lc.learn.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 读取文件
 */
public class NIOTest4 {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output.txt");

        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();




       /* ByteBuffer byteBuffer = ByteBuffer.allocate(521);

        //先写入数据
        inputStreamChannel.read(byteBuffer);
        //反转
        byteBuffer.flip();

        //输入到output中
        outputStreamChannel.write(byteBuffer);*/

        /**
         * 上面注释是错误的↑
         * 应该将buffer作为容器，要多次操作。
         */

        ByteBuffer byteBuffer = ByteBuffer.allocate(4);

        while (true){

            byteBuffer.clear();  //如果没有这行 ：将一直循环  因为 limit 和 position 相等 read = 0 不能结束循环
            int read = inputStreamChannel.read(byteBuffer);
            System.out.println("read -- " + read);
            if(read == -1){
                //读取结束
                break;
            }
            //反转
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);

        }

        //关闭流
        fileInputStream.close();
        fileOutputStream.close();

    }
}
