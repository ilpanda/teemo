package cn.ilpanda.arch.java.base;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static <T> T fromJson(String json, Class<T> clz) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json, clz);
        } catch (Exception e) {
            TMLog.e(TAG, json + "\n" + CrashUtil.getThreadStack(e));
        }
        return t;
    }

    public static <T> List<T> fromArray(String json, Class<T> clz) {
        List<T> result = null;
        try {
            Type type = TypeToken.getParameterized(List.class, (Type) clz).getType();
            result = new Gson().fromJson(json, type);
        } catch (Exception e) {
            TMLog.e(TAG, json + "\n" + CrashUtil.getThreadStack(e));
        }
        return result;
    }

    public static <T> String toJson(Object src, Class<T> clz) {
        if (src == null) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.toJson(src, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String toJson(Object src) {
        if (src == null) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
