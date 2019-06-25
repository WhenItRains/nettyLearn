

# java NIO

### NIO:Selector(选择器)
  
### NIO:Channel(通道)
+ Channel是用来写入数据或者读取数据的*对象*!,类似java IO中的stream。
+ 读写方式:Channel是双向的，stream是单向的
+ 更能很好的反映出底层操作系统的真实情况，linux的底层数据通道是双向的(即读又写)
### NIO:Buffer(缓冲区)
+ buffer本身是一块内存,底层是数组,数据的读与写都是通过Buffer实现
+ buffer API
    > filp() 读写切换,必须调用.用来修改buffer维护的变量！
    > putChar() putInt() ... 添加原生类型的数据,但是必须要按照数据取出,见 NIOTest5
+ java原生数据类型都有各自对应的Buffer类型。(boolen没有)
+ 绝对方法：完全忽略掉 position,limit
+ 相对方法：JDK会自动维护position,limit
##### Buffer重要属性:
+ position  小于等于 position
+ limit 小于等于 capacity 
    > 初始值为 capacity
+ capacity
+ mark
    > 初始化为 -1

#####额外收获
+ java 关键字
    > native  即 JNI,Java Native Interface :java本地方法
+ 直接缓冲
    > 将java中的对象都在堆中,byteBuffer继承中的两种HeapByteBuffer(非直接缓冲/简介缓冲)和DirectByteBuffer(直接缓冲)
    直接缓冲的java在堆中,但是实际数据在java内存模型之外的堆外内存中,对比简介缓冲,有零拷贝的优势！ 见图片0拷贝.png
+ 间接缓冲    
    > 间接缓冲在操作buffer的时候(I/0操作)将java堆内的数据Copy到堆外中,进行IO操作.不直接操作java的内存数据.这样多了一次拷贝. 见图片0拷贝.png

    
# java IO

### IO:Stream(流)

#### stream介绍
    1. 功能区分：输入流和输出流
    2. 流结构上区分为：字节流(以字节为单位或称面向字节)和字符流(以字符为处理单位)
    3. 字节流的输入输出基础是以InputStearm和OutputStearm抽象类
    4. 字符流的输入输出基础是以Reader和Writer抽象类
    5. 最底层还是以字节流为基础。字符流是字节流的封装！
    6. 一个流对象，不可能即使输入流，又是输出流
#### inputStream 逻辑
    1. 打开流
    2. 循环获取到的信息
    3. 读取信息
    4. 关闭流
#### outputStream 逻辑
    1. 打开流
    2. 循环获取到的信息
    3. 写入信息
    4. 关闭流
#### staream流的分类
#### 节点流
    从指定的位置I/O流类，例如：从一块磁盘上读写
#### 过滤流
    使用节点流作为输入输入流，过滤流是使用已经存在的I/O流连接创建的
#### Stream使用的设计模式(装饰者/包装)
    java I/O库使用的是一种链接的机制，流与流首尾相连，形成流管道。这种机制是装饰者(Decorator)设计模式的应用！！
    使用装饰者设计模式更加灵活，但是会产生更多的类或者对象。
    继承是针对类的功能进行扩展！
    装饰者是针对对象的功能进行扩展！

