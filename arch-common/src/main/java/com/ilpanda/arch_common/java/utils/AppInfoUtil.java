package com.ilpanda.arch_common.java.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okio.ByteString;

public class AppInfoUtil {

    @Nullable
    public static AppInfo getAppInfo(Context context) {
        return getAppInfo(context, context.getPackageName());
    }

    @Nullable
    public static AppInfo getAppInfo(Context context, final String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            if (pm == null) return null;
            return getBean(pm, pm.getPackageInfo(packageName, 0));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the applications' information.
     *
     * @return the applications' information
     */
    @NonNull
    public static List<AppInfo> getAppsInfo(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        if (pm == null) return list;
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null) continue;
            list.add(ai);
        }
        return list;
    }

    @NonNull
    public static List<String> getAppSignaturesMD5(Context context, final String packageName) {
        return getAppSignaturesHash(context, packageName, Algorithm.MD5);
    }

    @NonNull
    public static List<String> getAppSignaturesSHA1(Context context, final String packageName) {
        return getAppSignaturesHash(context, packageName, Algorithm.SHA_1);
    }

    @NonNull
    public static List<String> getAppSignaturesSHA256(Context context, final String packageName) {
        return getAppSignaturesHash(context, packageName, Algorithm.SHA_256);
    }

    @NonNull
    public static List<String> getAppSignaturesSHA512(Context context, final String packageName) {
        return getAppSignaturesHash(context, packageName, Algorithm.SHA_512);
    }

    private static List<String> getAppSignaturesHash(Context context, final String packageName, final Algorithm algorithm) {
        ArrayList<String> result = new ArrayList<>();
        if (TextUtils.isEmpty(packageName)) return result;
        Signature[] signatures = getAppSignatures(context, packageName);
        if (signatures == null || signatures.length <= 0) return result;
        for (Signature signature : signatures) {
            String hash = "";
            if (algorithm == Algorithm.MD5) {
                hash = ByteString.of(signature.toByteArray()).md5().hex();
            } else if (algorithm == Algorithm.SHA_1) {
                hash = ByteString.of(signature.toByteArray()).sha1().hex();
            } else if (algorithm == Algorithm.SHA_256) {
                hash = ByteString.of(signature.toByteArray()).sha256().hex();
            } else if (algorithm == Algorithm.SHA_512) {
                hash = ByteString.of(signature.toByteArray()).sha512().hex();
            }
            result.add(hash);
        }
        return result;
    }


    enum Algorithm {
        MD5("MD5"),
        SHA_1("SHA-1"),
        SHA_256("SHA-256"),
        SHA_512("SHA-512");

        private String algorithm;

        Algorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }
    }

    /**
     * Return the application's signature.
     *
     * @param packageName The name of the package.
     * @return the application's signature
     */
    @Nullable
    public static Signature[] getAppSignatures(Context context, final String packageName) {
        if (TextUtils.isEmpty(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES);
                if (pi == null) return null;

                SigningInfo signingInfo = pi.signingInfo;
                if (signingInfo.hasMultipleSigners()) {
                    return signingInfo.getApkContentsSigners();
                } else {
                    return signingInfo.getSigningCertificateHistory();
                }
            } else {
                PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
                if (pi == null) return null;

                return pi.signatures;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the application's package information.
     *
     * @return the application's package information
     */
    @Nullable
    public static AppInfo getApkInfo(Context context, final File apkFile) {
        if (apkFile == null || !apkFile.isFile() || !apkFile.exists()) return null;
        return getApkInfo(context, apkFile.getAbsolutePath());
    }

    /**
     * Return the application's package information.
     *
     * @return the application's package information
     */
    @Nullable
    public static AppInfo getApkInfo(Context context, final String apkFilePath) {
        if (TextUtils.isEmpty(apkFilePath)) return null;
        PackageManager pm = context.getPackageManager();
        if (pm == null) return null;
        PackageInfo pi = pm.getPackageArchiveInfo(apkFilePath, 0);
        if (pi == null) return null;
        ApplicationInfo appInfo = pi.applicationInfo;
        appInfo.sourceDir = apkFilePath;
        appInfo.publicSourceDir = apkFilePath;
        return getBean(pm, pi);
    }


    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pi == null) return null;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        String packageName = pi.packageName;
        ApplicationInfo ai = pi.applicationInfo;
        if (ai == null) {
            return new AppInfo(packageName, "", null, "", versionName, versionCode, false);
        }
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }


    public static boolean isAppDebug(Context context) {
        return isAppDebug(context, context.getPackageName());
    }

    /**
     * Return whether it is a debug application.
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug(Context context, final String packageName) {
        if (TextUtils.isEmpty(packageName)) return false;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class AppInfo {

        private String packageName;
        private String name;
        private Drawable icon;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(final int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        public AppInfo(String packageName, String name, Drawable icon, String packagePath,
                       String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        @Override
        @NonNull
        public String toString() {
            return "{" +
                    "\n    pkg name: " + getPackageName() +
                    "\n    app icon: " + getIcon() +
                    "\n    app name: " + getName() +
                    "\n    app path: " + getPackagePath() +
                    "\n    app v name: " + getVersionName() +
                    "\n    app v code: " + getVersionCode() +
                    "\n    is system: " + isSystem() +
                    "\n}";
        }
    }

}
