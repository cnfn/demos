package com.example.output;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Cnfn
 * @date 2017/10/15
 */
public class TestOutputBySystemRules {
    @Rule
    public SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public SystemErrRule systemErrRule = new SystemErrRule().enableLog().muteForSuccessfulTests();

    @Test
    public void writesToSystemOut() {
        String randomStr = RandomStringUtils.randomAlphabetic(10);
        System.out.println(randomStr);
        assertThat(systemOutRule.getLog()).contains(randomStr);
    }

    @Test
    public void writesToSystemErr() {
        String randomStr = RandomStringUtils.randomAlphabetic(10);
        System.err.println(randomStr);
        assertThat(systemErrRule.getLog()).contains(randomStr);
    }

    @Test
    public void writesToSystemOutWithClear() {
        String randomStr = RandomStringUtils.randomAlphabetic(10);
        System.out.println(randomStr);
        assertThat(systemOutRule.getLog()).contains(randomStr);

        systemOutRule.clearLog();
        // 如果注释掉下面这行代码将会测试失败
        System.out.println(randomStr);
        assertThat(systemOutRule.getLog()).contains(randomStr);
    }
}
