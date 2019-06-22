package lc.learn.io.decorator;

/**
 * 装饰构建角色
 * 1 需要实现抽象构建角色
 * 2 需要持有具体构建角色
 * 核心所在
 */
public class Decorator implements Component{

    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    public void doSomething() {
        component.doSomething();
    }
}
