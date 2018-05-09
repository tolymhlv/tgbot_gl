package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GsonUtil {
    private static final Gson gson = new GsonBuilder().create();

    public static <T> T parseJson(InputStream inputStream, Class<T> clz) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream);
        return gson.fromJson(reader, clz);
    }


}
