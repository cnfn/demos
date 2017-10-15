/**
 * @author Cnfn
 * @date 2017/10/15
 */
public class JavaVMStackSOF2 {
    private int stackLength = 1;

    public void stackLeak() {
        String str = new String("sdfdsfssssssssssssssssssssssss");
        Double d = Double.valueOf("1231123213.1231233");
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF2 oom = new JavaVMStackSOF2();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length: " + oom.stackLength);
            throw e;
        }
    }
}
