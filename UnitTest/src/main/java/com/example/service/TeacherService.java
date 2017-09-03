package com.example.service;

import java.util.List;

import com.example.dao.Teacher;

/**
 * @author Cnfn
 * @date 2017/09/02
 */
public interface TeacherService {
    List<Teacher> list(String schoolName);

    void get(String teacherName);
}
