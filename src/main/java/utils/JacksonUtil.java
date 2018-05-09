package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JacksonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T parseJson(InputStream inputStream, Class<T> clz) throws IOException {
        return mapper.readValue(inputStream, clz);
    }
}
