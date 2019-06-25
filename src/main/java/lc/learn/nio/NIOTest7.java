package lc.learn.nio;

import java.nio.ByteBuffer;

/**
 * 只读Buffer
 * 可以将普通buffer调用asReadOnlyBuffer() 生成新的BUFFER
 */
public class NIOTest7 {

    public static void main(String[] args) {

        // HeapByteBuffer extends ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        //增加数据
        for (int i = 0 ; i < byteBuffer.capacity(); ++i){
            byteBuffer.put((byte) i);
        }
        //获取buffer的只读buffer  不允许写入
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

       /*
        readOnlyBuffer.position(2);
        readOnlyBuffer.put((byte)2 );

        写入时出现：
       Exception in thread "main" java.nio.ReadOnlyBufferException
        */


    }
}
