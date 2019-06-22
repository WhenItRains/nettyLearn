package lc.learn.io.decorator;

/**
 * 具体构建角色
 * 实现基础接口
 */
public class ConcreatComponent implements Component {
    public void doSomething() {
        System.out.println("功能A");
    }
}
