package com.ilpanda.arch_common.java.utils;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;

import androidx.core.text.TextUtilsCompat;
import androidx.core.view.ViewCompat;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class Utils {

    private Utils() {

    }

    public static boolean isRtl() {
        return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) ==
                ViewCompat.LAYOUT_DIRECTION_RTL;
    }

    public static boolean isMain() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public static String getInternalTotalSize(Context context) {
        return getFormatTotalDiskSize(context, Environment.getDataDirectory().getAbsolutePath());
    }

    public static String getInternalAvailableSize(Context context) {
        return getFormatFsAvailableSize(context, Environment.getDataDirectory().getAbsolutePath());
    }

    public static String getExternalTotalSize(Context context) {
        return getFormatTotalDiskSize(context, getSDCardPathByEnvironment());
    }

    public static String getExternalAvailableSize(Context context) {
        return getFormatFsAvailableSize(context, getSDCardPathByEnvironment());
    }

    public static String getSDCardPathByEnvironment() {
        if (isSDCardEnableByEnvironment()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }

    public static boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    public static String getFormatTotalDiskSize(Context context, String path) {
        return Formatter.formatFileSize(context, getTotalDiskSize(new File(path)));
    }

    public static String getFormatTotalDiskSize(Context context, File file) {
        return Formatter.formatFileSize(context, getTotalDiskSize(file));
    }

    public static long getTotalDiskSize(File file) {
        try {
            StatFs statFs = new StatFs(file.getAbsolutePath());
            //noinspection deprecation
            long blockCount =
                    SDK_INT < JELLY_BEAN_MR2 ? (long) statFs.getBlockCount() : statFs.getBlockCountLong();
            //noinspection deprecation
            long blockSize =
                    SDK_INT < JELLY_BEAN_MR2 ? (long) statFs.getBlockSize() : statFs.getBlockSizeLong();
//            return blockCount * blockSize / (1024 * 1024 * 1024);
            return blockCount * blockSize;
        } catch (IllegalArgumentException ignored) {

        }
        return 0;
    }


    public static String getFormatFsAvailableSize(Context context, String path) {
        return Formatter.formatFileSize(context, getFsAvailableSize(new File(path)));
    }

    public static String getFormatFsAvailableSize(Context context, File file) {
        return Formatter.formatFileSize(context, getFsAvailableSize(file));
    }

    public static long getFsAvailableSize(File file) {
        return getFsAvailableSize(file.getAbsolutePath());
    }

    // 可用空间
    public static long getFsAvailableSize(final String anyPathInFs) {
        if (TextUtils.isEmpty(anyPathInFs)) return 0;
        StatFs statFs = new StatFs(anyPathInFs);
        long blockSize;
        long availableSize;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            availableSize = statFs.getAvailableBlocksLong();
        } else {
            blockSize = statFs.getBlockSize();
            availableSize = statFs.getAvailableBlocks();
        }
        return blockSize * availableSize;
    }

    public static int calculateMaxMemorySize(Context context) {
        ActivityManager am = getService(context, ACTIVITY_SERVICE);
        boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
        return largeHeap ? am.getLargeMemoryClass() : am.getMemoryClass();
    }

    public static int calculateMemoryCacheSize(Context context) {
        int maxMemorySize = calculateMaxMemorySize(context);
        // Target ~15% of the available heap.
        return (int) (1024L * 1024L * maxMemorySize / 7);
    }

    @SuppressWarnings("unchecked")
    static <T> T getService(Context context, String service) {
        return (T) context.getSystemService(service);
    }


    // 判断是否为主进程
    public static boolean isMainProcess(Context context, int pid, String packageName) {
        return packageName.equals(getProcessName(context, pid));
    }

    // 获取进程名称
    public static String getProcessName(Context context, int pid) {
        ActivityManager am = getService(context, Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
        if (processInfoList == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return null;
    }

}
