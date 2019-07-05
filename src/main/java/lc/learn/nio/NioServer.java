package lc.learn.nio;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * channel 使用多个和一个都可以
 * 通过一个线程可以管控多个channel
 * 现在使用一个线程 :服务器端
 */
public class NioServer {

    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        //创建一个服务通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //非阻塞
        serverSocketChannel.configureBlocking(false);
        //获取服务器端的socket
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));
        Selector selector = Selector.open();

        //监听server的channel 只是关注 连接 这个事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //
        while (true) {
            try {
                //关注的事件的数量
                int select = selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel client;
                    try {

                        //连接使用 ServerSocketChannel
                        if (selectionKey.isAcceptable()) {
                            //注册事件

                            //只是建立连接
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            //关注写事件
                            client.register(selector, SelectionKey.OP_READ);

                            String key = "【" + UUID.randomUUID().toString() + "】";
                            clientMap.put(key, client);
                        } else if (selectionKey.isReadable()) {

                            client = (SocketChannel) selectionKey.channel();

                            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
                            //将数据读到byteBuffer中
                            int read = client.read(byteBuffer);
                            //读到了数据
                            if (read > 0) {
                                byteBuffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                //将数据转为为数组
                                String content = String.valueOf(charset.decode(byteBuffer).array());

                                //找到发送者
                                String sendKey = null;
                                Set<Map.Entry<String, SocketChannel>> entries = clientMap.entrySet();

                                for (Map.Entry<String, SocketChannel> entry : entries) {

                                    if (entry.getValue() == client) {
                                        sendKey = entry.getKey();
                                        break;
                                    }
                                }
                                //将消息发送到各个客户端
                                for (Map.Entry<String, SocketChannel> entry : entries) {
                                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                                    allocate.put((sendKey + ":" + content).getBytes());
                                    allocate.flip();
                                    entry.getValue().write(allocate);
                                }

                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                selectionKeys.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
