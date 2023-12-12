package com.shopmall.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @author ：zhuxiying
 * @date ：Created in 2023-12-12 14:12
 * @description：
 * @modified By：
 * @version:
 */
public class DoubleSerializer extends JsonSerializer <Double> {
    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if (value != null && !"".equals(value)) {
            DecimalFormat df2 =new DecimalFormat("0.00");
            gen.writeString(df2.format(value));
        } else {
            gen.writeString(value + "");
        }
    }
}
