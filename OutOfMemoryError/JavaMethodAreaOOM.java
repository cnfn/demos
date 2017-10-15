import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * VM Args: -XX:PermSize=3M -XX:MaxPermSize=3M
 *
 * @author Cnfn
 * @date 2017/10/15
 */
public class JavaMethodAreaOOM {
    static class OOMObject {
    }

    public static void main(final String[] args) throws InterruptedException {
        List<Object> list = new ArrayList<>();
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
                    throws Throwable {
                    return methodProxy.invokeSuper(objects, args);
                }
            });
            list.add(enhancer.create());
        }
    }
}
