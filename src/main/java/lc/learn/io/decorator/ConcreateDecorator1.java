package lc.learn.io.decorator;

/**
 * 扩展的功能类
 */
public class ConcreateDecorator1 extends Decorator{
    public ConcreateDecorator1(Component Component) {
        super(Component);
    }

    @Override
    public void doSomething() {
        //完成基础功能
        super.doSomething();
        //添加新的功能
        this.dodoSomeThing();

    }

    private void dodoSomeThing(){
        System.out.println("功能B");
    }
}
