package com.example.mockito;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Cnfn
 * @date 2017/09/03
 */
@ExtendWith(MockitoExtension.class)
class TestArgumentCaptor {
    @Captor
    private ArgumentCaptor<String> argumentCaptor;

    @Test
    @DisplayName("测试 ArgumentCaptor: 捕获调用参数")
    void testAnswer(@Mock Map<String, String> map) {
        when(map.get(Mockito.anyString())).thenReturn("value");

        String randomKey = RandomStringUtils.randomPrint(5);
        map.get(randomKey);

        verify(map).get(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(randomKey);
    }
}
