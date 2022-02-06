package cn.ilpanda.arch.java.base;


import java.io.Closeable;


public class Closeables {

    private static final String TAG = Closeables.class.getSimpleName();

    public static void closeQuietly(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
