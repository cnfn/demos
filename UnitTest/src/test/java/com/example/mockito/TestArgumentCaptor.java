package com.example.mockito;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author zhixiao.mzx
 * @date 2017/09/03
 */
@ExtendWith(MockitoExtension.class)
class TestArgumentCaptor {

    @Test
    @DisplayName("测试 ArgumentCaptor")
    void testAnswer(@Mock Map<String, String> map) {
        when(map.get(Mockito.anyString())).thenAnswer((Answer<String>)invocationOnMock -> {
            String key = invocationOnMock.getArgument(0);
            return generateValue(key);
        });

        String randomKey = RandomStringUtils.randomPrint(5);
        assertThat(map.get(randomKey)).isEqualTo(generateValue(randomKey));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(map).get(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(randomKey);
    }

    private String generateValue(String key) {
        return "value_" + key;
    }
}
