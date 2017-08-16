package com.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author Cnfn
 * @date 2017/08/16
 */
@Data
public class TestAnnotation {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;
}
