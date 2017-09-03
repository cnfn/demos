package com.example.mockito;

import com.example.dao.School;
import com.example.service.TeacherService;
import com.example.service.impl.SchoolServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author zhixiao.mzx
 * @date 2017/09/02
 */
@ExtendWith(MockitoExtension.class)
class TestVerify {
    @InjectMocks
    private SchoolServiceImpl schoolService;

    @Mock
    private TeacherService teacherService;

    @Test
    @DisplayName("测试 Mockito.verify 方法")
    void testGet() {
        School school = schoolService.get("schoolName");
        assertThat(school.getTeachers()).isEmpty();

        // 验证调用过 teacherService.list() 方法, 且参数是 "schoolName"
        verify(teacherService).list("schoolName");

        // 验证仅调用 1 次 teacherService.list() 方法, 且参数是 "schoolName"
        verify(teacherService, times(1)).list("schoolName");

        // 验证从未调用 TeacherService.get() 方法
        verify(teacherService, never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("验证调用的情况")
    void testVerifyZeroInteractions() {
        verifyNoMoreInteractions(teacherService);
        verifyZeroInteractions(teacherService);
    }
}
