package ObjTran.demo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TranInterface {
    List<String> test(Map<String,Integer> map);

    <T,R> R test1(@TestAnnotation(HashMap.class) T t);
}
