import java.util.ArrayList;
import java.util.List;

/**
 * 测试 Java 堆溢出
 * 堆最小值(初始化值)(-Xms)与堆最大值(-Xmx)都设置为 20m,避免堆自动扩展
 *
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutofMemoryError
 *
 * @author Cnfn
 * @date 2017/10/15
 */
public class HeapOOM {
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            list.add(new OOMObject());
        }
    }

    static class OOMObject {}
}
