package com.example.service;

import java.util.List;

import com.example.dao.Teacher;

/**
 * @author zhixiao.mzx
 * @date 2017/09/02
 */
public interface TeacherService {
    List<Teacher> list(String schoolName);
}
