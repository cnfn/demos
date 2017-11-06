package com.example.chapter4.chapter3;

/**
 * testGC() 方法执行后, objA 和 objB 会不会被 GC 呢?
 * 执行参数: -ea -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 *
 * @author zhixiao.mzx
 * @date 2017/11/06
 */
public class ReferenceCountingGC {
    private static final int _1MB = 1024 * 1024;
    public Object instance = null;

    /**
     * 这个成员属性的唯一意义就是占点内存, 以便能在 GC 日志中看清楚是是否被回收过
     */
    private byte[] bigSize = new byte[2 * _1MB];

    public static void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();

        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        // 假设在这行发生 GC, objA 和 objB 是否能被回收?
        System.gc();
    }

    public static void main(String[] args) {
        testGC();
    }
}
