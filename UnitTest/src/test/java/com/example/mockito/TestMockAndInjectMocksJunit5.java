package com.example.mockito;

import java.util.Collections;

import com.example.dao.School;
import com.example.dao.Teacher;
import com.example.service.TeacherService;
import com.example.service.impl.SchoolServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Cnfn
 * @date 2017/09/02
 */
@ExtendWith(MockitoExtension.class)
public class TestMockAndInjectMocksJunit5 {
    @InjectMocks
    private SchoolServiceImpl schoolService;

    @Mock
    private TeacherService teacherService;

    @Test
    public void testGetBeforeStub() {
        School school = schoolService.get("schoolName");
        assertThat(school.getTeachers()).isEmpty();
    }

    @Test
    @DisplayName("返回指定的 teachers")
    public void testGetAfterStub() {
        String mockedTeacherName = RandomStringUtils.randomPrint(10);
        Teacher teacher = new Teacher();
        teacher.setName(mockedTeacherName);
        when(teacherService.list(Mockito.anyString())).thenReturn(Collections.singletonList(teacher));

        School school = schoolService.get("schoolName");
        assertThat(school.getTeachers()).contains(teacher);
    }
}
