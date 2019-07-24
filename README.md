
# Netty
### Netty :Handler(数据处理)
#### netty提供的handler
    webSocketChannelInitialzer.class :websocket使用
### Netty :Handler处理的消息类型
    TextWebSocketFrame ：websocket的消息类型
### Netty :Hander的方法介绍
1. userEventTriggered() 
    > 主要用于心跳检测,由于Netty服务器或者客户端检测不到断网,所以需要使用心跳,检测网路断开.Netty属于TCP编程
1. channelActive()
    > channel激活的时候,调用这个方法    
### Netty :byteBuf(netty的字节缓冲)

### Netty :EventLoopGroup(事件循环组)
    见lc.learn.netty.secondexample.MyServer 内容
##### EventLoopGroup aip
1. next() 返回下一个要使用的EventLoop
1. register(Channel channel) 将channel注册到EventLoop中,注册完成后通知channelFuture.异步方法
1. register(ChannelPromsie channelPromsie) 类似 register(Channel channel)

### Netty :NioEventLoopGroup(Nio事件循环组)
    基于NIO selector的channel

### Netty :ServerBootStrap(启动ServerChannel)

### Netty :设计模式(启动ServerChannel)
##### Rcactor模式 (反应器模式)
    netty整体设计模式是Rcactor模式的体现
    参考文档 Scalable IO in java
            Reactor :reactor-siemens.pdf
    

### Netty 额外收获
1. protoBuf:编解码.
1. gRPC:将数据进行整合的方式,跨语言
1. thrift:
1. 再学零拷贝():
1. 操作系统(linux、unix)空间:
     1. 内核和用户空间,权限不同..
     1. 操作系统切换空间模式.比如:在用户空间向内存空间发出请求时,
     1. 第一种文件拷贝:
        > 读数据时,在用户空间向内存空间发出请求,然后内核空间将硬盘拷贝到内核空间缓冲区,再将内核空间拷贝到用户空间缓冲区，然后进行数据传输、操作等..
        写数据时,将用户空间缓冲区的数据拷贝到内核空间缓冲区(与读数据的时候不同)，再进行网络数据传输
        读写数据时,4次空间转换，2次没必要的拷贝
     1. 第二种文件拷贝:2次空间转换，0次没必要的拷贝,sendfile()系统调用方法！ 
        > 网络数据传输:发送数据 =>用户空间先调用sendfile()访问内核空间,内核空间访问硬盘,将硬盘数据读取到内核空间缓冲区,再将数据写入到目标socket Buffer(与内核空间缓冲区不同)中,通过socketBuffer向目标发送数据,然后返回数据或者结果..
     1. 第三种文件拷贝: 
        > 用户空间先调用sendfile()访问内核空间,内核空间访问硬盘,将硬盘数据通过scatter/gather DMA读取到内核空间缓冲区,需要底层操作系统支持.
1. 标记接口:没有增加新的方法.
1. volatile关键字,防止代码重排序,代码顺序执行！！
# java NIO

### NIO:Selector(选择器)
+ 介绍：
    > SelectableChannel是双工的多路复用的对象
+ 关键元素：selectionKey
    + 将channel注册到selector上,selectionKey就会被创建
    + setKey:表示channel事件的所有情况：读、写、关、连接,selection创建时为空。
    + selected-Key:是setKey的子集,感兴趣的channel事件,selection创建时为空。
    + cancelled-Key:也是setKey的子集,是已经取消的channel事件,selection创建时为空。
    + 通过channel的register(selector,int)，将selector注册到channel中;也将key添加到Set key中
    + 当channel关闭(close)的时候或者调用selectionKey的cancel方法会将key添加到cancelled-Key,channel取消注册会在下一次selection时执行
    + key从selected-key的setKey中移除：通过set的remove()或者set的Iterator的remove().不能随意的添加和删除，只能通过channel的事件去增加key
    + ............读selector源码
    
### NIO:Channel(通道)
+ Channel是用来写入数据或者读取数据的`对象`,类似java IO中的stream。
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
+ 直接缓冲buffer
    > 将java中的对象都在堆中,byteBuffer继承中的两种HeapByteBuffer(非直接缓冲/简介缓冲)和DirectByteBuffer(直接缓冲)
    直接缓冲的java在堆中,但是实际数据在java内存模型之外的堆外内存中,对比简介缓冲,有零拷贝的优势！ 见图片0拷贝.png
+ 间接缓冲buffer    
    > 间接缓冲在操作buffer的时候(I/0操作)将java堆内的数据Copy到堆外中,进行IO操作.不直接操作java的内存数据.这样多了一次拷贝. 见图片0拷贝.png
+ 映射缓冲buffer
    > 将文件内容放到堆外内存中，直接将操作内存数据.然后由操作系统将数据同步到文件中.见NIOTest9.java
##### NIO:Buffer重要属性:
+ position  小于等于 position
+ limit 小于等于 capacity 
    > 初始值为 capacity
+ capacity
+ mark
    > 初始化为 -1

##### NIO:额外收获
+ java 关键字
    > native  即 JNI,Java Native Interface :java本地方法
+ IO与NIO区分：
      > IO 这种网络编程模式是一个线程处理一个客户端 
      > NIO 一个线程处理多个客户端 组件：selector 选择器
      > NIO 和 node 异步框架
      > 异步编程模型包括：event(事件)

# java编解码问题
## 编解码格式
+ ASCII
    > 最初ASCII只有7bit一个字符,现在扩展到了8bit,共表示128种字符
+ ISO-8859-1
    > 8bit表示一个字符,即用一个字节来表示一个字符,共计可以表示256种字符
+ GB2312
    > 2个字节表示一个汉字
+ GBK
    > 对GB2321生僻字的扩展.    
+ GB18030
    > 最完整的汉字表示形式！
+ Big5
    > 繁体中文的编码形式
+ unicode
    > 全世界的所有的语言的字符编码集,统一采用2个字节表示一个字符。但是对于西方国家来说，占用字节容量大
+ utf-8:unicode转换格式
    > unicode是编码形式，UTF-8是一种存储形式。UTF-8是unicode的实现形式
    > 变长字节表示形式，根据不同文字储存不同大小
    > UTF-8一般用3个表示中文,英文同ASCII和ISO-8859-1一样
    > 在文件的开始，有一个符号0xFEFF(BE)或者0xFFFE(LE) utf-16LE:lettle endian (小端) ,utf-16BE big endian(大端)
+ BOM(Byte order Mark)
    > 文件的开始是否有 (Zero Width No-Break Space) BOM头信息
## CharSet类
###### 两个文件之间拷贝，中间设置ISO-8859-1 编码，拷贝内容为中文，输出到另外的文件，另外没有乱码的问题
>见 NIOTest13.class的例子
    将数据从一个文件中取出，到另外的一个文件的中间解码和编码的过程中，都使用了同一个编解码格式，而且两个文件的编解码也是相同的，所以没有出现乱码问题。
    但是，在解码(ISO-8859-1)的`过程中已经出现了乱码`，但是又将乱码按照ISO-8859-1形式解析了回去，数据就没有被改变。但是在中间显示的时候是乱码的！
# java IO
### IO:Stream(流)
#### 代码样例
    传统IO 服务端：
    ServerSocket serverSocket = ...
    serverSocket.bind(8899)  //进行连接的端口号，不是进行数据传输的端口号！
   
    where(true){
    Socket socket = serverSocket.accept();  //阻塞方法
    
        new Thread(socket);
        run(){
            socket.getInputStream(). 
                 ...
                 ...
                 ...
        }
    }

    
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

