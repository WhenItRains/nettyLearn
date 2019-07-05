package lc.learn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于Channel的Scattering 和 Gathering
 */
public class NIOTest11 {

    public static void main(String[] args) throws IOException {

        //开启端口号 监听数据
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);

        //处理字符的长度
        int messageLength = 2 + 3 + 4;

        //buffer数组
        ByteBuffer[] buffers = new ByteBuffer[3];

        //增加buffer
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        //获取通道
        SocketChannel accept = serverSocketChannel.accept();

        //循环
        while (true){
            int bytesRead = 0;
            while (bytesRead < messageLength){
                //将数据读到buffers中
                long read = accept.read(buffers);
                //修改读的字节数
                bytesRead +=read;

                System.out.println("bytesRead:" + bytesRead);

                Arrays.asList(buffers).stream().
                        map(buffer -> "position: "+ buffer.position() +",limit:" + buffer.limit()).
                        forEach(System.out::println);

            }
            //反转
            Arrays.asList(buffers).forEach(buffer ->{
                buffer.flip();
            });
            //
            int bytesWritten = 0;
            while (bytesWritten < messageLength){
                long r = accept.write(buffers);
                bytesWritten += r;
            }

            Arrays.asList(buffers).forEach(buffer ->{
                buffer.clear();
            });

            System.out.println("bytesRead" + bytesRead +",bytesWritten" + bytesWritten + ",messageLength" + messageLength);
        }

    }
}
