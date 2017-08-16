package com.example.config.jackson;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

/**
 * @author Cnfn
 * @date 2017/08/16
 */
@JsonComponent
public class TimestampSerializer extends JsonSerializer<Timestamp> {
    
    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider sp) throws IOException {
        gen.writeString(value.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}