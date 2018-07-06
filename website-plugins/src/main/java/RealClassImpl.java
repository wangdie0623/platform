public class RealClassImpl implements ITInterface {

    @Override
    public void say() {
        System.out.println("say");
    }

    @Override
    public int foo() {
        System.out.println("foo");
        return 1;
    }

}
