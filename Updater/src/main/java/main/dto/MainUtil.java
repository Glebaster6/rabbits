package main.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class MainUtil {
    @SneakyThrows
    public static byte[] objectToByteArray(Object object){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.flush();
        return bos.toByteArray();
    }

    @SneakyThrows
    public static String objectToJsonString(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @SneakyThrows
    public static <T> Object stringJsonToObject(String value, Class<T> valueType ){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(value, valueType);
    }
}
