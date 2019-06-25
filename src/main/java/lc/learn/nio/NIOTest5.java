package lc.learn.nio;

import java.nio.ByteBuffer;

public class NIOTest5 {

    public static void main(String[] args) {

        //放入数据
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(111);
        buffer.putLong(50000000L);
        buffer.putDouble(54.321);
        buffer.putChar('1');
        buffer.putShort((short) 11);

        //反转
        buffer.flip();

        //读取数据  按照数据读取

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());





    }
}
