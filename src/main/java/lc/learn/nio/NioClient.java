package lc.learn.nio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NIO客户端
 *
 */
public class NioClient {

    public static void main(String[] args) {
        //2个线程 main和其他线程
        try{
            //创建客户端channel
            SocketChannel socketChannel = SocketChannel.open();
            //异步
            socketChannel.configureBlocking(false);
            //开启选择器
            Selector selector = Selector.open();
            //将channel注册到选择器，用于连接
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8899));

            while (true){
                //阻塞 等待事件
                selector.select();
                //事件集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                for(SelectionKey selectionKey : selectionKeys){
                    //是否是连接事件
                    if(selectionKey.isConnectable()){
                        SocketChannel channel = (SocketChannel)selectionKey.channel();
                        if (channel.isConnectionPending()){
                            //完成连接
                            channel.finishConnect();
                            ByteBuffer allocate = ByteBuffer.allocate(1024);
                            allocate.put((LocalDateTime.now() + "连接成功").getBytes());
                            allocate.flip();
                            //通知连接成功 通知到服务器
                            channel.write(allocate);

                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(()->{
                                //等待用户输入
                                while (true){
                                    allocate.clear();
                                    InputStreamReader input = new InputStreamReader(System.in);
                                    BufferedReader bufferedReader = new BufferedReader(input);
                                    String s = bufferedReader.readLine();
                                    allocate.put(s.getBytes());
                                    allocate.flip();
                                    //输入的内容返回到服务器
                                    channel.write(allocate);
                                }
                            });
                        }

                        channel.register(selector,SelectionKey.OP_READ);
                    }if (selectionKey.isReadable()){
                        SocketChannel channel =  (SocketChannel)selectionKey.channel();
                        //将数据读取到byteBuffer
                        ByteBuffer allocate = ByteBuffer.allocate(512);
                        int read = channel.read(allocate);

                        //读取到数据
                        if(read > 0){
                            //将数据转为字符串
                            //输出到控制台
                            String s = new String(allocate.array(), 0, read);
                            System.out.println(s);

                        }
                    }

                }
                //清空 selectionKeys
                selectionKeys.clear();
            }
        }catch (Exception e){

        }
    }
}
