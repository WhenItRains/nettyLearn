package lc.learn.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOTest8 {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("input2.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output2.txt");

        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);
        /*
        *  allocateDirect()方法  生成  直接缓冲方法，
        *  allocate()方法 生成 间接缓冲
        *  return new DirectByteBuffer(capacity);
        * */
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
