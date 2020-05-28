package main.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.*;

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
    public static Object byteArrayToObject(byte[] data){
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
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

    @SneakyThrows
    public static String byteArrayToFile(String path, byte[] data){
        File file = new File(path);
        OutputStream os = new FileOutputStream(file);
        os.write(data);
        os.close();

        return path;
    }

}