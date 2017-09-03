package com.example.mockito;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author zhixiao.mzx
 * @date 2017/09/03
 */
@ExtendWith(MockitoExtension.class)
class TestSpy {

    @Test
    @DisplayName("测试 spy: 只 mock 一个真是对象的部分方法, 其他方法执行真实操作")
    void testAnswer() {
        List<String> list = new ArrayList<>();
        list.add("test");
        assertThat(list).size().isEqualTo(1);

        List<String> spy = spy(list);
        when(spy.size()).thenReturn(100);
        assertThat(spy).contains("test");
        assertThat(spy).size().isEqualTo(100);
    }
}
