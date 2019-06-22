package lc.learn.io.decorator;

public class DecoratorMain {

    public static void main(String[] args) {
        Component component = new ConcreateDecorator1(new ConcreatComponent());
        component.doSomething();
    }
}
