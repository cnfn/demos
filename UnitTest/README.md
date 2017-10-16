# 在单元测试中捕获标准输出和标准错误输出

目前了解到有三种方式实现在单元测试中捕获标准输出和标准错误输出:

1.  重定向标准输出和标准错误输出;
2.  借助第三方库 [System Rules](http://stefanbirkner.github.io/system-rules/) + JUnit 4;
3.  借助 [Spring Boot Test 提供的 JUnit Rule](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-output-capture-test-utility) + JUnit 4;

## 1. 重定向标准输出和标准错误输出

将标准输出 `System.out` 和标准错误输出 `System.err` 重定向到 `ByteArrayOutputStream`, 然后通过 `ByteArrayOutputStream.toString()` 方法获取内容.

优点:

1.  没有任何依赖, 不会产生依赖冲突;

缺点:

1.  需要手动加载配置, 要多写一些代码(相比另外两种方法多几行);

测试代码: [TestOutputByByteArrayOutputStream.java](./src/test/java/com/example/output/TestOutputByByteArrayOutputStream.java)

## 2. 借助第三方库 [System Rules](http://stefanbirkner.github.io/system-rules/) + JUnit 4

[System Rules](http://stefanbirkner.github.io/system-rules/) 提供了一些列关于 `java.lang.System` 的 JUnit Rule:

1.  System.out, System.err and System.in
2.  System.exit
3.  System Properties
4.  Environment Variables
5.  Security Managers

在捕获 `System.out` 和 `System.err` 输出内容这块儿, 其实它也是通过重定向到 `ByteArrayOutputStream` 的方式实现的, 只不过将其封装了一下, 使用上更加方便. 另外它还提供了一些扩展功能:

1.  兼容 Windows/Linux/MacOS 不同操作系统下换行符不同的情况;
2.  调用 `mute()` 方法禁止输出到终端;
3.  调用 `muteForSuccessfulTests()` 方法, 只有在测试没有通过的情况下打印输出;

优点:

1.  功能较为丰富完善;
2.  无须手动加载配置;
3.  依赖相对较少;

缺点:

1.  默认不捕获输出内容, 需要先调用 `enableLog()` 下才行, 使用上感觉不是很优雅;

测试代码: [TestOutputBySystemRules.java](./src/test/java/com/example/output/TestOutputBySystemRules.java)

## 3. 借助 [Spring Boot Test 提供的 JUnit Rule](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-output-capture-test-utility) + JUnit 4

Spring Boot 在当前版本 (1.5.7) 下也就只提供了一个 JUnit Rule: `OutputCapture`, 它可以同时捕获 `System.out` 和 `System.err`, 使用上也是最简洁的一种方法啦.

优点:

1.  最简洁的一种方法;

缺点:

1.  依赖有点多(starter 全家桶:joy:);

测试代码: [TestOutputBySpringBootOutputCapture.java](./src/test/java/com/example/output/TestOutputBySpringBootOutputCapture.java)



## 综上

对于 Spring Boot 项目来说, 一般都有依赖 `spring-boot-starter-test`, 因此如果不需要 `System Rules` 提供的扩展功能的话, 就使用 **第三种方案** 吧. 但对于非 Spring Boot 项目就推荐使用 `System Rules`(第二种方案) 啦.