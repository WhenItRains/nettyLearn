package lc.learn.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO 模型
 * selector 选择器
 */
public class NIOTest12 {
    public static void main(String[] args) throws IOException {

        //一个线程处理多个客户端
        //多个端口号

        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;


        //selector构造方式
        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //非阻塞的
            serverSocketChannel.configureBlocking(false);

            //与通道关联的对象
            ServerSocket socket = serverSocketChannel.socket();
            //绑定操作
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            socket.bind(address);

            //将channel注册到selector上 返回selectionKey ,可以通过selectionkey获取到channel
            SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("监听端口号" + ports[i]);
        }

        //等待数据
        while (true) {
            int numbers = selector.select();
            System.out.println("numbers" + numbers);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            System.out.println("selectionKeys" + selectionKeys);
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);
                    iterator.remove();
                    System.out.println("获取客户端连接" + socketChannel);
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int byteRead = 0;

                    while (true) {
                        ByteBuffer allocate = ByteBuffer.allocate(512);

                        allocate.clear();
                        int read = socketChannel.read(allocate);

                        byteRead += read;
                        if (read <= 0) {
                            break;
                        }
                        //反转
                        allocate.flip();
                        socketChannel.write(allocate);
                    }
                    System.out.println("读取" + byteRead + ",来自于：" + socketChannel);
                    iterator.remove();
                }
            }

        }




/*        SelectorProvider selectorProvider = SelectorProvider.provider();
        System.out.println(selectorProvider.getClass());
        class sun.nio.ch.WindowsSelectorProvider*/

    }
}

