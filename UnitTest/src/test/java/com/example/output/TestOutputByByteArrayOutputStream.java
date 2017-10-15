package com.example.output;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Cnfn
 * @date 2017/10/15
 */
public class TestOutputByByteArrayOutputStream {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setupStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void clearStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    @DisplayName("获取标准输出")
    public void testOut() {
        String randomPrint = RandomStringUtils.randomPrint(10);
        System.out.println(randomPrint);
        assertThat(outContent.toString()).contains(randomPrint);
    }

    @Test
    @DisplayName("获取标准错误输出")
    public void testErr() {
        String randomPrint = RandomStringUtils.randomPrint(10);
        System.err.println(randomPrint);
        assertThat(errContent.toString()).contains(randomPrint);
    }
}
