package com.example.service.impl;

import com.example.dao.School;
import com.example.service.SchoolService;
import com.example.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Cnfn
 * @date 2017/09/02
 */
@Service
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    private TeacherService teacherService;

    public School get(String schoolName) {
        School school = new School();
        school.setName(schoolName);
        school.setTeachers(teacherService.list(schoolName));

        return school;
    }
}
