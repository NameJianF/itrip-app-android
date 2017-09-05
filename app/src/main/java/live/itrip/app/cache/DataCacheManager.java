package live.itrip.app.cache;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import live.itrip.common.util.AppLog;
import live.itrip.common.util.FileUtils;


/**
 * Created by Feng on 2017/8/22.
 * <p>
 * files-普通的文件存储
 * database-数据库文件（.db文件）
 * sharedPreference-配置数据(.xml文件）
 * cache-图片缓存文件
 */

public class DataCacheManager {
    private static final String DATABASE_NAME = "itrip";

    public static final String SHAREDPREFERENCES_FILE_NAME = "live.itrip.app.app_preference.xml";
    public static final String KEY_LOAD_IMAGE = "KEY_LOAD_IMAGE";


    public static String getCacheSizeString(Context context) {
        try {
            long internalCacheSize = FileUtils.getDirSize(getCacheDir(context));
            long externalCacheSize = FileUtils.getDirSize(getExternalCacheFile(context));
            long databaseSize = FileUtils.getDirSize(getDatabaseFile(context));
//            long sharedPreferenceSize
            long filesSize = FileUtils.getDirSize(getFilesDir(context));

            return FileUtils.formatFileSize(internalCacheSize + externalCacheSize + databaseSize + filesSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0KB";
    }

    /**
     * 清除缓存数据
     *
     * @param context
     */
    public static void clear(Context context) {
        cleanInternalCache(getCacheDir(context));
        cleanExternalCache(getExternalCacheFile(context));
        cleanDatabases(getDatabaseFile(context));
//        cleanSharedPreference(context);
        cleanFiles(getFilesDir(context));
    }

    public static File getCacheDir(Context context) {
        return context.getCacheDir();
    }

    private static File getDatabaseFile(Context context) {
        return context.getDatabasePath(DataCacheManager.DATABASE_NAME);
    }

    private static File getFilesDir(Context context) {
        return context.getFilesDir();
    }

    private static File getExternalCacheFile(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return context.getExternalCacheDir();
        }

        return null;
    }

    public static File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     */
    private static void cleanInternalCache(File file) {
        deleteFilesByDirectory(file);
    }

    /**
     * * 清除本应用 数据库(/data/data/com.xxx.xxx/databases)
     *
     * @param file
     */
    private static void cleanDatabases(File file) {
        deleteFilesByDirectory(file);
    }

    /**
     * * 清除本应用配置数据 SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.N)
    private static void cleanSharedPreference(Context context) {
//        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param file
     */
    private static void cleanFiles(File file) {
        deleteFilesByDirectory(file);
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param file
     */
    private static void cleanExternalCache(File file) {
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED))
        {
            deleteFilesByDirectory(file);
        }
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
//        if (directory != null && directory.exists() && directory.isDirectory()) {
//            for (File item : directory.listFiles()) {
//                boolean ret = item.delete();
//                AppLog.d(String.format("Delete File:%s,Result:%s", item.getAbsolutePath(), ret));
//            }
//        }
        deleteFolderFile(directory, true);
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param file
     * @param deleteThisPath
     */
    private static void deleteFolderFile(File file, boolean deleteThisPath) {
        if (file.exists()) {
            try {
//                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i], true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        boolean ret = file.delete();
                        AppLog.d(String.format("Delete File:%s,Result:%s", file.getAbsolutePath(), ret));
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            boolean ret = file.delete();
                            AppLog.d(String.format("Delete File:%s,Result:%s", file.getAbsolutePath(), ret));

                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
