package com.example.Java8Date;

import com.example.service.BookService;
import jooq.generated.tables.records.BookRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private BookService bookService;

    @Test
    public void contextLoads() {
        BookRecord bookRecord = bookService.get(1);
        assertThat(bookRecord.getId()).isEqualTo(1);
    }

}
