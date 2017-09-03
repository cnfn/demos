package com.example.mockito;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Cnfn
 * @date 2017/09/03
 */
@ExtendWith(MockitoExtension.class)
class TestAnswer {

    @Test
    @DisplayName("测试 Answer: 根据参数构造返回值")
    void testAnswer(@Mock Map<String, String> map) {
        /**
         * 可替换为 lambda 表达式:
         * when(map.get(Mockito.anyString())).thenAnswer((Answer<String>)invocationOnMock -> {
         *    String key = invocationOnMock.getArgument(0);
         *    return generateValue(key);
         });
         */
        when(map.get(Mockito.anyString())).thenAnswer(new Answer<String>() {
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                String key = invocationOnMock.getArgument(0);
                return generateValue(key);
            }
        });

        String randomKey = RandomStringUtils.randomPrint(5);
        assertThat(map.get(randomKey)).isEqualTo(generateValue(randomKey));
    }

    private String generateValue(String key) {
        return "value_" + key;
    }
}
