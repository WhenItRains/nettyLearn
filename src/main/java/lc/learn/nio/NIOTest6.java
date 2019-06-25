package lc.learn.nio;

import java.nio.ByteBuffer;

public class NIOTest6 {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        //增加数据
        for (int i = 0 ; i < byteBuffer.capacity(); ++i){
            byteBuffer.put((byte) i);
        }

        //设置区域
        byteBuffer.limit(6);
        byteBuffer.position(2);


        /**生成新的btye :将 postion = 2 为起始位置，limit 为结束位置
         * 两个buffer的属性：postion 等等 都没有关系了
         */
        ByteBuffer slice = byteBuffer.slice();

        //获取 和 更新新的属性  两个buffer的数据会同时发生改变，因为：底层数据都是一份
        for (int i = 0 ; i < slice.capacity(); i++){
            byte b = slice.get(i);
            b*=2;
            slice.put(i,b);
        }

        //初始化
        byteBuffer.position(0);  //绝对操作
        byteBuffer.limit(byteBuffer.capacity());

        while (byteBuffer.hasRemaining()){
            byte b = byteBuffer.get(); //相对操作
            System.out.println(b);
        }

    }
}
