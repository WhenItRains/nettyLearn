package lc.learn.netty.secondexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端
 *
 */
public class MyServer {

    public static void main(String[] args) throws InterruptedException {

        //事件循环组boss:处理连接  设置开启多少线程，配置线程执行属性
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // new NioEventLoopGroup(1)  1个线程
        //事件循环组worker:处理请求，完成业务逻辑
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new MyServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }



    }
}
