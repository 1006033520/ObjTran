package ObjTran.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) {
        InvocationHandler invocationHandler = (o, method, objects) -> {

            for (Type genericParameterType : method.getGenericParameterTypes()) {
                genericParameterType.getTypeName();
                System.out.println(genericParameterType.getTypeName());
            }
            return null;
        };

        TranInterface tranInterface = (TranInterface) Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(),new Class[]{TranInterface.class},invocationHandler);

        Integer bb = tranInterface.test1("aa");
        System.out.println(bb);




        List<String> a = tranInterface.test(new HashMap<>());
        System.out.println(a.toString());

    }
}
