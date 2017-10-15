## 测试环境

```sh
$ java -version
java version "1.7.0_80"
Java(TM) SE Runtime Environment (build 1.7.0_80-b15)
Java HotSpot(TM) 64-Bit Server VM (build 24.80-b11, mixed mode)

$ pacman -Qi visualvm
名字           : visualvm
版本           : 1.3.9-1
描述           : Visual tool integrating several commandline JDK tools and lightweight profiling capabilities
架构           : x86_64
URL            : https://visualvm.github.io/
软件许可       : custom:GPL
组             : 无
提供           : 无
依赖于         : java-environment
可选依赖       : 无
要求被         : 无
被可选依赖     : jdk7
冲突与         : 无
取代           : 无
安装后大小     : 32.42 MiB
打包者         : Guillaume ALAUX <guillaume@archlinux.org>
编译日期       : 2016年10月25日 星期二 03时16分20秒
...
```

## 测试 Java 堆溢出

测试内容: Java 堆用于存储对象实例, 只要不停地创建对象, 并且保证对象有引用计数不被 GC 回收, 那么在对象梳理到达堆的最大容量限制后就会产生内存溢出.

测试参数: 堆最小值(初始化值)(`-Xms`)与堆最大值(`-Xmx`)都设置为 20m,避免堆自动扩展.

```sh
-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
```

示例代码: [HeapOOM](./HeapOOM.java)

测试命令:

```sh
$ javac HeapOOM.java
$ java -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError HeapOOM
java.lang.OutOfMemoryError: Java heap space
Dumping heap to java_pid3583.hprof ...
Heap dump file created [27482529 bytes in 0.155 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at java.util.Arrays.copyOf(Arrays.java:2245)
        at java.util.Arrays.copyOf(Arrays.java:2219)
        at java.util.ArrayList.grow(ArrayList.java:242)
        at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:216)
        at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:208)
        at java.util.ArrayList.add(ArrayList.java:440)
        at HeapOOM.main(HeapOOM.java:17)
```

通过 VisualVM 查看 dump 文件:

```sh
$ visualvm --openfile java_pid3583.hprof
```

通过 **Classes** 标签页可以看到有 99.5% 的对象都是 `HeapOOM$OOMObject`, 且占据了堆 65.6% 的空间:

| Class Name        | Instances [%]    | Instances       | Size               |
| ----------------- | ---------------- | --------------- | ------------------ |
| HeapOOM$OOMObject | 99.5074520745097 | 810,326 (99.5%) | 12,965,216 (65.6%) |

## 虚拟机栈和本地方法栈溢出

HotSpot 虚拟机并不区分虚拟机栈和本地方法栈. 因此栈容量只能通过 `-Xss` (虚拟机每个线程栈大小)来设定.

在 Java 7 中, 虚拟机每个线程栈最小值是 228k.

测试代码1: [没有局部变量: JavaVMStackSOF1.java](./JavaVMStackSOF1.java)

测试命令:

```sh
$ javac JavaVMStackSOF1.java
$ java -Xss228k JavaVMStackSOF1
stack length: 1517
Exception in thread "main" java.lang.StackOverflowError
        at JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:9)
        at JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:10)
        at JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:10)
        ...
        ...
        at JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:10)
```

测试代码2: [有局部变量: JavaVMStackSOF2.java](./JavaVMStackSOF2.java)

测试命令:

```sh
$ javac JavaVMStackSOF2.java
$ java -Xss228k JavaVMStackSOF2
stack length: 1278
Exception in thread "main" java.lang.StackOverflowError
        at java.lang.Double.doubleToRawLongBits(Native Method)
        at java.lang.Double.doubleToLongBits(Double.java:808)
        at sun.misc.FloatingDecimal.doubleToBigInt(FloatingDecimal.java:218)
        at sun.misc.FloatingDecimal.doubleValue(FloatingDecimal.java:1492)
        at java.lang.Double.valueOf(Double.java:504)
        at JavaVMStackSOF2.stackLeak(JavaVMStackSOF2.java:10)
        at JavaVMStackSOF2.stackLeak(JavaVMStackSOF2.java:12)
        at JavaVMStackSOF2.stackLeak(JavaVMStackSOF2.java:12)
        ...
        ...
        at JavaVMStackSOF2.stackLeak(JavaVMStackSOF2.java:12)
```

测试结果: 当设置线程栈大小为 228k 时, 当局部变量少一点时, 函数栈深度大一点(深度分别是 1517 和 1278), 但最后都是抛出`StackOverflowError`, 测试代码 2 并没有按照预想的那样抛出 `OutOfMemoryError`.

## 方法区溢出

方法区用于存放 Class 相关信息, 如类名, 访问修饰符, 常量池, 字段类型, 字段描述, 方法描述等. 对于这个区域的测试, 基本思路时运行时产品大量的类去填满方法区, 直到溢出. 下面通过 CGLib 操作字节码运行时生成大量的动态类.

测试代码: [JavaMethodAreaOOM.java](./JavaMethodAreaOOM.java)

测试命令: 

```sh
$ javac JavaMethodAreaOOM.java -cp cglib-3.2.5.jar
$ java -XX:PermSize=3M -XX:MaxPermSize=3M -XX:+HeapDumpOnOutOfMemoryError JavaVMStackOOM
java.lang.OutOfMemoryError: PermGen space
Dumping heap to java_pid14141.hprof ...
Heap dump file created [1427625 bytes in 0.006 secs]
Error occurred during initialization of VM
java.lang.OutOfMemoryError: PermGen space
        at sun.misc.Launcher.<init>(Launcher.java:71)
        at sun.misc.Launcher.<clinit>(Launcher.java:57)
        at java.lang.ClassLoader.initSystemClassLoader(ClassLoader.java:1489)
        at java.lang.ClassLoader.getSystemClassLoader(ClassLoader.java:1474)
```

>   TODO: 在测试中发现再稍微增大永久代大小(比如说设置 PermSize=10M), 就不会触发 OutOfMemoryError 了, 猜测是类被回收了, 后续可以测试验证下.

## 本机直接内存溢出

`DirectMemory` 容量可通过 `-XX:MaxDirectMemorySize` 指定, 如果不指定, 默认与 Java 堆的最大值 `-Xmx` 相同. 

>   TODO: 使用示例代码测试时这个参数并没有生效, java 进程一直申请内存, 直到因 OOM 被 linux 系统干掉...

测试代码: [DirectMemoryOOM.java](./DirectMemoryOOM.java)

由 `DirectMemory` 导致的内存溢出, 一个明显特征是在 Heap Dump 文件中看不到明显异常, 如果发现 OOM 之后 Dump 文件很小, 而程序中又直接或间接使用了 NIO, 那就可以考虑检查下是不是这方面的原因.