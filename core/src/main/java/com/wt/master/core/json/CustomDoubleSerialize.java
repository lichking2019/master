package com.wt.master.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 保留小数点儿后两位
 *
 * @author lichking2019@aliyun.com
 * @date May 8, 2019 at 4:39:32 PM
 */
public class CustomDoubleSerialize extends JsonSerializer<Double> {

    private DecimalFormat df = new DecimalFormat("##.00");

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        if(value != null) {
//            gen.writeString(df.format(value));
            gen.writeString(String.format("%.2f",value));
        }
    }

    public static void main(String args[]){
        DecimalFormat df = new DecimalFormat("##.00");

        System.out.println(df.format(Double.parseDouble("0.1")));
        System.out.println(String.format("%.2f",Double.parseDouble("0.1")));
    }
}