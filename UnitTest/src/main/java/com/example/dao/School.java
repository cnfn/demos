package com.example.dao;

import java.util.List;

import lombok.Data;

/**
 * @author zhixiao.mzx
 * @date 2017/09/02
 */
@Data
public class School {
    private String name;
    private List<Teacher> teachers;
}
