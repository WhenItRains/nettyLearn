package lc.learn.netty.test;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * 返回 核心 * 2
 * inter 有超线程(核心 *2 )
 *
 */
public class Test1 {
    public static void main(String[] args) {
        int max = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(max);
    }
}
