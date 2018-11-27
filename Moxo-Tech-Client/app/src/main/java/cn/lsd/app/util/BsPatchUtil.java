package cn.lsd.app.util;

public class BsPatchUtil {
    static {
        System.loadLibrary("apkpatch");
    }

    public static native int patch(String oldApk, String newApk, String patch);
}
