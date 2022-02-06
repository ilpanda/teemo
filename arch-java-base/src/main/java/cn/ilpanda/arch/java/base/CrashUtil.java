package cn.ilpanda.arch.java.base;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class CrashUtil {

    private static final String TAG = CrashUtil.class.getSimpleName();

    public static String getThreadStack(Throwable ex) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);

        Throwable cause = ex;
        while (cause != null) {
            cause.printStackTrace(printWriter);
            break;
        }
        String stackTrace = result.toString();

        printWriter.close();

        return stackTrace.trim();
    }

}
