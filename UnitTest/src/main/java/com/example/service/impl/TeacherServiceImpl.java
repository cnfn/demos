package com.example.service.impl;

import java.util.Arrays;
import java.util.List;

import com.example.dao.Teacher;
import com.example.service.TeacherService;
import org.springframework.stereotype.Service;

/**
 * @author zhixiao.mzx
 * @date 2017/09/02
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    public List<Teacher> list(String schoolName) {
        Teacher teacher1 = new Teacher();
        teacher1.setName(schoolName + "_teacher1");

        Teacher teacher2 = new Teacher();
        teacher2.setName(schoolName + "_teacher2");

        return Arrays.asList(teacher1, teacher2);
    }

    @Override
    public void get(String teacherName) {
        throw new RuntimeException("Teatcher.get");
    }
}
