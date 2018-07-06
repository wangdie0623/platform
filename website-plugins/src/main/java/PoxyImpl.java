import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PoxyImpl implements InvocationHandler {
    private Object target;//被代理对象

    public PoxyImpl(Object target) {
        this.target = target;
    }

    /**
     *
     * @param proxy //代理对象本身
     * @param method //待执行方法
     * @param args //待执行方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy:"+method.getName());
        if (target instanceof ITInterface) {
            ITInterface face = (ITInterface) target;
            face.foo();
        }
        Object result = method.invoke(target, args);
        return result;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        RealClassImpl aClass = new RealClassImpl();
        PoxyImpl poxy = new PoxyImpl(aClass);
        ITInterface face = (ITInterface) Proxy.newProxyInstance(
                ITInterface.class.getClassLoader(),
                aClass.getClass().getInterfaces(),
                poxy);
        face.foo();

    }
}
