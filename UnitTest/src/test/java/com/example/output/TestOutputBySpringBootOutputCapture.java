package com.example.output;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Cnfn
 * @date 2017/10/16
 */
public class TestOutputBySpringBootOutputCapture {
    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void writeToSystemOut() {
        String randomStr = RandomStringUtils.randomAlphabetic(10);
        System.out.println(randomStr);
        assertThat(outputCapture.toString()).contains(randomStr);
    }

    @Test
    public void writeToSystemErr() {
        String randomStr = RandomStringUtils.randomAlphabetic(10);
        System.err.println(randomStr);
        assertThat(outputCapture.toString()).contains(randomStr);
    }
}
